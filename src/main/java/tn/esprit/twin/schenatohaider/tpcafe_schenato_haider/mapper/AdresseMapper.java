package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.mapper;

import org.mapstruct.Mapper;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.AdresseRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.AdresseResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Adresse;

@Mapper(componentModel = "spring")
public interface AdresseMapper {
    Adresse toEntity(AdresseRequest request);
    AdresseResponse toDto(Adresse adresse);
}