package fr.vinci.biblijeu2.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public record RawgJeu(
        Long id,
        String name,
        String released,
        // Dans le JSON le champ s'appelle "background_image" : @JsonProperty fait le lien.
        @JsonProperty("background_image") String backgroundImage,
        Double rating
) {
}