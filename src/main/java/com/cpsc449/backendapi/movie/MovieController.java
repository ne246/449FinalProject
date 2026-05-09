package com.cpsc449.backendapi.movie;

import com.cpsc449.backendapi.auth.AuthenticatedUser;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<MovieResponse> create(
            @AuthenticationPrincipal AuthenticatedUser user,
            @Valid @RequestBody MovieRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.create(user.id(), request));
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAll(@AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(movieService.getAll(user.id()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getOne(
            @AuthenticationPrincipal AuthenticatedUser user,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(movieService.getOne(user.id(), id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> update(
            @AuthenticationPrincipal AuthenticatedUser user,
            @PathVariable Long id,
            @Valid @RequestBody MovieRequest request
    ) {
        return ResponseEntity.ok(movieService.update(user.id(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal AuthenticatedUser user,
            @PathVariable Long id
    ) {
        movieService.delete(user.id(), id);
        return ResponseEntity.noContent().build();
    }
}
