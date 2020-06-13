package com.Eragoo.Blog;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api")
public class Hello {
    @GetMapping("/hello")
    public ResponseEntity<String> get() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("kek");
    }
}
