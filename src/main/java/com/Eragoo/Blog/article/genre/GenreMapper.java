package com.Eragoo.Blog.article.genre;

import com.Eragoo.Blog.article.genre.dto.GenreDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper
public interface GenreMapper {
    GenreDto entityToDto(Genre genre);
    Set<GenreDto> entityListToSetDto(List<Genre> genreList);
    @Mapping(target = "id", ignore = true)
    Genre dtoToEntity(GenreDto genreDto);
}