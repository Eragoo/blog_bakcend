package com.Eragoo.Blog.article.genre;

import com.Eragoo.Blog.article.genre.dto.GenreCommand;
import com.Eragoo.Blog.article.genre.dto.GenreDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

@Mapper
public interface GenreMapper {
    GenreDto entityToDto(Genre genre);

    Set<GenreDto> entityListToSetDto(List<Genre> genreList);

    @Mapping(target = "id", ignore = true)
    Genre dtoToEntity(GenreDto genreDto);

    @Mapping(target = "id", ignore = true)
    void updateGenreFromCommand(GenreCommand command, @MappingTarget Genre genre);
}