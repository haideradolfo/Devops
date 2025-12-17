package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "detail_commande")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Detail_Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idDetailCommande;

    int quantiteArticle;

    float sousTotalDetailArticle;

    float sousTotalDetailArticleApresPromo;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    Commande commande;

    @ManyToOne
    @JoinColumn(name = "article_id")
    Article article;
}