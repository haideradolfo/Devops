package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.PromotionRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.PromotionResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Promotion;

@Mapper(componentModel = "spring")
public interface PromotionMapper {

    @Mapping(target = "idPromotion", ignore = true)
    @Mapping(target = "articles", ignore = true)
    Promotion toEntity(PromotionRequest request);

    PromotionResponse toDto(Promotion promotion);
}