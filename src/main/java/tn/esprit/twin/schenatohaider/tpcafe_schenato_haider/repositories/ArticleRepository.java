package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Article;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.TypeArticle;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    // 1. Trouver les articles par nom exact
    @Query("SELECT a FROM Article a WHERE a.nomArticle = :nom")
    List<Article> findByNom(@Param("nom") String nom);

    // 2. Trouver les articles par type (utiliser String et convertir dans le service)
    @Query("SELECT a FROM Article a WHERE a.typeArticle = :type")
    List<Article> findByType(@Param("type") TypeArticle type);

    // 3. Trouver les articles par prix exact
    @Query("SELECT a FROM Article a WHERE a.prixArticle = :prix")
    List<Article> findByPrix(@Param("prix") Double prix);

    // 4. Vérifier l'existence d'un article par nom
    @Query("SELECT COUNT(a) > 0 FROM Article a WHERE a.nomArticle = :nom")
    boolean existsByNom(@Param("nom") String nom);

    // 5. Compter les articles par type
    @Query("SELECT COUNT(a) FROM Article a WHERE a.typeArticle = :type")
    long countByType(@Param("type") TypeArticle type);

    // 6. Trouver les articles dont le nom contient un mot et sont d'un type spécifique
    @Query("SELECT a FROM Article a WHERE a.nomArticle LIKE %:mot% AND a.typeArticle = :type")
    List<Article> findByNomContainingAndType(@Param("mot") String mot, @Param("type") TypeArticle type);

    // 7. Trouver les articles avec un prix dans une plage et de types spécifiques
    @Query("SELECT a FROM Article a WHERE a.prixArticle BETWEEN :prixMin AND :prixMax AND a.typeArticle IN :types")
    List<Article> findByPrixBetweenAndTypeIn(
            @Param("prixMin") Double prixMin,
            @Param("prixMax") Double prixMax,
            @Param("types") List<TypeArticle> types);

    // 8. Trouver les articles dont le nom commence par (insensible à la casse), triés par prix
    @Query("SELECT a FROM Article a WHERE LOWER(a.nomArticle) LIKE LOWER(CONCAT(:prefixe, '%')) ORDER BY a.prixArticle")
    List<Article> findByNomStartingWithIgnoreCaseOrderByPrix(@Param("prefixe") String prefixe);

    // 9. Trouver les articles d'un type avec un prix maximum
    @Query("SELECT a FROM Article a WHERE a.typeArticle = :type AND a.prixArticle = (SELECT MAX(a2.prixArticle) FROM Article a2 WHERE a2.typeArticle = :type)")
    List<Article> findByTypeWithMaxPrix(@Param("type") TypeArticle type);

    // 10. Trouver les articles par nom ou type, triés par prix décroissant
    @Query("SELECT a FROM Article a WHERE a.nomArticle LIKE %:recherche% OR CAST(a.typeArticle AS string) LIKE %:recherche% ORDER BY a.prixArticle DESC")
    List<Article> findByNomContainingOrTypeContainingOrderByPrixDesc(@Param("recherche") String recherche);

    // 11. Trouver les articles dont le nom commence par un préfixe spécifique
    @Query("SELECT a FROM Article a WHERE a.nomArticle LIKE :prefixe%")
    List<Article> findByNomStartingWith(@Param("prefixe") String prefixe);

    // 12. Trouver les articles dont le nom se termine par un suffixe
    @Query("SELECT a FROM Article a WHERE a.nomArticle LIKE %:suffixe")
    List<Article> findByNomEndingWith(@Param("suffixe") String suffixe);

    // 13. Trouver les articles sans type renseigné
    @Query("SELECT a FROM Article a WHERE a.typeArticle IS NULL")
    List<Article> findByTypeIsNull();

    // 14. Trouver les articles avec un prix renseigné
    @Query("SELECT a FROM Article a WHERE a.prixArticle IS NOT NULL")
    List<Article> findByPrixIsNotNull();

    // 15. Trouver les articles avec leurs promotions actives (version alternative)
    // @Query("SELECT DISTINCT a FROM Article a JOIN a.promotions p WHERE p.estActive = true")
    // List<Article> findArticlesWithActivePromotions();

    // 16. Trouver les articles avec nom contenant une chaine et prix dans une plage
    @Query("SELECT a FROM Article a WHERE a.nomArticle LIKE %:chaine% AND a.prixArticle BETWEEN :prixMin AND :prixMax")
    List<Article> findByNomContainingAndPrixBetween(
            @Param("chaine") String chaine,
            @Param("prixMin") Double prixMin,
            @Param("prixMax") Double prixMax);

}