package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto;

import lombok.*;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.StatusCommande;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class CommandeRequest {
    private LocalDate dateCommande;
    private float totalCommande;
    private StatusCommande statusCommande;
    private Long clientId;
}