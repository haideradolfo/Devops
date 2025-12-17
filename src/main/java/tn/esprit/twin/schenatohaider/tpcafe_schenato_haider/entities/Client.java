package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "client")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idClient;

    @Column(name = "cin")
    private Long cin;

    @Column(name = "firstName")
    String nom; // ← Supprimé @JsonProperty

    @Column(name = "lastName")
    String prenom; // ← Supprimé @JsonProperty

    @Temporal(TemporalType.DATE)
    LocalDate dateNaissance; // ← Supprimé @JsonProperty

    @ManyToOne
    @JoinColumn(name = "adresse_id")
    Adresse adresse; // ← Supprimé @JsonProperty

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "carte_fidelite_id")
    CarteFidelite carteFidelite; // ← Supprimé @JsonProperty

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    List<Commande> commandes;
}