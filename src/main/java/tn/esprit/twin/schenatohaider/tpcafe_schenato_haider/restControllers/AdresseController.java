package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.restControllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.AdresseRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.AdresseResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Adresse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Client;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services.IAdresseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/adresses")
public class AdresseController {

    private final IAdresseService adresseService;

    // ✅ Méthodes CRUD existantes
    @GetMapping
    public List<AdresseResponse> selectAllAdresses() {
        return adresseService.selectAllAdresses();
    }

    @GetMapping("/{id}")
    public AdresseResponse selectAdresseById(@PathVariable Long id) {
        return adresseService.selectAdresseById(id);
    }

    @PostMapping
    public AdresseResponse addAdresse(@RequestBody AdresseRequest request) {
        return adresseService.addAdresse(request);
    }

    @PostMapping("/batch")
    public List<AdresseResponse> saveAdresses(@RequestBody List<AdresseRequest> requests) {
        return adresseService.saveAdresses(requests);
    }

    @DeleteMapping("/{id}")
    public void deleteAdresseById(@PathVariable Long id) {
        adresseService.deleteAdresseById(id);
    }

    @DeleteMapping
    public void deleteAllAdresses() {
        adresseService.deleteAllAdresses();
    }

    @GetMapping("/count")
    public long countAdresses() {
        return adresseService.countAdresses();
    }

    @GetMapping("/exists/{id}")
    public boolean verifAdresseById(@PathVariable Long id) {
        return adresseService.verifAdresseById(id);
    }

    // ✅ Nouvelles endpoints de recherche (avec Integer pour codePostal)

    @GetMapping("/ville/{ville}")
    public List<AdresseResponse> findByVille(@PathVariable String ville) {
        return adresseService.findByVille(ville);
    }

    @GetMapping("/code-postal/{codePostal}")
    public List<AdresseResponse> findByCodePostal(@PathVariable Integer codePostal) { // ✅ Integer
        return adresseService.findByCodePostal(codePostal);
    }

    @GetMapping("/ville/{ville}/count")
    public long countByVille(@PathVariable String ville) {
        return adresseService.countByVille(ville);
    }

    @DeleteMapping("/ville/{ville}")
    public void deleteByVille(@PathVariable String ville) {
        adresseService.deleteByVille(ville);
    }

    @GetMapping("/ville/{ville}/code-postal/{codePostal}")
    public List<AdresseResponse> findByVilleAndCodePostal(
            @PathVariable String ville,
            @PathVariable Integer codePostal) { // ✅ Integer
        return adresseService.findByVilleAndCodePostal(ville, codePostal);
    }

    @GetMapping("/recherche")
    public List<AdresseResponse> findByRueContainingIgnoreCaseAndVilleIgnoreCase(
            @RequestParam String mot,
            @RequestParam String ville) {
        return adresseService.findByRueContainingIgnoreCaseAndVilleIgnoreCase(mot, ville);
    }

    @GetMapping("/villes")
    public List<AdresseResponse> findByVilleIn(@RequestParam List<String> villes) {
        return adresseService.findByVilleIn(villes);
    }

    @GetMapping("/code-postal/plage")
    public List<AdresseResponse> findByCodePostalBetween(
            @RequestParam Integer debut,  // ✅ Integer
            @RequestParam Integer fin) {  // ✅ Integer
        return adresseService.findByCodePostalBetween(debut, fin);
    }

    @GetMapping("/code-postal/superieur/{codePostal}")
    public List<AdresseResponse> findByCodePostalGreaterThan(@PathVariable Integer codePostal) { // ✅ Integer
        return adresseService.findByCodePostalGreaterThan(codePostal);
    }

    @GetMapping("/code-postal/superieur-egal/{codePostal}")
    public List<AdresseResponse> findByCodePostalGreaterThanEqual(@PathVariable Integer codePostal) { // ✅ Integer
        return adresseService.findByCodePostalGreaterThanEqual(codePostal);
    }

    @GetMapping("/code-postal/inferieur/{codePostal}")
    public List<AdresseResponse> findByCodePostalLessThan(@PathVariable Integer codePostal) { // ✅ Integer
        return adresseService.findByCodePostalLessThan(codePostal);
    }

    @GetMapping("/code-postal/inferieur-egal/{codePostal}")
    public List<AdresseResponse> findByCodePostalLessThanEqual(@PathVariable Integer codePostal) { // ✅ Integer
        return adresseService.findByCodePostalLessThanEqual(codePostal);
    }

    @GetMapping("/rue-commence")
    public List<AdresseResponse> findByRueStartingWithAndVilleOrderByCodePostal(
            @RequestParam String debutRue,
            @RequestParam String ville) {
        return adresseService.findByRueStartingWithAndVilleOrderByCodePostal(debutRue, ville);
    }

    @GetMapping("/rue-commence-par")
    public List<AdresseResponse> findByRueStartingWith(@RequestParam String debutRue) {
        return adresseService.findByRueStartingWith(debutRue);
    }

    @GetMapping("/ville-termine-par")
    public List<AdresseResponse> findByVilleEndingWith(@RequestParam String finVille) {
        return adresseService.findByVilleEndingWith(finVille);
    }

    @GetMapping("/rue-null")
    public List<AdresseResponse> findByRueIsNull() {
        return adresseService.findByRueIsNull();
    }

    @GetMapping("/ville-not-null")
    public List<AdresseResponse> findByVilleIsNotNull() {
        return adresseService.findByVilleIsNotNull();
    }

    @PostMapping("/affecter-client/{idClient}")
    public void ajouterEtAffecterAdresseAClient(@RequestBody Adresse ad, @PathVariable Long idClient) {
        // Créer un client avec seulement l'ID (ou le récupérer de la base)
        Client client = new Client();
        client.setIdClient(idClient);

        adresseService.ajouterEtAffecterAdresseAClient(ad, client);
    }

}