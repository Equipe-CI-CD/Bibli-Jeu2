package fr.vinci.biblijeu2.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;


@Entity
public class Jeu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long rawgId;

    @Column(nullable = false)
    private String nom;

    @Column(length = 1000)
    private String imageUrl;

    private String dateSortie;

    private Double note;

    private LocalDateTime dateAjout;

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