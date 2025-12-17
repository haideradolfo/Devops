package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Detail_CommandeRequest {
    private int quantiteArticle;
    private float sousTotalDetailArticle;
    private float sousTotalDetailArticleApresPromo;
    private Long commandeId;
    private Long articleId;
}