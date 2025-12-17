package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.AdresseRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.AdresseResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Adresse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Client;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.mapper.AdresseMapper;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories.AdresseRepository;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories.ClientRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdresseService implements IAdresseService {

    private final AdresseRepository adresseRepository;
    private final AdresseMapper adresseMapper;
    private final ClientRepository clientRepository;

    // Méthodes CRUD existantes (garder telles quelles)
    @Override
    public AdresseResponse addAdresse(AdresseRequest request) {
        Adresse adresse = adresseMapper.toEntity(request);
        Adresse saved = adresseRepository.save(adresse);
        return adresseMapper.toDto(saved);
    }

    @Override
    public List<AdresseResponse> saveAdresses(List<AdresseRequest> requests) {
        List<Adresse> adresses = requests.stream()
                .map(adresseMapper::toEntity)
                .collect(Collectors.toList());

        List<Adresse> saved = adresseRepository.saveAll(adresses);
        return saved.stream()
                .map(adresseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AdresseResponse selectAdresseById(Long id) {
        return adresseRepository.findById(id)
                .map(adresseMapper::toDto)
                .orElseGet(() -> AdresseResponse.builder()
                        .idAdresse(null)
                        .rue("default rue")
                        .ville("default ville")
                        .codePostal(0)
                        .build());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdresseResponse> selectAllAdresses() {
        return adresseRepository.findAll().stream()
                .map(adresseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAdresseById(Long id) {
        adresseRepository.deleteById(id);
    }

    @Override
    public void deleteAllAdresses() {
        adresseRepository.deleteAll();
    }

    @Override
    @Transactional(readOnly = true)
    public long countAdresses() {
        return adresseRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifAdresseById(Long id) {
        return adresseRepository.existsById(id);
    }

    // ✅ Nouvelles méthodes de recherche (avec Integer)

    @Override
    @Transactional(readOnly = true)
    public List<AdresseResponse> findByVille(String ville) {
        return adresseRepository.findByVille(ville).stream()
                .map(adresseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdresseResponse> findByCodePostal(Integer codePostal) { // ✅ Integer
        return adresseRepository.findByCodePostal(codePostal).stream()
                .map(adresseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long countByVille(String ville) {
        return adresseRepository.countByVille(ville);
    }

    @Override
    public void deleteByVille(String ville) {
        adresseRepository.deleteByVille(ville);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdresseResponse> findByVilleAndCodePostal(String ville, Integer codePostal) { // ✅ Integer
        return adresseRepository.findByVilleAndCodePostal(ville, codePostal).stream()
                .map(adresseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdresseResponse> findByRueContainingIgnoreCaseAndVilleIgnoreCase(String mot, String ville) {
        return adresseRepository.findByRueContainingIgnoreCaseAndVilleIgnoreCase(mot, ville).stream()
                .map(adresseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdresseResponse> findByVilleIn(List<String> villes) {
        return adresseRepository.findByVilleIn(villes).stream()
                .map(adresseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdresseResponse> findByCodePostalBetween(Integer codePostalDebut, Integer codePostalFin) { // ✅ Integer
        return adresseRepository.findByCodePostalBetween(codePostalDebut, codePostalFin).stream()
                .map(adresseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdresseResponse> findByCodePostalGreaterThan(Integer codePostal) { // ✅ Integer
        return adresseRepository.findByCodePostalGreaterThan(codePostal).stream()
                .map(adresseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdresseResponse> findByCodePostalGreaterThanEqual(Integer codePostal) { // ✅ Integer
        return adresseRepository.findByCodePostalGreaterThanEqual(codePostal).stream()
                .map(adresseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdresseResponse> findByCodePostalLessThan(Integer codePostal) { // ✅ Integer
        return adresseRepository.findByCodePostalLessThan(codePostal).stream()
                .map(adresseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdresseResponse> findByCodePostalLessThanEqual(Integer codePostal) { // ✅ Integer
        return adresseRepository.findByCodePostalLessThanEqual(codePostal).stream()
                .map(adresseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdresseResponse> findByRueStartingWithAndVilleOrderByCodePostal(String debutRue, String ville) {
        return adresseRepository.findByRueStartingWithAndVilleOrderByCodePostal(debutRue, ville).stream()
                .map(adresseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdresseResponse> findByRueStartingWith(String debutRue) {
        return adresseRepository.findByRueStartingWith(debutRue).stream()
                .map(adresseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdresseResponse> findByVilleEndingWith(String finVille) {
        return adresseRepository.findByVilleEndingWith(finVille).stream()
                .map(adresseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdresseResponse> findByRueIsNull() {
        return adresseRepository.findByRueIsNull().stream()
                .map(adresseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdresseResponse> findByVilleIsNotNull() {
        return adresseRepository.findByVilleIsNotNull().stream()
                .map(adresseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void ajouterEtAffecterAdresseAClient(Adresse ad, Client c) {
      ad= adresseRepository.save(ad);
      c.setAdresse(ad);
      clientRepository.save(c);
    }

}