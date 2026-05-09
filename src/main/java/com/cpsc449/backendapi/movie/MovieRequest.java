package com.cpsc449.backendapi.movie;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MovieRequest(
        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Genre is required")
        String genre,

        @NotNull(message = "Release year is required")
        @Min(value = 1888, message = "Release year must be 1888 or later")
        @Max(value = 2100, message = "Release year must be 2100 or earlier")
        Integer releaseYear,

        @NotNull(message = "Watched is required")
        Boolean watched,

        @Min(value = 1, message = "Rating must be between 1 and 5")
        @Max(value = 5, message = "Rating must be between 1 and 5")
        Integer rating
) {
}
