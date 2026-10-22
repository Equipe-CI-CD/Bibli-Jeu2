package fr.vinci.biblijeu2.repositories;

import fr.vinci.biblijeu2.models.Jeu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// On écrit seulement l'interface : Spring Data crée la classe tout seul au démarrage.
// JpaRepository<Jeu, Long> donne déjà save(), findAll(), findById(), deleteById()...
public interface JeuRepository extends JpaRepository<Jeu, Long> {

    // Spring devine la requête à partir du nom de la méthode :
    // "existe-t-il un Jeu dont rawgId = ... ?"
    boolean existsByRawgId(Long rawgId);

    // "tous les jeux, triés par dateAjout du plus récent au plus ancien"
    List<Jeu> findAllByOrderByDateAjoutDesc();
}