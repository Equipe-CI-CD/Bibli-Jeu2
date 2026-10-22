package fr.vinci.biblijeu2.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

// @Entity : chaque objet Jeu devient une ligne de la table JEU dans la base H2.
// Ici on ne peut pas utiliser un record : JPA a besoin d'une classe "classique"
// avec un constructeur vide.
@Entity
public class Jeu {

    // @Id = clé primaire, générée automatiquement par la base (1, 2, 3...).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // L'identifiant du jeu chez RAWG. unique = true : impossible d'enregistrer deux fois le même jeu.
    @Column(nullable = false, unique = true)
    private Long rawgId;

    @Column(nullable = false)
    private String nom;

    // Les adresses d'images RAWG peuvent être longues (255 caractères par défaut).
    @Column(length = 1000)
    private String imageUrl;

    private String dateSortie;

    private Double note;

    private LocalDateTime dateAjout;

    // Constructeur vide obligatoire pour JPA (il s'en sert pour relire les lignes de la table).
    protected Jeu() {
    }

    public Jeu(Long rawgId, String nom, String imageUrl, String dateSortie, Double note) {
        this.rawgId = rawgId;
        this.nom = nom;
        this.imageUrl = imageUrl;
        this.dateSortie = dateSortie;
        this.note = note;
        this.dateAjout = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getRawgId() {
        return rawgId;
    }

    public String getNom() {
        return nom;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDateSortie() {
        return dateSortie;
    }

    public Double getNote() {
        return note;
    }

    public LocalDateTime getDateAjout() {
        return dateAjout;
    }
}