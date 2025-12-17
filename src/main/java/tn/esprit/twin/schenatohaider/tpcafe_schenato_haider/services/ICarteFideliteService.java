package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services;

import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.CarteFideliteRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.CarteFideliteResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ICarteFideliteService {

    // ✅ Méthodes CRUD existantes
    CarteFideliteResponse addCarteFidelite(CarteFideliteRequest request);
    List<CarteFideliteResponse> saveCartesFidelite(List<CarteFideliteRequest> requests);
    CarteFideliteResponse selectCarteFideliteById(Long id);
    List<CarteFideliteResponse> selectAllCartesFidelite();
    void deleteCarteFideliteById(Long id);
    void deleteAllCartesFidelite();
    long countCartesFidelite();
    boolean verifCarteFideliteById(Long id);

    // ✅ Nouvelles méthodes de recherche
    List<CarteFideliteResponse> findByPointsAccumules(Integer points);
    List<CarteFideliteResponse> findByDateCreation(LocalDate date);
    long countByPointsAccumulesGreaterThan(Integer points);
    void deleteByDateCreationBefore(LocalDate date);
    List<CarteFideliteResponse> findByPointsAccumulesBetweenAndDateCreationAfter(Integer minPoints, Integer maxPoints, LocalDate date);
    List<CarteFideliteResponse> findByPointsAccumulesGreaterThanEqualOrderByDateCreation(Integer points);
    List<CarteFideliteResponse> findByDateCreationBetween(LocalDate startDate, LocalDate endDate);
    List<CarteFideliteResponse> findByPointsAccumulesLessThanOrDateCreationBefore(Integer points, LocalDate date);
    Optional<CarteFideliteResponse> findTopByOrderByPointsAccumulesDesc();
    List<CarteFideliteResponse> findByDateCreationIsNull();
    List<CarteFideliteResponse> findByPointsAccumulesIsNotNull();
    List<CarteFideliteResponse> findByClientNomAndClientPrenom(String nom, String prenom);
    List<CarteFideliteResponse> findTop5ByOrderByPointsAccumulesDesc();
}