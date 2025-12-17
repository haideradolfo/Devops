package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.Detail_CommandeRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.Detail_CommandeResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Article;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Commande;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Detail_Commande;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.mapper.Detail_CommandeMapper;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories.ArticleRepository;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories.CommandeRepository;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories.Detail_CommandeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class Detail_CommandeService implements IDetail_CommandeService {

    private final Detail_CommandeRepository detail_CommandeRepository;
    private final CommandeRepository commandeRepository;
    private final ArticleRepository articleRepository;
    private final Detail_CommandeMapper detail_CommandeMapper;

    // Méthodes CRUD existantes (garder telles quelles)
    @Override
    public Detail_CommandeResponse addDetailCommande(Detail_CommandeRequest request) {
        try {
            System.out.println("=== DEBUG Detail_CommandeService ===");
            System.out.println("Request received: " + request);

            Detail_Commande detail_Commande = detail_CommandeMapper.toEntity(request);
            System.out.println("Detail_Commande after mapping: " + detail_Commande);

            // Gérer les relations
            if (request.getCommandeId() != null) {
                Commande commande = commandeRepository.findById(request.getCommandeId())
                        .orElse(null);
                detail_Commande.setCommande(commande);
                System.out.println("Commande set: " + commande);
            }

            if (request.getArticleId() != null) {
                Article article = articleRepository.findById(request.getArticleId())
                        .orElse(null);
                detail_Commande.setArticle(article);
                System.out.println("Article set: " + article);
            }

            Detail_Commande saved = detail_CommandeRepository.save(detail_Commande);
            System.out.println("Detail_Commande after save: " + saved);

            Detail_CommandeResponse response = detail_CommandeMapper.toDto(saved);
            System.out.println("Response: " + response);
            System.out.println("=== END DEBUG ===");

            return response;
        } catch (Exception e) {
            System.err.println("Error in addDetailCommande: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Detail_CommandeResponse> saveDetailCommandes(List<Detail_CommandeRequest> requests) {
        return requests.stream()
                .map(this::addDetailCommande)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Detail_CommandeResponse selectDetailCommandeById(Long id) {
        return detail_CommandeRepository.findById(id)
                .map(detail_CommandeMapper::toDto)
                .orElseGet(() -> Detail_CommandeResponse.builder()
                        .idDetailCommande(null)
                        .quantiteArticle(0)
                        .sousTotalDetailArticle(0.0f)
                        .sousTotalDetailArticleApresPromo(0.0f)
                        .commande(null)
                        .article(null)
                        .build());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Detail_CommandeResponse> selectAllDetailCommandes() {
        return detail_CommandeRepository.findAll().stream()
                .map(detail_CommandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDetailCommandeById(Long id) {
        detail_CommandeRepository.deleteById(id);
    }

    @Override
    public void deleteAllDetailCommandes() {
        detail_CommandeRepository.deleteAll();
    }

    // ✅ Nouvelles méthodes de recherche

    @Override
    @Transactional(readOnly = true)
    public List<Detail_CommandeResponse> findByQuantite(Integer quantite) {
        return detail_CommandeRepository.findByQuantite(quantite).stream()
                .map(detail_CommandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Detail_CommandeResponse> findBySousTotal(Float sousTotal) {
        return detail_CommandeRepository.findBySousTotal(sousTotal).stream()
                .map(detail_CommandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long countByQuantiteGreaterThan(Integer quantite) {
        return detail_CommandeRepository.countByQuantiteGreaterThan(quantite);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsBySousTotalGreaterThan(Float sousTotalMin) {
        return detail_CommandeRepository.existsBySousTotalGreaterThan(sousTotalMin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Detail_CommandeResponse> findByQuantiteBetweenAndSousTotalGreaterThanEqual(Integer quantiteMin, Integer quantiteMax, Float sousTotalMin) {
        return detail_CommandeRepository.findByQuantiteBetweenAndSousTotalGreaterThanEqual(quantiteMin, quantiteMax, sousTotalMin).stream()
                .map(detail_CommandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Detail_CommandeResponse> findBySousTotalBetweenOrderByQuantite(Float sousTotalMin, Float sousTotalMax) {
        return detail_CommandeRepository.findBySousTotalBetweenOrderByQuantite(sousTotalMin, sousTotalMax).stream()
                .map(detail_CommandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Detail_CommandeResponse> findBySousTotalApresPromoBetween(Float min, Float max) {
        return detail_CommandeRepository.findBySousTotalApresPromoBetween(min, max).stream()
                .map(detail_CommandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Detail_CommandeResponse> findByQuantiteOrSousTotalGreaterThanEqual(Integer quantite, Float sousTotalMin) {
        return detail_CommandeRepository.findByQuantiteOrSousTotalGreaterThanEqual(quantite, sousTotalMin).stream()
                .map(detail_CommandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Detail_CommandeResponse> findTop5ByOrderBySousTotalDesc() {
        return detail_CommandeRepository.findTop5ByOrderBySousTotalDesc().stream()
                .map(detail_CommandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Detail_CommandeResponse> findByQuantiteIsNull() {
        return detail_CommandeRepository.findByQuantiteIsNull().stream()
                .map(detail_CommandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Detail_CommandeResponse> findBySousTotalApresPromoIsNotNull() {
        return detail_CommandeRepository.findBySousTotalApresPromoIsNotNull().stream()
                .map(detail_CommandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Detail_CommandeResponse> findDetailsWithCommandeAndArticle() {
        return detail_CommandeRepository.findDetailsWithCommandeAndArticle().stream()
                .map(detail_CommandeMapper::toDto)
                .collect(Collectors.toList());
    }
}