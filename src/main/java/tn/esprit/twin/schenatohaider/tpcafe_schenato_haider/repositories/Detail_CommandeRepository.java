package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Detail_Commande;

import java.util.List;

public interface Detail_CommandeRepository extends JpaRepository<Detail_Commande, Long> {

    // 1. Trouver les détails de commande par quantité exacte
    @Query("SELECT d FROM Detail_Commande d WHERE d.quantiteArticle = :quantite")
    List<Detail_Commande> findByQuantite(@Param("quantite") Integer quantite);

    // 2. Trouver les détails par sous-total exact
    @Query("SELECT d FROM Detail_Commande d WHERE d.sousTotalDetailArticle = :sousTotal")
    List<Detail_Commande> findBySousTotal(@Param("sousTotal") Float sousTotal);

    // 3. Compter les détails avec plus de X quantités
    @Query("SELECT COUNT(d) FROM Detail_Commande d WHERE d.quantiteArticle > :quantite")
    long countByQuantiteGreaterThan(@Param("quantite") Integer quantite);

    // 4. Vérifier l'existence de détails avec un sous-total élevé
    @Query("SELECT COUNT(d) > 0 FROM Detail_Commande d WHERE d.sousTotalDetailArticle > :sousTotalMin")
    boolean existsBySousTotalGreaterThan(@Param("sousTotalMin") Float sousTotalMin);

    // 5. Trouver les détails avec une quantité dans une plage et un sous-total minimum
    @Query("SELECT d FROM Detail_Commande d WHERE d.quantiteArticle BETWEEN :quantiteMin AND :quantiteMax AND d.sousTotalDetailArticle >= :sousTotalMin")
    List<Detail_Commande> findByQuantiteBetweenAndSousTotalGreaterThanEqual(
            @Param("quantiteMin") Integer quantiteMin,
            @Param("quantiteMax") Integer quantiteMax,
            @Param("sousTotalMin") Float sousTotalMin);

    // 6. Trouver les détails avec un sous-total dans une plage, triés par quantité
    @Query("SELECT d FROM Detail_Commande d WHERE d.sousTotalDetailArticle BETWEEN :sousTotalMin AND :sousTotalMax ORDER BY d.quantiteArticle")
    List<Detail_Commande> findBySousTotalBetweenOrderByQuantite(
            @Param("sousTotalMin") Float sousTotalMin,
            @Param("sousTotalMax") Float sousTotalMax);

    // 7. Trouver les détails avec un sous-total après promotion dans une plage
    @Query("SELECT d FROM Detail_Commande d WHERE d.sousTotalDetailArticleApresPromo BETWEEN :min AND :max")
    List<Detail_Commande> findBySousTotalApresPromoBetween(
            @Param("min") Float min,
            @Param("max") Float max);

    // 8. Trouver les détails par quantité ou sous-total minimum
    @Query("SELECT d FROM Detail_Commande d WHERE d.quantiteArticle = :quantite OR d.sousTotalDetailArticle >= :sousTotalMin")
    List<Detail_Commande> findByQuantiteOrSousTotalGreaterThanEqual(
            @Param("quantite") Integer quantite,
            @Param("sousTotalMin") Float sousTotalMin);

    // 9. Trouver les 5 détails les plus chers
    @Query("SELECT d FROM Detail_Commande d ORDER BY d.sousTotalDetailArticle DESC LIMIT 5")
    List<Detail_Commande> findTop5ByOrderBySousTotalDesc();

    // 10. Trouver les détails sans quantité renseignée
    @Query("SELECT d FROM Detail_Commande d WHERE d.quantiteArticle IS NULL")
    List<Detail_Commande> findByQuantiteIsNull();

    // 11. Trouver les détails avec un sous-total après promotion renseigné
    @Query("SELECT d FROM Detail_Commande d WHERE d.sousTotalDetailArticleApresPromo IS NOT NULL")
    List<Detail_Commande> findBySousTotalApresPromoIsNotNull();

    // 12. Trouver les détails avec leur commande et article
    @Query("SELECT d FROM Detail_Commande d JOIN FETCH d.commande JOIN FETCH d.article")
    List<Detail_Commande> findDetailsWithCommandeAndArticle();
}