package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.restControllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.PromotionRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.PromotionResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Promotion;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services.IPromotionService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/promotions")
public class PromotionController {

    private final IPromotionService promotionService;

    // ✅ Méthodes CRUD existantes
    @GetMapping
    public List<PromotionResponse> selectAllPromotions() {
        return promotionService.selectAllPromotions();
    }

    @GetMapping("/{id}")
    public PromotionResponse selectPromotionById(@PathVariable Long id) {
        return promotionService.selectPromotionById(id);
    }

    @PostMapping
    public PromotionResponse addPromotion(@RequestBody PromotionRequest request) {
        return promotionService.addPromotion(request);
    }

    @PostMapping("/batch")
    public List<PromotionResponse> addPromotions(@RequestBody List<PromotionRequest> requests) {
        return promotionService.savePromotions(requests);
    }

    @DeleteMapping("/{id}")
    public void deletePromotionById(@PathVariable Long id) {
        promotionService.deletePromotionById(id);
    }

    @DeleteMapping
    public void deleteAllPromotions() {
        promotionService.deleteAllPromotions();
    }

    @GetMapping("/count")
    public long countPromotions() {
        return promotionService.countPromotions();
    }

    @GetMapping("/exists/{id}")
    public boolean verifPromotionById(@PathVariable Long id) {
        return promotionService.verifPromotionById(id);
    }

    // ✅ Nouvelles endpoints de recherche

    @GetMapping("/pourcentage/{pourcentage}")
    public List<PromotionResponse> findByPourcentagePromo(@PathVariable String pourcentage) {
        return promotionService.findByPourcentagePromo(pourcentage);
    }

    @GetMapping("/date-debut/{dateDebut}")
    public List<PromotionResponse> findByDateDebutPromo(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut) {
        return promotionService.findByDateDebutPromo(dateDebut);
    }

    @GetMapping("/date-fin/{dateFin}")
    public List<PromotionResponse> findByDateFinPromo(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return promotionService.findByDateFinPromo(dateFin);
    }

    @GetMapping("/exists/pourcentage/{pourcentage}")
    public boolean existsByPourcentagePromo(@PathVariable String pourcentage) {
        return promotionService.existsByPourcentagePromo(pourcentage);
    }

    @GetMapping("/count/debut-apres/{date}")
    public long countByDateDebutPromoAfter(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return promotionService.countByDateDebutPromoAfter(date);
    }

    @GetMapping("/actives/{date}")
    public List<PromotionResponse> findActivePromotionsAtDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return promotionService.findActivePromotionsAtDate(date);
    }

    @GetMapping("/recherche-pourcentage-periode")
    public List<PromotionResponse> findByPourcentagePromoAndDateDebutPromoBetween(
            @RequestParam String pourcentage,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return promotionService.findByPourcentagePromoAndDateDebutPromoBetween(pourcentage, startDate, endDate);
    }

    @GetMapping("/valides/{date}")
    public List<PromotionResponse> findValidPromotionsAtDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return promotionService.findValidPromotionsAtDate(date);
    }

    @GetMapping("/pourcentages/tri-date-debut")
    public List<PromotionResponse> findByPourcentagePromoInOrderByDateDebutPromo(@RequestParam List<String> pourcentages) {
        return promotionService.findByPourcentagePromoInOrderByDateDebutPromo(pourcentages);
    }

    @GetMapping("/actives/tri-pourcentage")
    public List<PromotionResponse> findActivePromotionsOrderByPourcentagePromo() {
        return promotionService.findActivePromotionsOrderByPourcentagePromo();
    }

    @GetMapping("/sans-date-fin")
    public List<PromotionResponse> findByDateFinPromoIsNull() {
        return promotionService.findByDateFinPromoIsNull();
    }

    @GetMapping("/avec-pourcentage")
    public List<PromotionResponse> findByPourcentagePromoIsNotNull() {
        return promotionService.findByPourcentagePromoIsNotNull();
    }

    @GetMapping("/avec-articles")
    public List<PromotionResponse> findPromotionsWithArticles() {
        return promotionService.findPromotionsWithArticles();
    }

    @GetMapping("/expirees")
    public List<PromotionResponse> findExpiredPromotions() {
        return promotionService.findExpiredPromotions();
    }



    @PostMapping("/nouvelle-et-affecter/{idArticle}")
    public void ajouterPromoEtAffecterAArticle(@RequestBody Promotion promotion, @PathVariable Long idArticle) {
        promotionService.ajouterPromoEtAffecterAArticle(promotion, idArticle);
    }

    // Méthodes existantes (garder telles quelles)
    @PutMapping("/affecter/{idArticle}/{idPromotion}")
    public List<PromotionResponse> affecterPromotionAArticle(@PathVariable Long idArticle, @PathVariable Long idPromotion) {
        return promotionService.affecterPromotionAArticle(idArticle, idPromotion);
    }

    @PutMapping("/desaffecter/{idArticle}/{idPromotion}")
    public List<PromotionResponse> desaffecterPromotionAArticle(@PathVariable Long idArticle, @PathVariable Long idPromotion) {
        return promotionService.desaffecterPromotionAArticle(idArticle, idPromotion);
    }
}