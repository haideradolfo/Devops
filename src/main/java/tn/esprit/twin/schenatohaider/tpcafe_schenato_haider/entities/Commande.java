package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "commande")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idCommande;

    LocalDate dateCommande; // ← Supprimé @JsonProperty

    float totalCommande; // ← Supprimé @JsonProperty

    @Enumerated(EnumType.STRING)
    StatusCommande statusCommande; // ← Supprimé @JsonProperty

    @ManyToOne
    @JoinColumn(name = "client_id")
    Client client; // ← Supprimé @JsonProperty

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    List<Detail_Commande> details;
}