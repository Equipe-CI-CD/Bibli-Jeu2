package fr.vinci.biblijeu2.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// Un jeu tel que l'API RAWG le renvoie. On ne garde que les champs dont on a besoin.
// Jackson remplit le record tout seul à partir du JSON, en se basant sur le nom des champs.
// ignoreUnknown = true : les autres champs du JSON (platforms, genres...) sont ignorés.
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