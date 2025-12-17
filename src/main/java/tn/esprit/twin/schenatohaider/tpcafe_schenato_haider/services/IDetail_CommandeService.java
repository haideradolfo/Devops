package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services;

import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.Detail_CommandeRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.Detail_CommandeResponse;

import java.util.List;

public interface IDetail_CommandeService {

    // ✅ Méthodes CRUD existantes
    Detail_CommandeResponse addDetailCommande(Detail_CommandeRequest request);
    List<Detail_CommandeResponse> saveDetailCommandes(List<Detail_CommandeRequest> requests);
    Detail_CommandeResponse selectDetailCommandeById(Long id);
    List<Detail_CommandeResponse> selectAllDetailCommandes();
    void deleteDetailCommandeById(Long id);
    void deleteAllDetailCommandes();

    // ✅ Nouvelles méthodes de recherche
    List<Detail_CommandeResponse> findByQuantite(Integer quantite);
    List<Detail_CommandeResponse> findBySousTotal(Float sousTotal);
    long countByQuantiteGreaterThan(Integer quantite);
    boolean existsBySousTotalGreaterThan(Float sousTotalMin);
    List<Detail_CommandeResponse> findByQuantiteBetweenAndSousTotalGreaterThanEqual(Integer quantiteMin, Integer quantiteMax, Float sousTotalMin);
    List<Detail_CommandeResponse> findBySousTotalBetweenOrderByQuantite(Float sousTotalMin, Float sousTotalMax);
    List<Detail_CommandeResponse> findBySousTotalApresPromoBetween(Float min, Float max);
    List<Detail_CommandeResponse> findByQuantiteOrSousTotalGreaterThanEqual(Integer quantite, Float sousTotalMin);
    List<Detail_CommandeResponse> findTop5ByOrderBySousTotalDesc();
    List<Detail_CommandeResponse> findByQuantiteIsNull();
    List<Detail_CommandeResponse> findBySousTotalApresPromoIsNotNull();
    List<Detail_CommandeResponse> findDetailsWithCommandeAndArticle();
}