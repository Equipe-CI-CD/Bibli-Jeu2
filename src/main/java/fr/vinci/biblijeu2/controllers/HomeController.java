package fr.vinci.biblijeu2.controllers;

import fr.vinci.biblijeu2.services.BibliothequeService;
import fr.vinci.biblijeu2.services.RawgService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

// @Controller (et pas @RestController) : les méthodes renvoient le NOM d'une page
// Thymeleaf, que Spring va chercher dans src/main/resources/templates.
@Controller
public class HomeController {

    private final RawgService rawgService;
    private final BibliothequeService bibliothequeService;

    public HomeController(RawgService rawgService, BibliothequeService bibliothequeService) {
        this.rawgService = rawgService;
        this.bibliothequeService = bibliothequeService;
    }

    // Page d'accueil : http://localhost:8080/
    // Avec une recherche : http://localhost:8080/?nom=zelda
    // required = false : sans le paramètre "nom", on affiche juste le formulaire.
    @GetMapping("/")
    public String accueil(@RequestParam(value = "nom", required = false) String nom, Model model) {
        model.addAttribute("nom", nom);

        if (nom != null && !nom.isBlank()) {
            try {
                model.addAttribute("resultats", rawgService.rechercher(nom.trim()));
                model.addAttribute("dejaAjoutes", bibliothequeService.getRawgIdsEnregistres());
            } catch (IllegalStateException e) {
                // Clé API absente.
                model.addAttribute("erreur", e.getMessage());
            } catch (RestClientException e) {
                // RAWG injoignable, clé invalide...
                model.addAttribute("erreur", "Impossible de contacter RAWG : " + e.getMessage());
            }
        }
        return "index"; // -> templates/index.html
    }
}