package com.Eragoo.Blog.article.genre;

import com.Eragoo.Blog.article.genre.dto.GenreDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/genre")
@AllArgsConstructor
public class GenreController {
    private GenreService genreService;

    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> getGenre(@PathVariable long id) {
        return ResponseEntity.ok(genreService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Set<GenreDto>> getAll() {
        return ResponseEntity.ok(genreService.getAll());
    }

    @PostMapping
    public ResponseEntity<GenreDto> create(GenreDto genreDto) {
        return new ResponseEntity<>(genreService.create(genreDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        genreService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
