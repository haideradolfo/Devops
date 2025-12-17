package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.Detail_CommandeRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.Detail_CommandeResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Detail_Commande;

@Mapper(componentModel = "spring")
public interface Detail_CommandeMapper {

    @Mapping(target = "idDetailCommande", ignore = true)
    @Mapping(target = "commande", ignore = true)
    @Mapping(target = "article", ignore = true)
    Detail_Commande toEntity(Detail_CommandeRequest request);

    Detail_CommandeResponse toDto(Detail_Commande detail_commande);
}