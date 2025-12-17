package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.restControllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.ArticleRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.ArticleResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Article;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.TypeArticle;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services.IArticleService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final IArticleService articleService;

    // ✅ Méthodes CRUD existantes
    @GetMapping
    public List<ArticleResponse> selectAllArticles() {
        return articleService.selectAllArticles();
    }

    @GetMapping("/{id}")
    public ArticleResponse selectArticleById(@PathVariable long id) {
        return articleService.selectArticleById(id);
    }

    @PostMapping
    public ArticleResponse addArticle(@RequestBody ArticleRequest request) {
        return articleService.addArticle(request);
    }

    @PostMapping("/batch")
    public List<ArticleResponse> saveArticles(@RequestBody List<ArticleRequest> requests) {
        return articleService.saveArticles(requests);
    }

    @DeleteMapping("/{id}")
    public void deleteArticleById(@PathVariable long id) {
        articleService.deleteArticle(id);
    }

    @DeleteMapping
    public void deleteAllArticles() {
        articleService.deleteAllArticles();
    }

    @GetMapping("/count")
    public long countArticles() {
        return articleService.countingArticles();
    }

    @GetMapping("/exists/{id}")
    public boolean verifArticleById(@PathVariable long id) {
        return articleService.verifArticleById(id);
    }

    // ✅ Nouvelles endpoints de recherche

    @GetMapping("/nom/{nom}")
    public List<ArticleResponse> findByNom(@PathVariable String nom) {
        return articleService.findByNom(nom);
    }

    @GetMapping("/type/{type}")
    public List<ArticleResponse> findByType(@PathVariable String type) {
        try {
            TypeArticle typeEnum = TypeArticle.valueOf(type.toUpperCase());
            return articleService.findByType(typeEnum);
        } catch (IllegalArgumentException e) {
            // Retourner une liste vide si le type n'existe pas
            return List.of();
        }
    }

    @GetMapping("/prix/{prix}")
    public List<ArticleResponse> findByPrix(@PathVariable Double prix) {
        return articleService.findByPrix(prix);
    }

    @GetMapping("/exists/nom/{nom}")
    public boolean existsByNom(@PathVariable String nom) {
        return articleService.existsByNom(nom);
    }

    @GetMapping("/count/type/{type}")
    public long countByType(@PathVariable String type) {
        try {
            TypeArticle typeEnum = TypeArticle.valueOf(type.toUpperCase());
            return articleService.countByType(typeEnum);
        } catch (IllegalArgumentException e) {
            return 0;
        }
    }

    @GetMapping("/recherche-nom-type")
    public List<ArticleResponse> findByNomContainingAndType(
            @RequestParam String mot,
            @RequestParam String type) {
        try {
            TypeArticle typeEnum = TypeArticle.valueOf(type.toUpperCase());
            return articleService.findByNomContainingAndType(mot, typeEnum);
        } catch (IllegalArgumentException e) {
            return List.of();
        }
    }

    @GetMapping("/recherche-prix-types")
    public List<ArticleResponse> findByPrixBetweenAndTypeIn(
            @RequestParam Double prixMin,
            @RequestParam Double prixMax,
            @RequestParam List<String> types) {
        try {
            List<TypeArticle> typeEnums = types.stream()
                    .map(String::toUpperCase)
                    .map(TypeArticle::valueOf)
                    .collect(Collectors.toList());
            return articleService.findByPrixBetweenAndTypeIn(prixMin, prixMax, typeEnums);
        } catch (IllegalArgumentException e) {
            return List.of();
        }
    }

    @GetMapping("/nom-commence-ignore-case/{prefixe}/tri-prix")
    public List<ArticleResponse> findByNomStartingWithIgnoreCaseOrderByPrix(@PathVariable String prefixe) {
        return articleService.findByNomStartingWithIgnoreCaseOrderByPrix(prefixe);
    }

    @GetMapping("/type/{type}/prix-max")
    public List<ArticleResponse> findByTypeWithMaxPrix(@PathVariable String type) {
        try {
            TypeArticle typeEnum = TypeArticle.valueOf(type.toUpperCase());
            return articleService.findByTypeWithMaxPrix(typeEnum);
        } catch (IllegalArgumentException e) {
            return List.of();
        }
    }

    @GetMapping("/recherche/{recherche}/tri-prix-desc")
    public List<ArticleResponse> findByNomContainingOrTypeContainingOrderByPrixDesc(@PathVariable String recherche) {
        return articleService.findByNomContainingOrTypeContainingOrderByPrixDesc(recherche);
    }

    @GetMapping("/nom-commence/{prefixe}")
    public List<ArticleResponse> findByNomStartingWith(@PathVariable String prefixe) {
        return articleService.findByNomStartingWith(prefixe);
    }

    @GetMapping("/nom-termine/{suffixe}")
    public List<ArticleResponse> findByNomEndingWith(@PathVariable String suffixe) {
        return articleService.findByNomEndingWith(suffixe);
    }

    @GetMapping("/sans-type")
    public List<ArticleResponse> findByTypeIsNull() {
        return articleService.findByTypeIsNull();
    }

    @GetMapping("/avec-prix")
    public List<ArticleResponse> findByPrixIsNotNull() {
        return articleService.findByPrixIsNotNull();
    }

    // Endpoint supprimé car méthode commentée
    // @GetMapping("/avec-promotions-actives")
    // public List<ArticleResponse> findArticlesWithActivePromotions() {
    //     return articleService.findArticlesWithActivePromotions();
    // }

    @GetMapping("/recherche-avancee")
    public List<ArticleResponse> findByNomContainingAndPrixBetween(
            @RequestParam String chaine,
            @RequestParam Double prixMin,
            @RequestParam Double prixMax) {
        return articleService.findByNomContainingAndPrixBetween(chaine, prixMin, prixMax);
    }

    @PostMapping("/avec-promotions")
    public Article ajouterArticleEtPromotions(@RequestBody Article article) {
        return articleService.ajouterArticleEtPromotions(article);
    }


}