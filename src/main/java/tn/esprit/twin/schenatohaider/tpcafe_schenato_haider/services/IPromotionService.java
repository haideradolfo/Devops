package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services;

import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.PromotionRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.PromotionResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Promotion;

import java.time.LocalDate;
import java.util.List;

public interface IPromotionService {

    // ✅ Méthodes CRUD existantes
    PromotionResponse addPromotion(PromotionRequest request);
    List<PromotionResponse> savePromotions(List<PromotionRequest> requests);
    PromotionResponse selectPromotionById(Long id);
    List<PromotionResponse> selectAllPromotions();
    void deletePromotionById(Long id);
    void deleteAllPromotions();
    long countPromotions();
    boolean verifPromotionById(Long id);

    // ✅ Nouvelles méthodes de recherche
    List<PromotionResponse> findByPourcentagePromo(String pourcentage);
    List<PromotionResponse> findByDateDebutPromo(LocalDate dateDebut);
    List<PromotionResponse> findByDateFinPromo(LocalDate dateFin);
    boolean existsByPourcentagePromo(String pourcentage);
    long countByDateDebutPromoAfter(LocalDate date);
    List<PromotionResponse> findActivePromotionsAtDate(LocalDate date);
    List<PromotionResponse> findByPourcentagePromoAndDateDebutPromoBetween(String pourcentage, LocalDate startDate, LocalDate endDate);
    List<PromotionResponse> findValidPromotionsAtDate(LocalDate date);
    List<PromotionResponse> findByPourcentagePromoInOrderByDateDebutPromo(List<String> pourcentages);
    List<PromotionResponse> findActivePromotionsOrderByPourcentagePromo();
    List<PromotionResponse> findByDateFinPromoIsNull();
    List<PromotionResponse> findByPourcentagePromoIsNotNull();
    List<PromotionResponse> findPromotionsWithArticles();
    List<PromotionResponse> findExpiredPromotions();

    List<PromotionResponse> affecterPromotionAArticle(long idArticle, long idPromotion);
    List<PromotionResponse> desaffecterPromotionAArticle(long idArticle, long idPromotion);

    void ajouterPromoEtAffecterAArticle(Promotion p, long idArticle);
}