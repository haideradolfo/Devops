package tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.mapper;

import org.mapstruct.Mapper;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.ArticleRequest;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.dto.ArticleResponse;
import tn.esprit.twin.schenatohaider.tpcafe_schenato_haider.entities.Article;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    Article toEntity(ArticleRequest request);
    ArticleResponse toDto(Article article);
}