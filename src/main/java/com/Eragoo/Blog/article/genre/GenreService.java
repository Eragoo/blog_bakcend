package com.Eragoo.Blog.article.genre;

import com.Eragoo.Blog.article.genre.dto.GenreCommand;
import com.Eragoo.Blog.article.genre.dto.GenreDto;
import com.Eragoo.Blog.article.genre.dto.GenreFilteringCommand;
import com.Eragoo.Blog.error.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Set<GenreDto> getAll(GenreFilteringCommand filteringDto) {
        Specification<Genre> specification = filteringDto.getSpecification();
        List<Genre> genres = genreRepository.findAll(specification);
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

    @Transactional
    public GenreDto update(long id, GenreCommand genreCommand) {
        Genre genre = getGenreIfExist(id);
        genreMapper.updateGenreFromCommand(genreCommand, genre);
        return genreMapper.entityToDto(genre);
    }

    private Genre getGenreIfExist(long id) {
        return genreRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Genre with id " + id + " not found"));
    }
}
