package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services;

import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.ArticleRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.ArticleResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Article;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Promotion;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.TypeArticle;

import java.util.List;

public interface IArticleService {

    // ✅ Méthodes CRUD existantes
    ArticleResponse addArticle(ArticleRequest request);
    List<ArticleResponse> saveArticles(List<ArticleRequest> requests);
    ArticleResponse selectArticleById(long id);
    List<ArticleResponse> selectAllArticles();
    void deleteArticle(long id);
    void deleteAllArticles();
    long countingArticles();
    boolean verifArticleById(long id);

    // ✅ Nouvelles méthodes de recherche (avec TypeArticle enum)
    List<ArticleResponse> findByNom(String nom);
    List<ArticleResponse> findByType(TypeArticle type);
    List<ArticleResponse> findByPrix(Double prix);
    boolean existsByNom(String nom);
    long countByType(TypeArticle type);
    List<ArticleResponse> findByNomContainingAndType(String mot, TypeArticle type);
    List<ArticleResponse> findByPrixBetweenAndTypeIn(Double prixMin, Double prixMax, List<TypeArticle> types);
    List<ArticleResponse> findByNomStartingWithIgnoreCaseOrderByPrix(String prefixe);
    List<ArticleResponse> findByTypeWithMaxPrix(TypeArticle type);
    List<ArticleResponse> findByNomContainingOrTypeContainingOrderByPrixDesc(String recherche);
    List<ArticleResponse> findByNomStartingWith(String prefixe);
    List<ArticleResponse> findByNomEndingWith(String suffixe);
    List<ArticleResponse> findByTypeIsNull();
    List<ArticleResponse> findByPrixIsNotNull();
    // List<ArticleResponse> findArticlesWithActivePromotions(); // Supprimé car commenté dans le repository
    List<ArticleResponse> findByNomContainingAndPrixBetween(String chaine, Double prixMin, Double prixMax);

    Article ajouterArticleEtPromotions(Article article);


}