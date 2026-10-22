package fr.vinci.biblijeu2.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

// url : https://api.rawg.io/api/games?search=
@JsonIgnoreProperties(ignoreUnknown = true)
public record RawgReponse(Integer count, List<RawgJeu> results) {
}