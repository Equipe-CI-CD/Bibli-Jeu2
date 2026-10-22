package fr.vinci.biblijeu2.services;

import fr.vinci.biblijeu2.models.Jeu;
import fr.vinci.biblijeu2.repositories.JeuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// On démarre l'appli avec une base H2 en mémoire : ta vraie bibliothèque n'est pas touchée.
@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:testdb")
class BibliothequeServiceTest {

    @Autowired
    private BibliothequeService bibliothequeService;

    @Autowired
    private JeuRepository jeuRepository;

    // Avant chaque test, on vide la table pour partir de zéro.
    @BeforeEach
    void viderLaBase() {
        jeuRepository.deleteAll();
    }

    @Test
    void ajouterEnregistreLeJeu() {
        boolean ajoute = bibliothequeService.ajouter(3328L, "The Witcher 3", "https://img/w3.jpg", "2015-05-18", 4.65);

        assertTrue(ajoute);
        List<Jeu> jeux = bibliothequeService.lister();
        assertEquals(1, jeux.size());
        assertEquals("The Witcher 3", jeux.get(0).getNom());
    }

    @Test
    void ajouterDeuxFoisLeMemeJeuEstRefuse() {
        bibliothequeService.ajouter(3328L, "The Witcher 3", null, null, null);
        boolean ajouteUneDeuxiemeFois = bibliothequeService.ajouter(3328L, "The Witcher 3", null, null, null);

        assertFalse(ajouteUneDeuxiemeFois);
        assertEquals(1, bibliothequeService.lister().size());
    }

    @Test
    void champVideEstEnregistreCommeNull() {
        bibliothequeService.ajouter(99L, "Jeu sans date", "", "", null);

        Jeu jeu = bibliothequeService.lister().get(0);
        assertNull(jeu.getImageUrl());
        assertNull(jeu.getDateSortie());
    }

    @Test
    void supprimerRetireLeJeu() {
        bibliothequeService.ajouter(3328L, "The Witcher 3", null, null, null);
        Long id = bibliothequeService.lister().get(0).getId();

        bibliothequeService.supprimer(id);

        assertTrue(bibliothequeService.lister().isEmpty());
    }
}