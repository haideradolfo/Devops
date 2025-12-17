package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ClientRequest {
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private Long adresseId;
    private Long carteFideliteId;
}