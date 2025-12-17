package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Commande;

import java.time.LocalDate;
import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {

    // 1. Trouver les commandes par statut
    @Query(value = "SELECT * FROM commande WHERE status_commande = :statut", nativeQuery = true)
    List<Commande> findByStatusCommande(@Param("statut") String statut);

    // 2. Trouver les commandes par date exacte
    @Query(value = "SELECT * FROM commande WHERE date_commande = :date", nativeQuery = true)
    List<Commande> findByDateCommande(@Param("date") LocalDate date);

    // 3. Compter les commandes par statut
    @Query(value = "SELECT COUNT(*) FROM commande WHERE status_commande = :statut", nativeQuery = true)
    long countByStatusCommande(@Param("statut") String statut);

    // 4. Supprimer les commandes antérieures à une date
    @Modifying
    @Query(value = "DELETE FROM commande WHERE date_commande < :date", nativeQuery = true)
    void deleteByDateCommandeBefore(@Param("date") LocalDate date);

    // 5. Trouver les commandes entre deux dates avec un statut spécifique
    @Query(value = "SELECT * FROM commande WHERE date_commande BETWEEN :dateDebut AND :dateFin AND status_commande = :statut", nativeQuery = true)
    List<Commande> findByDateCommandeBetweenAndStatusCommande(
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin,
            @Param("statut") String statut);

    // 6. Trouver les commandes avec un total supérieur à un montant et un statut différent
    @Query(value = "SELECT * FROM commande WHERE total_commande > :montant AND status_commande != :statut", nativeQuery = true)
    List<Commande> findByTotalCommandeGreaterThanAndStatusCommandeNot(
            @Param("montant") Float montant,
            @Param("statut") String statut);

    // 7. Trouver les commandes avec certains statuts, triées par date
    @Query(value = "SELECT * FROM commande WHERE status_commande IN :statuts ORDER BY date_commande", nativeQuery = true)
    List<Commande> findByStatusCommandeInOrderByDateCommande(@Param("statuts") List<String> statuts);

    // 8. Trouver les commandes avant une date avec un total dans une plage
    @Query(value = "SELECT * FROM commande WHERE date_commande < :date AND total_commande BETWEEN :minTotal AND :maxTotal", nativeQuery = true)
    List<Commande> findByDateCommandeBeforeAndTotalCommandeBetween(
            @Param("date") LocalDate date,
            @Param("minTotal") Float minTotal,
            @Param("maxTotal") Float maxTotal);

    // 9. Trouver les commandes où le statut se termine par une lettre spécifique
    @Query(value = "SELECT * FROM commande WHERE status_commande LIKE %:lettre", nativeQuery = true)
    List<Commande> findByStatusCommandeEndingWith(@Param("lettre") String lettre);

    // 10. Trouver les commandes sans statut renseigné
    @Query(value = "SELECT * FROM commande WHERE status_commande IS NULL", nativeQuery = true)
    List<Commande> findByStatusCommandeIsNull();

    // 11. Trouver les commandes avec un total renseigné
    @Query(value = "SELECT * FROM commande WHERE total_commande IS NOT NULL", nativeQuery = true)
    List<Commande> findByTotalCommandeIsNotNull();

    // 12. Trouver les commandes avec leurs détails et client
    @Query(value = "SELECT c.* FROM commande c " +
            "LEFT JOIN client cl ON c.client_id = cl.id_client " +
            "LEFT JOIN detail_commande dc ON c.id_commande = dc.commande_id " +
            "GROUP BY c.id_commande", nativeQuery = true)
    List<Commande> findCommandesWithDetailsAndClient();

    // 13. Trouver le top 3 des commandes les plus récentes
    @Query(value = "SELECT * FROM commande ORDER BY date_commande DESC LIMIT 3", nativeQuery = true)
    List<Commande> findTop3ByOrderByDateCommandeDesc();
}