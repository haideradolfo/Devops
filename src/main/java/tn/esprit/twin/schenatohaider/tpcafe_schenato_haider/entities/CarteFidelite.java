package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "cartefidelite")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class CarteFidelite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idCarteFidelite;

    @Column(name = "pointsAccumules")
    int pointsAccumules; // ← Supprimé @JsonProperty

    @Temporal(TemporalType.DATE)
    LocalDate dateCreation; // ← Supprimé @JsonProperty

    @OneToOne(mappedBy = "carteFidelite")
    @ToString.Exclude
    @JsonIgnore
    Client client;
}