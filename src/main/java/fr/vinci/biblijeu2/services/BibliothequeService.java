package fr.vinci.biblijeu2.services;

import fr.vinci.biblijeu2.models.Jeu;
import fr.vinci.biblijeu2.repositories.JeuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class BibliothequeService {

    private final JeuRepository jeuRepository;

    public BibliothequeService(JeuRepository jeuRepository) {
        this.jeuRepository = jeuRepository;
    }

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


    public Set<Long> getRawgIdsEnregistres() {
        return jeuRepository.findAll().stream()
                .map(Jeu::getRawgId)
                .collect(Collectors.toSet());
    }

    private String videVersNull(String valeur) {
        return (valeur == null || valeur.isBlank()) ? null : valeur;
    }
}