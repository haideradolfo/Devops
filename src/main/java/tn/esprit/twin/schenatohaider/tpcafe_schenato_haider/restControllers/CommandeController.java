package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.restControllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.CommandeRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.CommandeResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Commande;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services.ICommandeService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/commandes")
public class CommandeController {

    private final ICommandeService commandeService;

    // ✅ Méthodes CRUD existantes
    @GetMapping
    public List<CommandeResponse> selectAllCommandes() {
        return commandeService.selectAllCommandes();
    }

    @GetMapping("/{id}")
    public CommandeResponse selectCommandeById(@PathVariable Long id) {
        return commandeService.selectCommandeById(id);
    }

    @PostMapping
    public CommandeResponse addCommande(@RequestBody CommandeRequest request) {
        return commandeService.addCommande(request);
    }

    @PostMapping("/batch")
    public List<CommandeResponse> saveCommandes(@RequestBody List<CommandeRequest> requests) {
        return commandeService.saveCommandes(requests);
    }

    @DeleteMapping("/{id}")
    public void deleteCommandeById(@PathVariable Long id) {
        commandeService.deleteCommandeById(id);
    }

    @DeleteMapping
    public void deleteAllCommandes() {
        commandeService.deleteAllCommandes();
    }

    @GetMapping("/count")
    public long countCommandes() {
        return commandeService.countCommandes();
    }

    @GetMapping("/exists/{id}")
    public boolean verifCommandeById(@PathVariable Long id) {
        return commandeService.verifCommandeById(id);
    }

    // ✅ Nouvelles endpoints de recherche

    @GetMapping("/statut/{statut}")
    public List<CommandeResponse> findByStatusCommande(@PathVariable String statut) {
        return commandeService.findByStatusCommande(statut);
    }

    @GetMapping("/date/{date}")
    public List<CommandeResponse> findByDateCommande(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return commandeService.findByDateCommande(date);
    }

    @GetMapping("/count/statut/{statut}")
    public long countByStatusCommande(@PathVariable String statut) {
        return commandeService.countByStatusCommande(statut);
    }

    @DeleteMapping("/date-avant/{date}")
    public void deleteByDateCommandeBefore(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        commandeService.deleteByDateCommandeBefore(date);
    }

    @GetMapping("/date-entre-statut")
    public List<CommandeResponse> findByDateCommandeBetweenAndStatusCommande(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
            @RequestParam String statut) {
        return commandeService.findByDateCommandeBetweenAndStatusCommande(dateDebut, dateFin, statut);
    }

    @GetMapping("/total-superieur-statut-different")
    public List<CommandeResponse> findByTotalCommandeGreaterThanAndStatusCommandeNot(
            @RequestParam Float montant,
            @RequestParam String statut) {
        return commandeService.findByTotalCommandeGreaterThanAndStatusCommandeNot(montant, statut);
    }

    @GetMapping("/statuts/tri-date")
    public List<CommandeResponse> findByStatusCommandeInOrderByDateCommande(@RequestParam List<String> statuts) {
        return commandeService.findByStatusCommandeInOrderByDateCommande(statuts);
    }

    @GetMapping("/date-avant-total-entre")
    public List<CommandeResponse> findByDateCommandeBeforeAndTotalCommandeBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam Float minTotal,
            @RequestParam Float maxTotal) {
        return commandeService.findByDateCommandeBeforeAndTotalCommandeBetween(date, minTotal, maxTotal);
    }

    @GetMapping("/statut-termine-par/{lettre}")
    public List<CommandeResponse> findByStatusCommandeEndingWith(@PathVariable String lettre) {
        return commandeService.findByStatusCommandeEndingWith(lettre);
    }

    @GetMapping("/sans-statut")
    public List<CommandeResponse> findByStatusCommandeIsNull() {
        return commandeService.findByStatusCommandeIsNull();
    }

    @GetMapping("/avec-total")
    public List<CommandeResponse> findByTotalCommandeIsNotNull() {
        return commandeService.findByTotalCommandeIsNotNull();
    }

    @GetMapping("/avec-details-client")
    public List<CommandeResponse> findCommandesWithDetailsAndClient() {
        return commandeService.findCommandesWithDetailsAndClient();
    }

    @GetMapping("/top-3-recentes")
    public List<CommandeResponse> findTop3ByOrderByDateCommandeDesc() {
        return commandeService.findTop3ByOrderByDateCommandeDesc();
    }

    @PostMapping("/ajouter-et-affecter-client")
    public void ajouterCommandeEtAffecterAClient(@RequestBody Commande commande, @RequestParam String nomC, @RequestParam String prenomC) {
        commandeService.ajouterCommandeEtAffecterAClient(commande, nomC, prenomC);
    }
}