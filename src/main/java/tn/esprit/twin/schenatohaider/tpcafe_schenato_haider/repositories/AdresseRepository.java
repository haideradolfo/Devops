package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Adresse;

import java.util.List;
import java.util.Optional;

public interface AdresseRepository extends JpaRepository<Adresse, Long> {

    // 1. Trouver toutes les adresses d'une ville spécifique
    List<Adresse> findByVille(String ville);

    // 2. Trouver les adresses par code postal exact
    List<Adresse> findByCodePostal(Integer codePostal); // Changé en Integer

    // 3. Compter le nombre d'adresses dans une ville
    long countByVille(String ville);

    // 4. Supprimer toutes les adresses d'une ville
    void deleteByVille(String ville);

    // 5. Trouver les adresses d'une ville avec un code postal spécifique
    List<Adresse> findByVilleAndCodePostal(String ville, Integer codePostal); // Changé en Integer

    // 6. Trouver les adresses dont la rue contient un mot, insensible à la casse de la ville
    @Query("SELECT a FROM Adresse a WHERE LOWER(a.rue) LIKE LOWER(CONCAT('%', :mot, '%')) AND LOWER(a.ville) = LOWER(:ville)")
    List<Adresse> findByRueContainingIgnoreCaseAndVilleIgnoreCase(@Param("mot") String mot, @Param("ville") String ville);

    // 7. Trouver les adresses situées dans une liste de villes
    List<Adresse> findByVilleIn(List<String> villes);

    // 8. Trouver les adresses avec un code postal dans une plage spécifique
    List<Adresse> findByCodePostalBetween(Integer codePostalDebut, Integer codePostalFin); // Changé en Integer

    // 9. Trouver les adresses avec un code postal supérieur au code postal passé en paramètre
    List<Adresse> findByCodePostalGreaterThan(Integer codePostal); // Changé en Integer

    // 10. Trouver les adresses avec un code postal supérieur ou égal au code postal passé en paramètre
    List<Adresse> findByCodePostalGreaterThanEqual(Integer codePostal); // Changé en Integer

    // 11. Trouver les adresses avec un code postal inférieur au code postal passé en paramètre
    List<Adresse> findByCodePostalLessThan(Integer codePostal); // Changé en Integer

    // 12. Trouver les adresses avec un code postal inférieur ou égal au code postal passé en paramètre
    List<Adresse> findByCodePostalLessThanEqual(Integer codePostal); // Changé en Integer

    // 13. Trouver les adresses dont la rue commence par, dans une ville, triées par code postal
    List<Adresse> findByRueStartingWithAndVilleOrderByCodePostal(String debutRue, String ville);

    // 14. Trouver les adresses dont le nom de rue commence par une chaîne spécifique
    List<Adresse> findByRueStartingWith(String debutRue);

    // 15. Trouver les adresses dont le nom de ville se termine par une terminaison spécifique
    List<Adresse> findByVilleEndingWith(String finVille);

    // 16. Trouver les adresses où le champ rue est null
    List<Adresse> findByRueIsNull();

    // 17. Trouver les adresses où la ville n'est pas null
    List<Adresse> findByVilleIsNotNull();

    Optional<Adresse> findByRue(String rue);
}