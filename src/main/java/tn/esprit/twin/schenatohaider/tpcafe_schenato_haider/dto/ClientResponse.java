package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto;

import lombok.*;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Adresse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.CarteFidelite;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ClientResponse {
    private Long idClient;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private Adresse adresse;
    private CarteFidelite carteFidelite;
}