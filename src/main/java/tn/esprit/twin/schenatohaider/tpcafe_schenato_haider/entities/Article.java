package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "article")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArticle;

    private String nomArticle;

    private Double prixArticle;

    @Enumerated(EnumType.STRING)
    private TypeArticle typeArticle;

    @OneToMany(mappedBy = "article")
    @Builder.Default // ← AJOUTER
    private List<Detail_Commande> detailsCommande = new ArrayList<>();

    @ManyToMany(mappedBy = "articles", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default // ← AJOUTER
    private List<Promotion> promotions = new ArrayList<>();
}