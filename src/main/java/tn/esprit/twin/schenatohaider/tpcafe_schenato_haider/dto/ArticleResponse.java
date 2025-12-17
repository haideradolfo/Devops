package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto;

import lombok.*;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.TypeArticle;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ArticleResponse {
    private Long idArticle;
    private String nomArticle;
    private Double prixArticle;
    private TypeArticle typeArticle;
}