package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "promotion")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPromotion;

    String pourcentagePromo;

    LocalDate dateDebutPromo;

    LocalDate dateFinPromo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "promotion_article",
            joinColumns = @JoinColumn(name = "promotion_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    @Builder.Default // ← AJOUTER CETTE LIGNE
    List<Article> articles = new ArrayList<>();
}