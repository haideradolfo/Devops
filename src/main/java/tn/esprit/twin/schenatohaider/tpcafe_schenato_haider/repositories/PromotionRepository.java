package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Promotion;

import java.time.LocalDate;
import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    // 1. Trouver les promotions par pourcentage exact
    @Query(value = "SELECT * FROM promotion WHERE pourcentage_promo = :pourcentage", nativeQuery = true)
    List<Promotion> findByPourcentagePromo(@Param("pourcentage") String pourcentage);

    // 2. Trouver les promotions par date de début
    @Query(value = "SELECT * FROM promotion WHERE date_debut_promo = :dateDebut", nativeQuery = true)
    List<Promotion> findByDateDebutPromo(@Param("dateDebut") LocalDate dateDebut);

    // 3. Trouver les promotions par date de fin
    @Query(value = "SELECT * FROM promotion WHERE date_fin_promo = :dateFin", nativeQuery = true)
    List<Promotion> findByDateFinPromo(@Param("dateFin") LocalDate dateFin);

    // 4. Vérifier l'existence d'une promotion par pourcentage
    @Query(value = "SELECT COUNT(*) > 0 FROM promotion WHERE pourcentage_promo = :pourcentage", nativeQuery = true)
    boolean existsByPourcentagePromo(@Param("pourcentage") String pourcentage);

    // 5. Compter les promotions débutant après une date
    @Query(value = "SELECT COUNT(*) FROM promotion WHERE date_debut_promo > :date", nativeQuery = true)
    long countByDateDebutPromoAfter(@Param("date") LocalDate date);

    // 6. Trouver les promotions actives à une date donnée
    @Query(value = "SELECT * FROM promotion WHERE date_debut_promo <= :date AND (date_fin_promo IS NULL OR date_fin_promo >= :date)", nativeQuery = true)
    List<Promotion> findActivePromotionsAtDate(@Param("date") LocalDate date);

    // 7. Trouver les promotions avec un pourcentage spécifique débutant dans une période
    @Query(value = "SELECT * FROM promotion WHERE pourcentage_promo = :pourcentage AND date_debut_promo BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Promotion> findByPourcentagePromoAndDateDebutPromoBetween(
            @Param("pourcentage") String pourcentage,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    // 8. Trouver les promotions valides à une date spécifique
    @Query(value = "SELECT * FROM promotion WHERE :date BETWEEN date_debut_promo AND COALESCE(date_fin_promo, :date)", nativeQuery = true)
    List<Promotion> findValidPromotionsAtDate(@Param("date") LocalDate date);

    // 9. Trouver les promotions avec certains pourcentages, triées par date de début
    @Query(value = "SELECT * FROM promotion WHERE pourcentage_promo IN :pourcentages ORDER BY date_debut_promo", nativeQuery = true)
    List<Promotion> findByPourcentagePromoInOrderByDateDebutPromo(@Param("pourcentages") List<String> pourcentages);

    // 10. Trouver les promotions actives triées par pourcentage
    @Query(value = "SELECT * FROM promotion WHERE date_debut_promo <= CURRENT_DATE AND (date_fin_promo IS NULL OR date_fin_promo >= CURRENT_DATE) ORDER BY pourcentage_promo", nativeQuery = true)
    List<Promotion> findActivePromotionsOrderByPourcentagePromo();

    // 11. Trouver les promotions sans date de fin
    @Query(value = "SELECT * FROM promotion WHERE date_fin_promo IS NULL", nativeQuery = true)
    List<Promotion> findByDateFinPromoIsNull();

    // 12. Trouver les promotions avec un pourcentage renseigné
    @Query(value = "SELECT * FROM promotion WHERE pourcentage_promo IS NOT NULL", nativeQuery = true)
    List<Promotion> findByPourcentagePromoIsNotNull();

    // 13. Trouver les promotions avec leurs articles associés
    @Query(value = "SELECT DISTINCT p.* FROM promotion p " +
            "LEFT JOIN promotion_article pa ON p.id_promotion = pa.promotion_id " +
            "LEFT JOIN article a ON pa.article_id = a.id_article", nativeQuery = true)
    List<Promotion> findPromotionsWithArticles();

    // 14. Trouver les promotions expirées
    @Query(value = "SELECT * FROM promotion WHERE date_fin_promo < CURRENT_DATE", nativeQuery = true)
    List<Promotion> findExpiredPromotions();
}