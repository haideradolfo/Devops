package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.restControllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.ClientRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.ClientResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Client;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Commande;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services.IClientService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final IClientService clientService;

    // ✅ Méthodes CRUD existantes
    @GetMapping
    public List<ClientResponse> selectAllClients() {
        return clientService.selectAllClients();
    }

    @GetMapping("/{id}")
    public ClientResponse selectClientById(@PathVariable Long id) {
        return clientService.selectClientById(id);
    }

    @PostMapping
    public ClientResponse addClient(@RequestBody ClientRequest request) {
        return clientService.addClient(request);
    }

    @PostMapping("/batch")
    public List<ClientResponse> saveClients(@RequestBody List<ClientRequest> requests) {
        return clientService.saveClients(requests);
    }

    @DeleteMapping("/{id}")
    public void deleteClientById(@PathVariable Long id) {
        clientService.deleteClientById(id);
    }

    @DeleteMapping
    public void deleteAllClients() {
        clientService.deleteAllClients();
    }

    @GetMapping("/count")
    public long countClients() {
        return clientService.countingClients();
    }

    @GetMapping("/exists/{id}")
    public boolean verifClientById(@PathVariable Long id) {
        return clientService.verifClientById(id);
    }

    // ✅ Nouvelles endpoints de recherche

    @GetMapping("/nom/{nom}")
    public List<ClientResponse> findByNom(@PathVariable String nom) {
        return clientService.findByNom(nom);
    }

    @GetMapping("/prenom/{prenom}")
    public List<ClientResponse> findByPrenom(@PathVariable String prenom) {
        return clientService.findByPrenom(prenom);
    }

    @GetMapping("/nom/{nom}/prenom/{prenom}")
    public List<ClientResponse> findByNomAndPrenom(@PathVariable String nom, @PathVariable String prenom) {
        return clientService.findByNomAndPrenom(nom, prenom);
    }

    @GetMapping("/exists/nom/{nom}")
    public boolean existsByNom(@PathVariable String nom) {
        return clientService.existsByNom(nom);
    }

    @GetMapping("/count/nes-apres/{date}")
    public long countByDateNaissanceAfter(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return clientService.countByDateNaissanceAfter(date);
    }

    @GetMapping("/recherche/or")
    public List<ClientResponse> findByNomContainingOrPrenomContaining(
            @RequestParam String nom,
            @RequestParam String prenom) {
        return clientService.findByNomContainingOrPrenomContaining(nom, prenom);
    }

    @GetMapping("/recherche/and")
    public List<ClientResponse> findByNomContainingAndPrenomContaining(@RequestParam String chaine) {
        return clientService.findByNomContainingAndPrenomContaining(chaine);
    }

    @GetMapping("/date-naissance/entre")
    public List<ClientResponse> findByDateNaissanceBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return clientService.findByDateNaissanceBetween(debut, fin);
    }

    @GetMapping("/nom-commence/{debutNom}/nes-avant/{date}")
    public List<ClientResponse> findByNomStartingWithAndDateNaissanceBefore(
            @PathVariable String debutNom,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return clientService.findByNomStartingWithAndDateNaissanceBefore(debutNom, date);
    }

    @GetMapping("/ville/{ville}")
    public List<ClientResponse> findByAdresseVille(@PathVariable String ville) {
        return clientService.findByAdresseVille(ville);
    }

    @GetMapping("/nom-contient/{chaine}/tri-prenom-asc")
    public List<ClientResponse> findByNomContainingOrderByPrenomAsc(@PathVariable String chaine) {
        return clientService.findByNomContainingOrderByPrenomAsc(chaine);
    }

    @GetMapping("/nom-contient/{chaine}/tri-prenom-desc")
    public List<ClientResponse> findByNomContainingOrderByPrenomDesc(@PathVariable String chaine) {
        return clientService.findByNomContainingOrderByPrenomDesc(chaine);
    }

    @GetMapping("/nom-commence-par/{lettre}")
    public List<ClientResponse> findByNomStartingWith(@PathVariable String lettre) {
        return clientService.findByNomStartingWith(lettre);
    }

    @GetMapping("/prenom-termine-par/{terminaison}")
    public List<ClientResponse> findByPrenomEndingWith(@PathVariable String terminaison) {
        return clientService.findByPrenomEndingWith(terminaison);
    }

    @GetMapping("/sans-date-naissance")
    public List<ClientResponse> findByDateNaissanceIsNull() {
        return clientService.findByDateNaissanceIsNull();
    }

    @GetMapping("/avec-adresse")
    public List<ClientResponse> findByAdresseIsNotNull() {
        return clientService.findByAdresseIsNotNull();
    }

    @GetMapping("/villes")
    public List<ClientResponse> findByAdresseVilleIn(@RequestParam List<String> villes) {
        return clientService.findByAdresseVilleIn(villes);
    }

    @GetMapping("/points-fidelite/superieur/{valeur}")
    public List<ClientResponse> findByCarteFidelitePtsAccumulesGreaterThan(@PathVariable Integer valeur) {
        return clientService.findByCarteFidelitePtsAccumulesGreaterThan(valeur);
    }

    @GetMapping("/points-fidelite/superieur-egal/{valeur}")
    public List<ClientResponse> findByCarteFidelitePtsAccumulesGreaterThanEqual(@PathVariable Integer valeur) {
        return clientService.findByCarteFidelitePtsAccumulesGreaterThanEqual(valeur);
    }

    @GetMapping("/points-fidelite/entre")
    public List<ClientResponse> findByCarteFidelitePtsAccumulesBetween(
            @RequestParam Integer min,
            @RequestParam Integer max) {
        return clientService.findByCarteFidelitePtsAccumulesBetween(min, max);
    }

    @GetMapping("/commandes/article/{nomArticle}")
    public List<ClientResponse> findClientsByArticleNom(@PathVariable String nomArticle) {
        return clientService.findClientsByArticleNom(nomArticle);
    }

    @GetMapping("/recherche-avancee")
    public List<ClientResponse> findByNomContainingAndArticleType(
            @RequestParam String chaineNom,
            @RequestParam String typeArticle) {
        return clientService.findByNomContainingAndArticleType(chaineNom, typeArticle);
    }

    @PostMapping("/affecter-adresse")
    public String affecterAdresseAClient(@RequestParam String rue, @RequestParam long cin) {
        return clientService.affecterAdresseAClient(rue, cin);
    }

    @PutMapping("/affecter-carte")
    public void affecterCarteAClient(@RequestParam long idCarte, @RequestParam long idClient) {
        clientService.affecterCarteAClient(idCarte, idClient);
    }

    @PutMapping("/affecter-commande")
    public void affecterCommandeAClient(@RequestParam long idCommande, @RequestParam long idClient) {
        clientService.affecterCommandeAClient(idCommande, idClient);
    }

    @PutMapping("/affecter-commande-par-nom")
    public void affecterCommandeAClient(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateCommande,
            @RequestParam String nomClient,
            @RequestParam String prenomClient) {
        clientService.affecterCommandeAClient(dateCommande, nomClient, prenomClient);
    }

    @PutMapping("/desaffecter-commande/{idCommande}")
    public void desaffecterClientDeCommande(@PathVariable long idCommande) {
        clientService.desaffecterClientDeCommande(idCommande);
    }

    @PostMapping("/avec-carte-fidelite")
    public Client ajouterClientEtCarteFidelite(@RequestBody Client client) {
        return clientService.ajouterClientEtCarteFidelite(client);
    }

    @PostMapping("/ajouter-et-affecter-commande")
    public void ajouterCommandeEtAffecterAClient(@RequestBody Commande commande, @RequestParam String nom, @RequestParam String prenom) {
        clientService.ajouterCommandeEtAffecterAClient(commande, nom, prenom);
    }
}