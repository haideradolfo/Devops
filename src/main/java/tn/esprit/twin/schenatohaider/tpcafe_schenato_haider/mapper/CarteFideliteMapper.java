package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.mapper;

import org.mapstruct.Mapper;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.CarteFideliteRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.CarteFideliteResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.CarteFidelite;

@Mapper(componentModel = "spring")
public interface CarteFideliteMapper {
    CarteFidelite toEntity(CarteFideliteRequest request);
    CarteFideliteResponse toDto(CarteFidelite carteFidelite);
}