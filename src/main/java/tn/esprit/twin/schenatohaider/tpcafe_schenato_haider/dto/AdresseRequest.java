package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class AdresseRequest {
    private String rue;
    private String ville;
    private int codePostal;
}