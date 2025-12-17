package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class AdresseResponse {
    private Long idAdresse;
    private String rue;
    private String ville;
    private int codePostal;
}