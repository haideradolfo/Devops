package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.CommandeRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.CommandeResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Client;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Commande;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.mapper.CommandeMapper;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories.ClientRepository;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories.CommandeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommandeService implements ICommandeService {

    private final CommandeRepository commandeRepository;
    private final ClientRepository clientRepository;
    private final CommandeMapper commandeMapper;

    // Méthodes CRUD existantes (garder telles quelles)
    @Override
    public CommandeResponse addCommande(CommandeRequest request) {
        try {
            System.out.println("=== DEBUG CommandeService ===");
            System.out.println("Request received: " + request);

            Commande commande = commandeMapper.toEntity(request);
            System.out.println("Commande after mapping: " + commande);

            // Gérer la relation client
            if (request.getClientId() != null) {
                Client client = clientRepository.findById(request.getClientId())
                        .orElse(null);
                commande.setClient(client);
                System.out.println("Client set: " + client);
            }

            Commande saved = commandeRepository.save(commande);
            System.out.println("Commande after save: " + saved);

            CommandeResponse response = commandeMapper.toDto(saved);
            System.out.println("Response: " + response);
            System.out.println("=== END DEBUG ===");

            return response;
        } catch (Exception e) {
            System.err.println("Error in addCommande: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<CommandeResponse> saveCommandes(List<CommandeRequest> requests) {
        return requests.stream()
                .map(this::addCommande)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CommandeResponse selectCommandeById(Long id) {
        return commandeRepository.findById(id)
                .map(commandeMapper::toDto)
                .orElseGet(() -> CommandeResponse.builder()
                        .idCommande(null)
                        .dateCommande(null)
                        .totalCommande(0.0f)
                        .statusCommande(null)
                        .client(null)
                        .build());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandeResponse> selectAllCommandes() {
        return commandeRepository.findAll().stream()
                .map(commandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCommandeById(Long id) {
        commandeRepository.deleteById(id);
    }

    @Override
    public void deleteAllCommandes() {
        commandeRepository.deleteAll();
    }

    @Override
    @Transactional(readOnly = true)
    public long countCommandes() {
        return commandeRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifCommandeById(Long id) {
        return commandeRepository.existsById(id);
    }

    // ✅ Nouvelles méthodes de recherche

    @Override
    @Transactional(readOnly = true)
    public List<CommandeResponse> findByStatusCommande(String statut) {
        return commandeRepository.findByStatusCommande(statut).stream()
                .map(commandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandeResponse> findByDateCommande(LocalDate date) {
        return commandeRepository.findByDateCommande(date).stream()
                .map(commandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long countByStatusCommande(String statut) {
        return commandeRepository.countByStatusCommande(statut);
    }

    @Override
    @Transactional
    public void deleteByDateCommandeBefore(LocalDate date) {
        commandeRepository.deleteByDateCommandeBefore(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandeResponse> findByDateCommandeBetweenAndStatusCommande(LocalDate dateDebut, LocalDate dateFin, String statut) {
        return commandeRepository.findByDateCommandeBetweenAndStatusCommande(dateDebut, dateFin, statut).stream()
                .map(commandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandeResponse> findByTotalCommandeGreaterThanAndStatusCommandeNot(Float montant, String statut) {
        return commandeRepository.findByTotalCommandeGreaterThanAndStatusCommandeNot(montant, statut).stream()
                .map(commandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandeResponse> findByStatusCommandeInOrderByDateCommande(List<String> statuts) {
        return commandeRepository.findByStatusCommandeInOrderByDateCommande(statuts).stream()
                .map(commandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandeResponse> findByDateCommandeBeforeAndTotalCommandeBetween(LocalDate date, Float minTotal, Float maxTotal) {
        return commandeRepository.findByDateCommandeBeforeAndTotalCommandeBetween(date, minTotal, maxTotal).stream()
                .map(commandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandeResponse> findByStatusCommandeEndingWith(String lettre) {
        return commandeRepository.findByStatusCommandeEndingWith(lettre).stream()
                .map(commandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandeResponse> findByStatusCommandeIsNull() {
        return commandeRepository.findByStatusCommandeIsNull().stream()
                .map(commandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandeResponse> findByTotalCommandeIsNotNull() {
        return commandeRepository.findByTotalCommandeIsNotNull().stream()
                .map(commandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandeResponse> findCommandesWithDetailsAndClient() {
        return commandeRepository.findCommandesWithDetailsAndClient().stream()
                .map(commandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandeResponse> findTop3ByOrderByDateCommandeDesc() {
        return commandeRepository.findTop3ByOrderByDateCommandeDesc().stream()
                .map(commandeMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void ajouterCommandeEtAffecterAClient(Commande c, String nomC, String prenomC) {
        c = commandeRepository.save(c);
        List<Client> clients = clientRepository.findByNomAndPrenom(nomC, prenomC);
        Client client = clients.get(0);
        c.setClient(client);
        commandeRepository.save(c);
    }
}