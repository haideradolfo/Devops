package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.restControllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.CarteFideliteRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.CarteFideliteResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.services.ICarteFideliteService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cartesfidelite")
public class CarteFideliteController {

    private final ICarteFideliteService carteFideliteService;

    // ✅ Méthodes CRUD existantes
    @GetMapping
    public List<CarteFideliteResponse> selectAllCartesFidelite() {
        return carteFideliteService.selectAllCartesFidelite();
    }

    @GetMapping("/{id}")
    public CarteFideliteResponse selectCarteFideliteById(@PathVariable Long id) {
        return carteFideliteService.selectCarteFideliteById(id);
    }

    @PostMapping
    public CarteFideliteResponse addCarteFidelite(@RequestBody CarteFideliteRequest request) {
        return carteFideliteService.addCarteFidelite(request);
    }

    @PostMapping("/batch")
    public List<CarteFideliteResponse> saveCartesFidelite(@RequestBody List<CarteFideliteRequest> requests) {
        return carteFideliteService.saveCartesFidelite(requests);
    }

    @DeleteMapping("/{id}")
    public void deleteCarteFideliteById(@PathVariable Long id) {
        carteFideliteService.deleteCarteFideliteById(id);
    }

    @DeleteMapping
    public void deleteAllCartesFidelite() {
        carteFideliteService.deleteAllCartesFidelite();
    }

    @GetMapping("/count")
    public long countCartesFidelite() {
        return carteFideliteService.countCartesFidelite();
    }

    @GetMapping("/exists/{id}")
    public boolean verifCarteFideliteById(@PathVariable Long id) {
        return carteFideliteService.verifCarteFideliteById(id);
    }

    // ✅ Nouvelles endpoints de recherche

    @GetMapping("/points/{points}")
    public List<CarteFideliteResponse> findByPointsAccumules(@PathVariable Integer points) {
        return carteFideliteService.findByPointsAccumules(points);
    }

    @GetMapping("/date/{date}")
    public List<CarteFideliteResponse> findByDateCreation(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return carteFideliteService.findByDateCreation(date);
    }

    @GetMapping("/count/points-superieurs/{points}")
    public long countByPointsAccumulesGreaterThan(@PathVariable Integer points) {
        return carteFideliteService.countByPointsAccumulesGreaterThan(points);
    }

    @DeleteMapping("/date-avant/{date}")
    public void deleteByDateCreationBefore(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        carteFideliteService.deleteByDateCreationBefore(date);
    }

    @GetMapping("/recherche-avancee")
    public List<CarteFideliteResponse> findByPointsAccumulesBetweenAndDateCreationAfter(
            @RequestParam Integer minPoints,
            @RequestParam Integer maxPoints,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return carteFideliteService.findByPointsAccumulesBetweenAndDateCreationAfter(minPoints, maxPoints, date);
    }

    @GetMapping("/points-min/{points}/tri-date")
    public List<CarteFideliteResponse> findByPointsAccumulesGreaterThanEqualOrderByDateCreation(@PathVariable Integer points) {
        return carteFideliteService.findByPointsAccumulesGreaterThanEqualOrderByDateCreation(points);
    }

    @GetMapping("/date-entre")
    public List<CarteFideliteResponse> findByDateCreationBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return carteFideliteService.findByDateCreationBetween(startDate, endDate);
    }

    @GetMapping("/points-faibles-ou-anciennes")
    public List<CarteFideliteResponse> findByPointsAccumulesLessThanOrDateCreationBefore(
            @RequestParam Integer points,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return carteFideliteService.findByPointsAccumulesLessThanOrDateCreationBefore(points, date);
    }

    @GetMapping("/top-points")
    public ResponseEntity<CarteFideliteResponse> findTopByOrderByPointsAccumulesDesc() {
        Optional<CarteFideliteResponse> carte = carteFideliteService.findTopByOrderByPointsAccumulesDesc();
        return carte.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/sans-date-creation")
    public List<CarteFideliteResponse> findByDateCreationIsNull() {
        return carteFideliteService.findByDateCreationIsNull();
    }

    @GetMapping("/avec-points")
    public List<CarteFideliteResponse> findByPointsAccumulesIsNotNull() {
        return carteFideliteService.findByPointsAccumulesIsNotNull();
    }

    @GetMapping("/client/{nom}/{prenom}")
    public List<CarteFideliteResponse> findByClientNomAndClientPrenom(
            @PathVariable String nom,
            @PathVariable String prenom) {
        return carteFideliteService.findByClientNomAndClientPrenom(nom, prenom);
    }

    @GetMapping("/top-5-points")
    public List<CarteFideliteResponse> findTop5ByOrderByPointsAccumulesDesc() {
        return carteFideliteService.findTop5ByOrderByPointsAccumulesDesc();
    }
}