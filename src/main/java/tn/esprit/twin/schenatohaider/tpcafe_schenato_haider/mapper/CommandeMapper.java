package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.CommandeRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.CommandeResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Commande;

@Mapper(componentModel = "spring")
public interface CommandeMapper {

    @Mapping(target = "idCommande", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "details", ignore = true)
    Commande toEntity(CommandeRequest request);

    CommandeResponse toDto(Commande commande);
}