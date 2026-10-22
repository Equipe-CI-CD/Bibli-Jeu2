package fr.vinci.biblijeu2.services;

import fr.vinci.biblijeu2.models.RawgJeu;
import fr.vinci.biblijeu2.models.RawgReponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

// Le seul endroit de l'appli qui parle à l'API RAWG.
@Service
public class RawgService {

    private static final Logger log = LoggerFactory.getLogger(RawgService.class);

    private final RestClient restClient;
    private final String cleApi;

    // @Value lit les propriétés de application.properties.
    // rawg.api.cle vaut ${RAWG_API_KEY:} : la clé vient de la variable d'environnement,
    // elle n'est donc jamais écrite dans le code poussé sur GitHub.
    public RawgService(@Value("${rawg.api.url}") String urlApi,
                       @Value("${rawg.api.cle}") String cleApi) {
        // RestClient = le client HTTP de Spring. Toutes les requêtes partiront de urlApi.
        this.restClient = RestClient.create(urlApi);
        this.cleApi = cleApi;
    }

    // Recherche des jeux par nom. Appelle : {urlApi}/games?key=...&search=...&page_size=20
    public List<RawgJeu> rechercher(String nom) {
        if (cleApi == null || cleApi.isBlank()) {
            throw new IllegalStateException(
                    "Clé API RAWG manquante : définis la variable d'environnement RAWG_API_KEY.");
        }

        log.info("Appel API RAWG pour la recherche \"{}\"", nom);
        RawgReponse reponse = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/games")
                        .queryParam("key", cleApi)
                        .queryParam("search", nom)
                        .queryParam("page_size", 20)
                        .build())
                .retrieve()
                // Jackson transforme le JSON reçu en objet RawgReponse.
                .body(RawgReponse.class);

        if (reponse == null || reponse.results() == null) {
            return List.of();
        }
        return reponse.results();
    }
}