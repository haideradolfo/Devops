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
public class CarteFideliteRequest {
    private int pointsAccumules;
    private LocalDate dateCreation;
}