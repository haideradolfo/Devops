package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "adresse")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Adresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idAdresse;

    String rue;        // ← Supprimé @JsonProperty
    String ville;      // ← Supprimé @JsonProperty
    int codePostal;    // ← Supprimé @JsonProperty

    @OneToMany(mappedBy = "adresse")
    @ToString.Exclude
    @JsonIgnore
    List<Client> clients;
}