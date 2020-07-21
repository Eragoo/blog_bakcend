package com.Eragoo.Blog.article.genre;

import com.Eragoo.Blog.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class GenreService {
    private GenreRepository genreRepository;
    private GenreMapper genreMapper;

    public GenreDto getById(long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre with id " + id + " not found"));
        return genreMapper.genreToGenreDto(genre);
    }

    public Set<GenreDto> getAll() {
        List<Genre> genres = genreRepository.findAll();
        return genreMapper.genreListToGenreSetDto(genres);
    }

    public GenreDto create(GenreDto genreDto) {
        Genre genre = genreMapper.genreDtoToGenre(genreDto);
        Genre savedGenre = genreRepository.save(genre);
        return genreMapper.genreToGenreDto(savedGenre);
    }

    public void delete(long id) {
        genreRepository.deleteById(id);
    }
}
