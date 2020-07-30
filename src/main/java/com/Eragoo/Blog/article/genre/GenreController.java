package com.Eragoo.Blog.article.genre;

import com.Eragoo.Blog.article.genre.dto.GenreCommand;
import com.Eragoo.Blog.article.genre.dto.GenreDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/genre")
@AllArgsConstructor
public class GenreController {
    private GenreService genreService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(T(com.Eragoo.Blog.role.Permission).VIEW_GENRES, " +
            "T(com.Eragoo.Blog.role.Permission).MANAGE_GENRES)")
    public ResponseEntity<GenreDto> get(@PathVariable long id) {
        return ResponseEntity.ok(genreService.get(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority(T(com.Eragoo.Blog.role.Permission).VIEW_GENRES, " +
            "T(com.Eragoo.Blog.role.Permission).MANAGE_GENRES)")
    public ResponseEntity<Set<GenreDto>> getAll() {
        return ResponseEntity.ok(genreService.getAll());
    }

    @PostMapping
    @PreAuthorize("hasAuthority(T(com.Eragoo.Blog.role.Permission).MANAGE_GENRES)")
    public ResponseEntity<GenreDto> create(GenreDto genreDto) {
        return new ResponseEntity<>(genreService.create(genreDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.Eragoo.Blog.role.Permission).MANAGE_GENRES)")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        genreService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.Eragoo.Blog.role.Permission).MANAGE_GENRES)")
    public ResponseEntity<GenreDto> update(@PathVariable long id, GenreCommand genreCommand) {
        GenreDto updated = genreService.update(id, genreCommand);
        return ResponseEntity.ok(updated);
    }
}
