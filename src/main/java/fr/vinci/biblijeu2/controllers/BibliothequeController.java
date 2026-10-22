package fr.vinci.biblijeu2.controllers;

import fr.vinci.biblijeu2.services.BibliothequeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Toutes les adresses de ce contrôleur commencent par /bibliotheque.
@Controller
@RequestMapping("/bibliotheque")
public class BibliothequeController {

    private final BibliothequeService bibliothequeService;

    public BibliothequeController(BibliothequeService bibliothequeService) {
        this.bibliothequeService = bibliothequeService;
    }

    // Page bibliothèque : http://localhost:8080/bibliotheque
    @GetMapping
    public String afficher(Model model) {
        model.addAttribute("jeux", bibliothequeService.lister());
        return "bibliotheque"; // templates/bibliotheque.html
    }

    // Reçoit le formulaire de la page d'accueil.
    // POST on modifie des données.
    @PostMapping("/ajouter")
    public String ajouter(@RequestParam("rawgId") Long rawgId,
                          @RequestParam("nomJeu") String nomJeu,
                          @RequestParam(value = "imageUrl", required = false) String imageUrl,
                          @RequestParam(value = "dateSortie", required = false) String dateSortie,
                          @RequestParam(value = "note", required = false) Double note,
                          @RequestParam(value = "nom", required = false) String nom,
                          RedirectAttributes redirectAttributes) {
        boolean ajoute = bibliothequeService.ajouter(rawgId, nomJeu, imageUrl, dateSortie, note);

        redirectAttributes.addFlashAttribute("message",
                ajoute ? nomJeu + " a été ajouté à ta bibliothèque."
                        : nomJeu + " est déjà dans ta bibliothèque.");
        redirectAttributes.addAttribute("nom", nom);

        return "redirect:/";
    }

    // Bouton "Supprimer" : POST /bibliotheque/3/supprimer
    @PostMapping("/{id}/supprimer")
    public String supprimer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bibliothequeService.supprimer(id);
        redirectAttributes.addFlashAttribute("message", "Jeu retiré de ta bibliothèque.");
        return "redirect:/bibliotheque";
    }
}