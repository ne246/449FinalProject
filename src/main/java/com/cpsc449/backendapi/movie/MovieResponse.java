package com.cpsc449.backendapi.movie;

import java.time.LocalDateTime;

public record MovieResponse(
        Long id,
        String title,
        String genre,
        Integer releaseYear,
        Boolean watched,
        Integer rating,
        LocalDateTime createdAt
) {
    public static MovieResponse from(Movie movie) {
        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getGenre(),
                movie.getReleaseYear(),
                movie.getWatched(),
                movie.getRating(),
                movie.getCreatedAt()
        );
    }
}
