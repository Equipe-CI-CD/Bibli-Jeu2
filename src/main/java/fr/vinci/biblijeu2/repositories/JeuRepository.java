package fr.vinci.biblijeu2.repositories;

import fr.vinci.biblijeu2.models.Jeu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface JeuRepository extends JpaRepository<Jeu, Long> {

    boolean existsByRawgId(Long rawgId);
    List<Jeu> findAllByOrderByDateAjoutDesc();
}