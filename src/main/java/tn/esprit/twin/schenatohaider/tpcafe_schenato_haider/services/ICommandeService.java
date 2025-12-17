package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services;

import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.CommandeRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.CommandeResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Commande;

import java.time.LocalDate;
import java.util.List;

public interface ICommandeService {

    // ✅ Méthodes CRUD existantes
    CommandeResponse addCommande(CommandeRequest request);
    List<CommandeResponse> saveCommandes(List<CommandeRequest> requests);
    CommandeResponse selectCommandeById(Long id);
    List<CommandeResponse> selectAllCommandes();
    void deleteCommandeById(Long id);
    void deleteAllCommandes();
    long countCommandes();
    boolean verifCommandeById(Long id);

    // ✅ Nouvelles méthodes de recherche
    List<CommandeResponse> findByStatusCommande(String statut);
    List<CommandeResponse> findByDateCommande(LocalDate date);
    long countByStatusCommande(String statut);
    void deleteByDateCommandeBefore(LocalDate date);
    List<CommandeResponse> findByDateCommandeBetweenAndStatusCommande(LocalDate dateDebut, LocalDate dateFin, String statut);
    List<CommandeResponse> findByTotalCommandeGreaterThanAndStatusCommandeNot(Float montant, String statut);
    List<CommandeResponse> findByStatusCommandeInOrderByDateCommande(List<String> statuts);
    List<CommandeResponse> findByDateCommandeBeforeAndTotalCommandeBetween(LocalDate date, Float minTotal, Float maxTotal);
    List<CommandeResponse> findByStatusCommandeEndingWith(String lettre);
    List<CommandeResponse> findByStatusCommandeIsNull();
    List<CommandeResponse> findByTotalCommandeIsNotNull();
    List<CommandeResponse> findCommandesWithDetailsAndClient();
    List<CommandeResponse> findTop3ByOrderByDateCommandeDesc();

    void ajouterCommandeEtAffecterAClient(Commande c, String nomC, String prenomC);
}