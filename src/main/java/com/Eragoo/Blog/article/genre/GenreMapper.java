package com.Eragoo.Blog.article.genre;

import com.Eragoo.Blog.article.genre.dto.GenreDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper
public interface GenreMapper {
    GenreDto genreToGenreDto(Genre genre);
    Set<GenreDto> genreListToGenreSetDto(List<Genre> genreList);
    @Mapping(target = "id", ignore = true)
    Genre genreDtoToGenre(GenreDto genreDto);
}
