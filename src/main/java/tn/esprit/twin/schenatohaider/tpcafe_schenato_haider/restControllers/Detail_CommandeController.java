package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.restControllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.Detail_CommandeRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.Detail_CommandeResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services.IDetail_CommandeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/detailscommande")
public class Detail_CommandeController {

    private final IDetail_CommandeService detail_CommandeService;

    // ✅ Méthodes CRUD existantes
    @GetMapping
    public List<Detail_CommandeResponse> selectAllDetailCommandes() {
        return detail_CommandeService.selectAllDetailCommandes();
    }

    @GetMapping("/{id}")
    public Detail_CommandeResponse selectDetailCommandeById(@PathVariable Long id) {
        return detail_CommandeService.selectDetailCommandeById(id);
    }

    @PostMapping
    public Detail_CommandeResponse addDetailCommande(@RequestBody Detail_CommandeRequest request) {
        return detail_CommandeService.addDetailCommande(request);
    }

    @PostMapping("/batch")
    public List<Detail_CommandeResponse> addDetailCommandes(@RequestBody List<Detail_CommandeRequest> requests) {
        return detail_CommandeService.saveDetailCommandes(requests);
    }

    @DeleteMapping("/{id}")
    public void deleteDetailCommandeById(@PathVariable Long id) {
        detail_CommandeService.deleteDetailCommandeById(id);
    }

    @DeleteMapping
    public void deleteAllDetailCommandes() {
        detail_CommandeService.deleteAllDetailCommandes();
    }

    // ✅ Nouvelles endpoints de recherche

    @GetMapping("/quantite/{quantite}")
    public List<Detail_CommandeResponse> findByQuantite(@PathVariable Integer quantite) {
        return detail_CommandeService.findByQuantite(quantite);
    }

    @GetMapping("/sous-total/{sousTotal}")
    public List<Detail_CommandeResponse> findBySousTotal(@PathVariable Float sousTotal) {
        return detail_CommandeService.findBySousTotal(sousTotal);
    }

    @GetMapping("/count/quantite-superieure/{quantite}")
    public long countByQuantiteGreaterThan(@PathVariable Integer quantite) {
        return detail_CommandeService.countByQuantiteGreaterThan(quantite);
    }

    @GetMapping("/exists/sous-total-superieur/{sousTotalMin}")
    public boolean existsBySousTotalGreaterThan(@PathVariable Float sousTotalMin) {
        return detail_CommandeService.existsBySousTotalGreaterThan(sousTotalMin);
    }

    @GetMapping("/recherche-quantite-soustotal")
    public List<Detail_CommandeResponse> findByQuantiteBetweenAndSousTotalGreaterThanEqual(
            @RequestParam Integer quantiteMin,
            @RequestParam Integer quantiteMax,
            @RequestParam Float sousTotalMin) {
        return detail_CommandeService.findByQuantiteBetweenAndSousTotalGreaterThanEqual(quantiteMin, quantiteMax, sousTotalMin);
    }

    @GetMapping("/sous-total-entre/tri-quantite")
    public List<Detail_CommandeResponse> findBySousTotalBetweenOrderByQuantite(
            @RequestParam Float sousTotalMin,
            @RequestParam Float sousTotalMax) {
        return detail_CommandeService.findBySousTotalBetweenOrderByQuantite(sousTotalMin, sousTotalMax);
    }

    @GetMapping("/sous-total-promo-entre")
    public List<Detail_CommandeResponse> findBySousTotalApresPromoBetween(
            @RequestParam Float min,
            @RequestParam Float max) {
        return detail_CommandeService.findBySousTotalApresPromoBetween(min, max);
    }

    @GetMapping("/quantite-ou-soustotal")
    public List<Detail_CommandeResponse> findByQuantiteOrSousTotalGreaterThanEqual(
            @RequestParam Integer quantite,
            @RequestParam Float sousTotalMin) {
        return detail_CommandeService.findByQuantiteOrSousTotalGreaterThanEqual(quantite, sousTotalMin);
    }

    @GetMapping("/top-5-chers")
    public List<Detail_CommandeResponse> findTop5ByOrderBySousTotalDesc() {
        return detail_CommandeService.findTop5ByOrderBySousTotalDesc();
    }

    @GetMapping("/sans-quantite")
    public List<Detail_CommandeResponse> findByQuantiteIsNull() {
        return detail_CommandeService.findByQuantiteIsNull();
    }

    @GetMapping("/avec-soustotal-promo")
    public List<Detail_CommandeResponse> findBySousTotalApresPromoIsNotNull() {
        return detail_CommandeService.findBySousTotalApresPromoIsNotNull();
    }

    @GetMapping("/avec-commande-article")
    public List<Detail_CommandeResponse> findDetailsWithCommandeAndArticle() {
        return detail_CommandeService.findDetailsWithCommandeAndArticle();
    }
}