package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.ClientRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.ClientResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Adresse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.CarteFidelite;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Client;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Commande;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.mapper.ClientMapper;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories.AdresseRepository;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories.CarteFideliteRepository;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories.ClientRepository;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories.CommandeRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ClientService implements IClientService {

    private final ClientRepository clientRepository;
    private final AdresseRepository adresseRepository;
    private final CarteFideliteRepository carteFideliteRepository;
    private final ClientMapper clientMapper;
    private final CommandeRepository commandeRepository;

    // Méthodes CRUD existantes (garder telles quelles)
    @Override
    public ClientResponse addClient(ClientRequest request) {
        try {
            System.out.println("=== DEBUG ClientService ===");
            System.out.println("Request received: " + request);

            Client client = clientMapper.toEntity(request);
            System.out.println("Client after mapping: " + client);

            // Gérer les relations - version sécurisée
            if (request.getAdresseId() != null) {
                Adresse adresse = adresseRepository.findById(request.getAdresseId())
                        .orElse(null);
                client.setAdresse(adresse);
                System.out.println("Adresse set: " + adresse);
            }

            if (request.getCarteFideliteId() != null) {
                CarteFidelite carteFidelite = carteFideliteRepository.findById(request.getCarteFideliteId())
                        .orElse(null);
                client.setCarteFidelite(carteFidelite);
                System.out.println("CarteFidelite set: " + carteFidelite);
            }

            Client saved = clientRepository.save(client);
            System.out.println("Client after save: " + saved);

            ClientResponse response = clientMapper.toDto(saved);
            System.out.println("Response: " + response);
            System.out.println("=== END DEBUG ===");

            return response;
        } catch (Exception e) {
            System.err.println("Error in addClient: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<ClientResponse> saveClients(List<ClientRequest> requests) {
        return requests.stream()
                .map(this::addClient)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponse selectClientById(Long id) {
        return clientRepository.findById(id)
                .map(clientMapper::toDto)
                .orElseGet(() -> ClientResponse.builder()
                        .idClient(null)
                        .nom("default nom")
                        .prenom("default prenom")
                        .dateNaissance(null)
                        .adresse(null)
                        .carteFidelite(null)
                        .build());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> selectAllClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteClientById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public void deleteAllClients() {
        clientRepository.deleteAll();
    }

    @Override
    @Transactional(readOnly = true)
    public long countingClients() {
        return clientRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifClientById(Long id) {
        return clientRepository.existsById(id);
    }

    // ✅ Nouvelles méthodes de recherche

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByNom(String nom) {
        return clientRepository.findByNom(nom).stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByPrenom(String prenom) {
        return clientRepository.findByPrenom(prenom).stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByNomAndPrenom(String nom, String prenom) {
        return clientRepository.findByNomAndPrenom(nom, prenom).stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNom(String nom) {
        return clientRepository.existsByNom(nom);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByDateNaissanceAfter(LocalDate date) {
        return clientRepository.countByDateNaissanceAfter(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByNomContainingOrPrenomContaining(String nom, String prenom) {
        return clientRepository.findByNomContainingOrPrenomContaining(nom, prenom).stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByNomContainingAndPrenomContaining(String chaine) {
        return clientRepository.findByNomContainingAndPrenomContaining(chaine).stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByDateNaissanceBetween(LocalDate dateDebut, LocalDate dateFin) {
        return clientRepository.findByDateNaissanceBetween(dateDebut, dateFin).stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByNomStartingWithAndDateNaissanceBefore(String debutNom, LocalDate date) {
        return clientRepository.findByNomStartingWithAndDateNaissanceBefore(debutNom, date).stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByAdresseVille(String ville) {
        return clientRepository.findByAdresseVille(ville).stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByNomContainingOrderByPrenomAsc(String chaine) {
        return clientRepository.findByNomContainingOrderByPrenomAsc(chaine).stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByNomContainingOrderByPrenomDesc(String chaine) {
        return clientRepository.findByNomContainingOrderByPrenomDesc(chaine).stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByNomStartingWith(String lettre) {
        return clientRepository.findByNomStartingWith(lettre).stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByPrenomEndingWith(String terminaison) {
        return clientRepository.findByPrenomEndingWith(terminaison).stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByDateNaissanceIsNull() {
        return clientRepository.findByDateNaissanceIsNull().stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByAdresseIsNotNull() {
        return clientRepository.findByAdresseIsNotNull().stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByAdresseVilleIn(List<String> villes) {
        return clientRepository.findByAdresseVilleIn(villes).stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByCarteFidelitePtsAccumulesGreaterThan(Integer valeur) {
        return clientRepository.findByCarteFidelitePtsAccumulesGreaterThan(valeur).stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByCarteFidelitePtsAccumulesGreaterThanEqual(Integer valeur) {
        return clientRepository.findByCarteFidelitePtsAccumulesGreaterThanEqual(valeur).stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByCarteFidelitePtsAccumulesBetween(Integer min, Integer max) {
        return clientRepository.findByCarteFidelitePtsAccumulesBetween(min, max).stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findClientsByArticleNom(String nomArticle) {
        return clientRepository.findClientsByArticleNom(nomArticle).stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findByNomContainingAndArticleType(String chaineNom, String typeArticle) {
        return clientRepository.findByNomContainingAndArticleType(chaineNom, typeArticle).stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public String affecterAdresseAClient(String rue, long cin) {
        // Trouver le client par CIN
        Client client = clientRepository.findByCin(cin)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec CIN: " + cin));

        // Créer ou trouver l'adresse
        Adresse adresse = adresseRepository.findByRue(rue)
                .orElseGet(() -> {
                    Adresse newAdresse = new Adresse();
                    newAdresse.setRue(rue);
                    return adresseRepository.save(newAdresse);
                });

        // Affecter l'adresse au client
        client.setAdresse(adresse);
        clientRepository.save(client);

        return "Adresse '" + rue + "' affectée avec succès au client " + client.getNom() + " " + client.getPrenom();
    }

    @Override
    public void affecterCarteAClient(long idCarte, long idClient) {
        CarteFidelite carte = carteFideliteRepository.findById(idCarte)
                .orElseThrow(() -> new RuntimeException("Carte fidélité non trouvée avec ID: " + idCarte));

        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec ID: " + idClient));

        client.setCarteFidelite(carte);
        clientRepository.save(client);
    }

    @Override
    public void affecterCommandeAClient(long idCommande, long idClient) {
        Commande commande = commandeRepository.findById(idCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec ID: " + idCommande));

        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec ID: " + idClient));

        commande.setClient(client);
        commandeRepository.save(commande);
    }

    @Override
    public void affecterCommandeAClient(LocalDate dateCommande, String nomClient, String prenomClient) {
        // Trouver le client par nom et prénom
        List<Client> clients = clientRepository.findByNomAndPrenom(nomClient, prenomClient);
        if (clients.isEmpty()) {
            throw new RuntimeException("Client non trouvé avec nom: " + nomClient + " et prénom: " + prenomClient);
        }
        Client client = clients.get(0);

        // Trouver une commande par date (première commande trouvée)
        List<Commande> commandes = commandeRepository.findByDateCommande(dateCommande);
        if (commandes.isEmpty()) {
            throw new RuntimeException("Aucune commande trouvée pour la date: " + dateCommande);
        }
        Commande commande = commandes.get(0);

        commande.setClient(client);
        commandeRepository.save(commande);
    }

    @Override
    public void desaffecterClientDeCommande(long idCommande) {
        Commande commande = commandeRepository.findById(idCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée avec ID: " + idCommande));

        commande.setClient(null);
        commandeRepository.save(commande);
    }

    @Override
    public Client ajouterClientEtCarteFidelite(Client client) {
        // Sauvegarder d'abord la carte fidélité si elle existe
        if (client.getCarteFidelite() != null) {
            CarteFidelite savedCarte = carteFideliteRepository.save(client.getCarteFidelite());
            client.setCarteFidelite(savedCarte);
        }

        // Sauvegarder le client
        return clientRepository.save(client);
    }


    @Override
    public void ajouterCommandeEtAffecterAClient(Commande c, String nom, String prenom){
        c = commandeRepository.save(c);
        List<Client> clients = clientRepository.findByNomAndPrenom(nom, prenom);
        Client client = clients.get(0); // Prendre le premier client de la liste
        c.setClient(client);
        commandeRepository.save(c);
    }

    @Override
    @Transactional
    public List<Client> incrementerPointsAnniversaire() {
        log.info("=== Méthode d'anniversaire appelée ===");

        LocalDate aujourdhui = LocalDate.now();
        List<Client> clientsModifies = new ArrayList<>();

        // Récupérer tous les clients
        List<Client> tousClients = clientRepository.findAll();

        for (Client client : tousClients) {
            LocalDate dateNaissance = client.getDateNaissance();

            if (dateNaissance != null &&
                    dateNaissance.getDayOfMonth() == aujourdhui.getDayOfMonth() &&
                    dateNaissance.getMonthValue() == aujourdhui.getMonthValue()) {

                log.info("🎂 Anniversaire de {} {}", client.getNom(), client.getPrenom());

                CarteFidelite carte = client.getCarteFidelite();
                if (carte != null) {
                    // Augmenter de 10%
                    int anciensPoints = carte.getPointsAccumules();
                    int nouveauxPoints = anciensPoints + (int)(anciensPoints * 0.10);

                    carte.setPointsAccumules(nouveauxPoints);
                    clientRepository.save(client);
                    clientsModifies.add(client);

                    log.info("  Points: {} → {}", anciensPoints, nouveauxPoints);
                } else {
                    log.info("  Pas de carte fidélité");
                }
            }
        }

        return clientsModifies;
    }
}