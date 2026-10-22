package fr.vinci.biblijeu2.services;

import fr.vinci.biblijeu2.models.Jeu;
import fr.vinci.biblijeu2.repositories.JeuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// La bibliothèque personnelle : ajouter, lister, supprimer.
// Le service passe par le repository, qui lui parle à la base H2.
@Service
public class BibliothequeService {

    private final JeuRepository jeuRepository;

    // Injection par constructeur : Spring nous donne le repository qu'il a créé.
    public BibliothequeService(JeuRepository jeuRepository) {
        this.jeuRepository = jeuRepository;
    }

    // Renvoie true si le jeu a été ajouté, false s'il était déjà dans la bibliothèque.
    public boolean ajouter(Long rawgId, String nom, String imageUrl, String dateSortie, Double note) {
        if (jeuRepository.existsByRawgId(rawgId)) {
            return false;
        }
        jeuRepository.save(new Jeu(rawgId, nom, videVersNull(imageUrl), videVersNull(dateSortie), note));
        return true;
    }

    public List<Jeu> lister() {
        return jeuRepository.findAllByOrderByDateAjoutDesc();
    }

    public void supprimer(Long id) {
        jeuRepository.deleteById(id);
    }

    // Les identifiants RAWG déjà enregistrés : sert à afficher "Déjà dans ta bibliothèque"
    // dans les résultats de recherche. Un Set permet de tester contains() rapidement.
    public Set<Long> getRawgIdsEnregistres() {
        return jeuRepository.findAll().stream()
                .map(Jeu::getRawgId)
                .collect(Collectors.toSet());
    }

    // Un champ de formulaire vide arrive sous forme de "" : on enregistre plutôt null.
    private String videVersNull(String valeur) {
        return (valeur == null || valeur.isBlank()) ? null : valeur;
    }
}