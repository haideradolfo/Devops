package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.ArticleRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.ArticleResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Article;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Promotion;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.TypeArticle;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.mapper.ArticleMapper;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories.ArticleRepository;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories.PromotionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService implements IArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final PromotionRepository promotionRepository;

    // Méthodes CRUD existantes (garder telles quelles)
    @Override
    public ArticleResponse addArticle(ArticleRequest request) {
        System.out.println("=== DEBUG START ===");
        System.out.println("Request: " + request);
        System.out.println("Request typeArticle: " + request.getTypeArticle());

        Article article = articleMapper.toEntity(request);
        System.out.println("Article after mapping: " + article);
        System.out.println("Article typeArticle: " + article.getTypeArticle());

        Article saved = articleRepository.save(article);
        System.out.println("Article after save: " + saved);

        ArticleResponse response = articleMapper.toDto(saved);
        System.out.println("Final response: " + response);
        System.out.println("=== DEBUG END ===");

        return response;
    }

    @Override
    public List<ArticleResponse> saveArticles(List<ArticleRequest> requests) {
        List<Article> articles = requests.stream()
                .map(articleMapper::toEntity)
                .collect(Collectors.toList());

        List<Article> saved = articleRepository.saveAll(articles);
        return saved.stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleResponse selectArticleById(long id) {
        return articleRepository.findById(id)
                .map(articleMapper::toDto)
                .orElseGet(() -> ArticleResponse.builder()
                        .idArticle(null)
                        .nomArticle("default nom")
                        .prixArticle(0.0)
                        .typeArticle(null)
                        .build());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> selectAllArticles() {
        return articleRepository.findAll().stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteArticle(long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public void deleteAllArticles() {
        articleRepository.deleteAll();
    }

    @Override
    @Transactional(readOnly = true)
    public long countingArticles() {
        return articleRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifArticleById(long id) {
        return articleRepository.existsById(id);
    }

    // ✅ Nouvelles méthodes de recherche (avec TypeArticle enum)

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> findByNom(String nom) {
        return articleRepository.findByNom(nom).stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> findByType(TypeArticle type) {
        return articleRepository.findByType(type).stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> findByPrix(Double prix) {
        return articleRepository.findByPrix(prix).stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNom(String nom) {
        return articleRepository.existsByNom(nom);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByType(TypeArticle type) {
        return articleRepository.countByType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> findByNomContainingAndType(String mot, TypeArticle type) {
        return articleRepository.findByNomContainingAndType(mot, type).stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> findByPrixBetweenAndTypeIn(Double prixMin, Double prixMax, List<TypeArticle> types) {
        return articleRepository.findByPrixBetweenAndTypeIn(prixMin, prixMax, types).stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> findByNomStartingWithIgnoreCaseOrderByPrix(String prefixe) {
        return articleRepository.findByNomStartingWithIgnoreCaseOrderByPrix(prefixe).stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> findByTypeWithMaxPrix(TypeArticle type) {
        return articleRepository.findByTypeWithMaxPrix(type).stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> findByNomContainingOrTypeContainingOrderByPrixDesc(String recherche) {
        return articleRepository.findByNomContainingOrTypeContainingOrderByPrixDesc(recherche).stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> findByNomStartingWith(String prefixe) {
        return articleRepository.findByNomStartingWith(prefixe).stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> findByNomEndingWith(String suffixe) {
        return articleRepository.findByNomEndingWith(suffixe).stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> findByTypeIsNull() {
        return articleRepository.findByTypeIsNull().stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> findByPrixIsNotNull() {
        return articleRepository.findByPrixIsNotNull().stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }

    // Méthode supprimée car commentée dans le repository
    // @Override
    // @Transactional(readOnly = true)
    // public List<ArticleResponse> findArticlesWithActivePromotions() {
    //     return articleRepository.findArticlesWithActivePromotions().stream()
    //             .map(articleMapper::toDto)
    //             .collect(Collectors.toList());
    // }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponse> findByNomContainingAndPrixBetween(String chaine, Double prixMin, Double prixMax) {
        return articleRepository.findByNomContainingAndPrixBetween(chaine, prixMin, prixMax).stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Article ajouterArticleEtPromotions(Article article) {
        return articleRepository.save(article);
    }

}