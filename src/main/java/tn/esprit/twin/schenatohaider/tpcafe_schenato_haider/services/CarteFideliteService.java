package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.CarteFideliteRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.CarteFideliteResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.CarteFidelite;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.mapper.CarteFideliteMapper;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories.CarteFideliteRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CarteFideliteService implements ICarteFideliteService {

    private final CarteFideliteRepository carteFideliteRepository;
    private final CarteFideliteMapper carteFideliteMapper;

    // Méthodes CRUD existantes (garder telles quelles)
    @Override
    public CarteFideliteResponse addCarteFidelite(CarteFideliteRequest request) {
        CarteFidelite carteFidelite = carteFideliteMapper.toEntity(request);
        CarteFidelite saved = carteFideliteRepository.save(carteFidelite);
        return carteFideliteMapper.toDto(saved);
    }

    @Override
    public List<CarteFideliteResponse> saveCartesFidelite(List<CarteFideliteRequest> requests) {
        List<CarteFidelite> cartesFidelite = requests.stream()
                .map(carteFideliteMapper::toEntity)
                .collect(Collectors.toList());

        List<CarteFidelite> saved = carteFideliteRepository.saveAll(cartesFidelite);
        return saved.stream()
                .map(carteFideliteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CarteFideliteResponse selectCarteFideliteById(Long id) {
        return carteFideliteRepository.findById(id)
                .map(carteFideliteMapper::toDto)
                .orElseGet(() -> CarteFideliteResponse.builder()
                        .idCarteFidelite(null)
                        .pointsAccumules(0)
                        .dateCreation(null)
                        .build());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarteFideliteResponse> selectAllCartesFidelite() {
        return carteFideliteRepository.findAll().stream()
                .map(carteFideliteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCarteFideliteById(Long id) {
        carteFideliteRepository.deleteById(id);
    }

    @Override
    public void deleteAllCartesFidelite() {
        carteFideliteRepository.deleteAll();
    }

    @Override
    @Transactional(readOnly = true)
    public long countCartesFidelite() {
        return carteFideliteRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifCarteFideliteById(Long id) {
        return carteFideliteRepository.existsById(id);
    }

    // ✅ Nouvelles méthodes de recherche

    @Override
    @Transactional(readOnly = true)
    public List<CarteFideliteResponse> findByPointsAccumules(Integer points) {
        return carteFideliteRepository.findByPointsAccumules(points).stream()
                .map(carteFideliteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarteFideliteResponse> findByDateCreation(LocalDate date) {
        return carteFideliteRepository.findByDateCreation(date).stream()
                .map(carteFideliteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long countByPointsAccumulesGreaterThan(Integer points) {
        return carteFideliteRepository.countByPointsAccumulesGreaterThan(points);
    }

    @Override
    @Transactional
    public void deleteByDateCreationBefore(LocalDate date) {
        carteFideliteRepository.deleteByDateCreationBefore(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarteFideliteResponse> findByPointsAccumulesBetweenAndDateCreationAfter(Integer minPoints, Integer maxPoints, LocalDate date) {
        return carteFideliteRepository.findByPointsAccumulesBetweenAndDateCreationAfter(minPoints, maxPoints, date).stream()
                .map(carteFideliteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarteFideliteResponse> findByPointsAccumulesGreaterThanEqualOrderByDateCreation(Integer points) {
        return carteFideliteRepository.findByPointsAccumulesGreaterThanEqualOrderByDateCreation(points).stream()
                .map(carteFideliteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarteFideliteResponse> findByDateCreationBetween(LocalDate startDate, LocalDate endDate) {
        return carteFideliteRepository.findByDateCreationBetween(startDate, endDate).stream()
                .map(carteFideliteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarteFideliteResponse> findByPointsAccumulesLessThanOrDateCreationBefore(Integer points, LocalDate date) {
        return carteFideliteRepository.findByPointsAccumulesLessThanOrDateCreationBefore(points, date).stream()
                .map(carteFideliteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CarteFideliteResponse> findTopByOrderByPointsAccumulesDesc() {
        return carteFideliteRepository.findTopByOrderByPointsAccumulesDesc()
                .map(carteFideliteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarteFideliteResponse> findByDateCreationIsNull() {
        return carteFideliteRepository.findByDateCreationIsNull().stream()
                .map(carteFideliteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarteFideliteResponse> findByPointsAccumulesIsNotNull() {
        return carteFideliteRepository.findByPointsAccumulesIsNotNull().stream()
                .map(carteFideliteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarteFideliteResponse> findByClientNomAndClientPrenom(String nom, String prenom) {
        return carteFideliteRepository.findByClientNomAndClientPrenom(nom, prenom).stream()
                .map(carteFideliteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarteFideliteResponse> findTop5ByOrderByPointsAccumulesDesc() {
        return carteFideliteRepository.findTop5ByOrderByPointsAccumulesDesc().stream()
                .map(carteFideliteMapper::toDto)
                .collect(Collectors.toList());
    }
}