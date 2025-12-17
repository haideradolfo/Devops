package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.PromotionRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.PromotionResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Article;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Promotion;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.mapper.PromotionMapper;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories.ArticleRepository;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories.PromotionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PromotionService implements IPromotionService {

    private final PromotionRepository promotionRepository;
    private final ArticleRepository articleRepository;
    private final PromotionMapper promotionMapper;

    // Méthodes CRUD existantes (garder telles quelles)
    @Override
    public PromotionResponse addPromotion(PromotionRequest request) {
        try {
            System.out.println("=== DEBUG PromotionService ===");
            System.out.println("Request received: " + request);

            Promotion promotion = promotionMapper.toEntity(request);
            System.out.println("Promotion after mapping: " + promotion);

            // Gérer la relation ManyToMany avec les articles
            if (request.getArticleIds() != null && !request.getArticleIds().isEmpty()) {
                List<Article> articles = articleRepository.findAllById(request.getArticleIds());
                promotion.setArticles(articles);
                System.out.println("Articles set: " + articles);
            }

            Promotion saved = promotionRepository.save(promotion);
            System.out.println("Promotion after save: " + saved);

            PromotionResponse response = promotionMapper.toDto(saved);
            System.out.println("Response: " + response);
            System.out.println("=== END DEBUG ===");

            return response;
        } catch (Exception e) {
            System.err.println("Error in addPromotion: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<PromotionResponse> savePromotions(List<PromotionRequest> requests) {
        return requests.stream()
                .map(this::addPromotion)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PromotionResponse selectPromotionById(Long id) {
        return promotionRepository.findById(id)
                .map(promotionMapper::toDto)
                .orElseGet(() -> PromotionResponse.builder()
                        .idPromotion(null)
                        .pourcentagePromo("0%")
                        .dateDebutPromo(null)
                        .dateFinPromo(null)
                        .articles(List.of())
                        .build());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionResponse> selectAllPromotions() {
        return promotionRepository.findAll().stream()
                .map(promotionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePromotionById(Long id) {
        promotionRepository.deleteById(id);
    }

    @Override
    public void deleteAllPromotions() {
        promotionRepository.deleteAll();
    }

    @Override
    @Transactional(readOnly = true)
    public long countPromotions() {
        return promotionRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifPromotionById(Long id) {
        return promotionRepository.existsById(id);
    }

    // ✅ Nouvelles méthodes de recherche

    @Override
    @Transactional(readOnly = true)
    public List<PromotionResponse> findByPourcentagePromo(String pourcentage) {
        return promotionRepository.findByPourcentagePromo(pourcentage).stream()
                .map(promotionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionResponse> findByDateDebutPromo(LocalDate dateDebut) {
        return promotionRepository.findByDateDebutPromo(dateDebut).stream()
                .map(promotionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionResponse> findByDateFinPromo(LocalDate dateFin) {
        return promotionRepository.findByDateFinPromo(dateFin).stream()
                .map(promotionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByPourcentagePromo(String pourcentage) {
        return promotionRepository.existsByPourcentagePromo(pourcentage);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByDateDebutPromoAfter(LocalDate date) {
        return promotionRepository.countByDateDebutPromoAfter(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionResponse> findActivePromotionsAtDate(LocalDate date) {
        return promotionRepository.findActivePromotionsAtDate(date).stream()
                .map(promotionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionResponse> findByPourcentagePromoAndDateDebutPromoBetween(String pourcentage, LocalDate startDate, LocalDate endDate) {
        return promotionRepository.findByPourcentagePromoAndDateDebutPromoBetween(pourcentage, startDate, endDate).stream()
                .map(promotionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionResponse> findValidPromotionsAtDate(LocalDate date) {
        return promotionRepository.findValidPromotionsAtDate(date).stream()
                .map(promotionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionResponse> findByPourcentagePromoInOrderByDateDebutPromo(List<String> pourcentages) {
        return promotionRepository.findByPourcentagePromoInOrderByDateDebutPromo(pourcentages).stream()
                .map(promotionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionResponse> findActivePromotionsOrderByPourcentagePromo() {
        return promotionRepository.findActivePromotionsOrderByPourcentagePromo().stream()
                .map(promotionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionResponse> findByDateFinPromoIsNull() {
        return promotionRepository.findByDateFinPromoIsNull().stream()
                .map(promotionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionResponse> findByPourcentagePromoIsNotNull() {
        return promotionRepository.findByPourcentagePromoIsNotNull().stream()
                .map(promotionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionResponse> findPromotionsWithArticles() {
        return promotionRepository.findPromotionsWithArticles().stream()
                .map(promotionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionResponse> findExpiredPromotions() {
        return promotionRepository.findExpiredPromotions().stream()
                .map(promotionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PromotionResponse> affecterPromotionAArticle(long idArticle, long idPromotion) {
        Article article = articleRepository.findById(idArticle).get();
        Promotion promotion = promotionRepository.findById(idPromotion).get();
        article.getPromotions().add(promotion);
        articleRepository.save(article);
        return null;
    }

    @Override
    public List<PromotionResponse> desaffecterPromotionAArticle(long idArticle, long idPromotion) {
        Article article = articleRepository.findById(idArticle).get();
        Promotion promotion = promotionRepository.findById(idPromotion).get();
        article.getPromotions().remove(promotion);
        articleRepository.save(article);
        return null;
    }


    @Override
    @Transactional
    public void ajouterPromoEtAffecterAArticle(Promotion p, long idArticle) {
        Article article = articleRepository.findById(idArticle).get();

        // Sauvegarder la promotion d'abord
        Promotion savedPromo = promotionRepository.save(p);

        // Ajouter la promotion à l'article
        article.getPromotions().add(savedPromo);

        // Sauvegarder l'article
        articleRepository.save(article);
    }
}