package fr.vinci.biblijeu2.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// La réponse complète de GET https://api.rawg.io/api/games?search=...
// RAWG met le nombre total de jeux dans "count" et la liste dans "results".
@JsonIgnoreProperties(ignoreUnknown = true)
public record RawgReponse(Integer count, List<RawgJeu> results) {
}