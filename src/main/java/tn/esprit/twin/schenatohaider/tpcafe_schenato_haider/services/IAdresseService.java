package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services;

import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.AdresseRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.AdresseResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Adresse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Client;

import java.util.List;

public interface IAdresseService {

    // ✅ Méthodes CRUD existantes
    AdresseResponse addAdresse(AdresseRequest request);
    List<AdresseResponse> saveAdresses(List<AdresseRequest> requests);
    AdresseResponse selectAdresseById(Long id);
    List<AdresseResponse> selectAllAdresses();
    void deleteAdresseById(Long id);
    void deleteAllAdresses();
    long countAdresses();
    boolean verifAdresseById(Long id);

    // ✅ Nouvelles méthodes de recherche (avec Integer pour codePostal)
    List<AdresseResponse> findByVille(String ville);
    List<AdresseResponse> findByCodePostal(Integer codePostal); // ✅ Integer
    long countByVille(String ville);
    void deleteByVille(String ville);
    List<AdresseResponse> findByVilleAndCodePostal(String ville, Integer codePostal); // ✅ Integer
    List<AdresseResponse> findByRueContainingIgnoreCaseAndVilleIgnoreCase(String mot, String ville);
    List<AdresseResponse> findByVilleIn(List<String> villes);
    List<AdresseResponse> findByCodePostalBetween(Integer codePostalDebut, Integer codePostalFin); // ✅ Integer
    List<AdresseResponse> findByCodePostalGreaterThan(Integer codePostal); // ✅ Integer
    List<AdresseResponse> findByCodePostalGreaterThanEqual(Integer codePostal); // ✅ Integer
    List<AdresseResponse> findByCodePostalLessThan(Integer codePostal); // ✅ Integer
    List<AdresseResponse> findByCodePostalLessThanEqual(Integer codePostal); // ✅ Integer
    List<AdresseResponse> findByRueStartingWithAndVilleOrderByCodePostal(String debutRue, String ville);
    List<AdresseResponse> findByRueStartingWith(String debutRue);
    List<AdresseResponse> findByVilleEndingWith(String finVille);
    List<AdresseResponse> findByRueIsNull();
    List<AdresseResponse> findByVilleIsNotNull();

    void ajouterEtAffecterAdresseAClient(Adresse ad, Client c);
}