package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.CarteFidelite;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CarteFideliteRepository extends JpaRepository<CarteFidelite, Long> {

    // 1. Trouver les cartes avec un nombre exact de points
    @Query("SELECT c FROM CarteFidelite c WHERE c.pointsAccumules = :points")
    List<CarteFidelite> findByPointsAccumules(@Param("points") Integer points);

    // 2. Trouver les cartes créées à une date spécifique
    @Query("SELECT c FROM CarteFidelite c WHERE c.dateCreation = :date")
    List<CarteFidelite> findByDateCreation(@Param("date") LocalDate date);

    // 3. Compter les cartes avec plus de X points
    @Query("SELECT COUNT(c) FROM CarteFidelite c WHERE c.pointsAccumules > :points")
    long countByPointsAccumulesGreaterThan(@Param("points") Integer points);

    // 4. Supprimer les cartes créées avant une date
    @Query("DELETE FROM CarteFidelite c WHERE c.dateCreation < :date")
    void deleteByDateCreationBefore(@Param("date") LocalDate date);

    // 5. Trouver les cartes avec des points dans une plage, créées après une date
    @Query("SELECT c FROM CarteFidelite c WHERE c.pointsAccumules BETWEEN :minPoints AND :maxPoints AND c.dateCreation > :date")
    List<CarteFidelite> findByPointsAccumulesBetweenAndDateCreationAfter(
            @Param("minPoints") Integer minPoints,
            @Param("maxPoints") Integer maxPoints,
            @Param("date") LocalDate date);

    // 6. Trouver les cartes avec au moins X points, triées par date de création
    @Query("SELECT c FROM CarteFidelite c WHERE c.pointsAccumules >= :points ORDER BY c.dateCreation")
    List<CarteFidelite> findByPointsAccumulesGreaterThanEqualOrderByDateCreation(@Param("points") Integer points);

    // 7. Trouver les cartes créées entre deux dates
    @Query("SELECT c FROM CarteFidelite c WHERE c.dateCreation BETWEEN :startDate AND :endDate")
    List<CarteFidelite> findByDateCreationBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    // 8. Trouver les cartes avec peu de points OU créées avant une date
    @Query("SELECT c FROM CarteFidelite c WHERE c.pointsAccumules < :points OR c.dateCreation < :date")
    List<CarteFidelite> findByPointsAccumulesLessThanOrDateCreationBefore(
            @Param("points") Integer points,
            @Param("date") LocalDate date);

    // 9. Trouver la carte avec le plus de points
    @Query("SELECT c FROM CarteFidelite c ORDER BY c.pointsAccumules DESC LIMIT 1")
    Optional<CarteFidelite> findTopByOrderByPointsAccumulesDesc();

    // 10. Trouver les cartes sans date de création
    @Query("SELECT c FROM CarteFidelite c WHERE c.dateCreation IS NULL")
    List<CarteFidelite> findByDateCreationIsNull();

    // 11. Trouver les cartes avec des points accumulés renseignés
    @Query("SELECT c FROM CarteFidelite c WHERE c.pointsAccumules IS NOT NULL")
    List<CarteFidelite> findByPointsAccumulesIsNotNull();

    // 12. Trouver les cartes avec leur client propriétaire (Par nom et prénom)
    @Query("SELECT c FROM CarteFidelite c WHERE c.client.nom = :nom AND c.client.prenom = :prenom")
    List<CarteFidelite> findByClientNomAndClientPrenom(
            @Param("nom") String nom,
            @Param("prenom") String prenom);

    // 13. Trouver top 5 des cartes avec le plus de points
    @Query("SELECT c FROM CarteFidelite c ORDER BY c.pointsAccumules DESC LIMIT 5")
    List<CarteFidelite> findTop5ByOrderByPointsAccumulesDesc();
}