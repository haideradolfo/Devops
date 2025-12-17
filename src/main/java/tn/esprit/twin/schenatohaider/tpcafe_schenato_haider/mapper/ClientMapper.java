package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.ClientRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.ClientResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "idClient", ignore = true)
    @Mapping(target = "adresse", ignore = true)
    @Mapping(target = "carteFidelite", ignore = true)
    @Mapping(target = "commandes", ignore = true)
    Client toEntity(ClientRequest request);

    ClientResponse toDto(Client client);
}