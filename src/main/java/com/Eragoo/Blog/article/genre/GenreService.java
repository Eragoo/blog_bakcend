package com.Eragoo.Blog.article.genre;

import com.Eragoo.Blog.article.genre.dto.GenreDto;
import com.Eragoo.Blog.error.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class GenreService {
    private GenreRepository genreRepository;
    private GenreMapper genreMapper;

    public GenreDto get(long id) {
        Genre genre = getGenreIfExist(id);
        return genreMapper.entityToDto(genre);
    }

    public Set<GenreDto> getAll() {
        List<Genre> genres = genreRepository.findAll();
        return genreMapper.entityListToSetDto(genres);
    }

    public GenreDto create(GenreDto genreDto) {
        Genre genre = genreMapper.dtoToEntity(genreDto);
        Genre savedGenre = genreRepository.save(genre);
        return genreMapper.entityToDto(savedGenre);
    }

    public void delete(long id) {
        Genre genre = getGenreIfExist(id);
        genreRepository.delete(genre);
    }

    private Genre getGenreIfExist(long id) {
        return genreRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Genre with id " + id + " not found"));
    }
}
