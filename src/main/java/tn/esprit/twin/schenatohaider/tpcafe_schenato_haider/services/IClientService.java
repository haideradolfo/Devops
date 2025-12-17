package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services;

import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.ClientRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.ClientResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Client;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Commande;

import java.time.LocalDate;
import java.util.List;

public interface IClientService {

    // ✅ Méthodes CRUD existantes
    ClientResponse addClient(ClientRequest request);
    List<ClientResponse> saveClients(List<ClientRequest> requests);
    ClientResponse selectClientById(Long id);
    List<ClientResponse> selectAllClients();
    void deleteClientById(Long id);
    void deleteAllClients();
    long countingClients();
    boolean verifClientById(Long id);

    // ✅ Nouvelles méthodes de recherche
    List<ClientResponse> findByNom(String nom);
    List<ClientResponse> findByPrenom(String prenom);
    List<ClientResponse> findByNomAndPrenom(String nom, String prenom);
    boolean existsByNom(String nom);
    long countByDateNaissanceAfter(LocalDate date);
    List<ClientResponse> findByNomContainingOrPrenomContaining(String nom, String prenom);
    List<ClientResponse> findByNomContainingAndPrenomContaining(String chaine);
    List<ClientResponse> findByDateNaissanceBetween(LocalDate dateDebut, LocalDate dateFin);
    List<ClientResponse> findByNomStartingWithAndDateNaissanceBefore(String debutNom, LocalDate date);
    List<ClientResponse> findByAdresseVille(String ville);
    List<ClientResponse> findByNomContainingOrderByPrenomAsc(String chaine);
    List<ClientResponse> findByNomContainingOrderByPrenomDesc(String chaine);
    List<ClientResponse> findByNomStartingWith(String lettre);
    List<ClientResponse> findByPrenomEndingWith(String terminaison);
    List<ClientResponse> findByDateNaissanceIsNull();
    List<ClientResponse> findByAdresseIsNotNull();
    List<ClientResponse> findByAdresseVilleIn(List<String> villes);
    List<ClientResponse> findByCarteFidelitePtsAccumulesGreaterThan(Integer valeur);
    List<ClientResponse> findByCarteFidelitePtsAccumulesGreaterThanEqual(Integer valeur);
    List<ClientResponse> findByCarteFidelitePtsAccumulesBetween(Integer min, Integer max);
    List<ClientResponse> findClientsByArticleNom(String nomArticle);
    List<ClientResponse> findByNomContainingAndArticleType(String chaineNom, String typeArticle);

    String affecterAdresseAClient(String rue, long cin);
    void affecterCarteAClient(long idCarte, long idClient);
    void affecterCommandeAClient(long idCommande, long idClient);
    void affecterCommandeAClient(LocalDate dateCommande, String nomClient, String prenomClient);
    void desaffecterClientDeCommande(long idCommande);
    Client ajouterClientEtCarteFidelite(Client client);

    void ajouterCommandeEtAffecterAClient(Commande c, String nom, String prenom);

    List<Client> incrementerPointsAnniversaire();
}