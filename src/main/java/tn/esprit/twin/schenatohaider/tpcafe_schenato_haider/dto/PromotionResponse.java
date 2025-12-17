package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto;

import lombok.*;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Article;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class PromotionResponse {
    private Long idPromotion;
    private String pourcentagePromo;
    private LocalDate dateDebutPromo;
    private LocalDate dateFinPromo;
    private List<Article> articles;
}