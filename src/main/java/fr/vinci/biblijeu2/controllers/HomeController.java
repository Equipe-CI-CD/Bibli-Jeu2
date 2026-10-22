package fr.vinci.biblijeu2.controllers;

import fr.vinci.biblijeu2.services.BibliothequeService;
import fr.vinci.biblijeu2.services.RawgService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

@Controller
public class HomeController {

    private final RawgService rawgService;
    private final BibliothequeService bibliothequeService;

    public HomeController(RawgService rawgService, BibliothequeService bibliothequeService) {
        this.rawgService = rawgService;
        this.bibliothequeService = bibliothequeService;
    }

    // url accueil : http://localhost:8080/
    // url recherche : http://localhost:8080/?nom=zelda
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
        return "index"; // templates/index.html
    }
}