package com.cpsc449.backendapi.movie;

import com.cpsc449.backendapi.exception.ForbiddenException;
import com.cpsc449.backendapi.exception.NotFoundException;
import com.cpsc449.backendapi.user.User;
import com.cpsc449.backendapi.user.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public MovieService(MovieRepository movieRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public MovieResponse create(Long userId, MovieRequest request) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Authenticated user not found"));

        Movie movie = new Movie();
        applyRequest(movie, request);
        movie.setOwner(owner);

        return MovieResponse.from(movieRepository.save(movie));
    }

    public List<MovieResponse> getAll(Long userId) {
        return movieRepository.findAllByOwnerId(userId).stream()
                .map(MovieResponse::from)
                .toList();
    }

    public MovieResponse getOne(Long userId, Long movieId) {
        return MovieResponse.from(getOwnedMovie(userId, movieId));
    }

    public MovieResponse update(Long userId, Long movieId, MovieRequest request) {
        Movie movie = getOwnedMovie(userId, movieId);
        applyRequest(movie, request);
        return MovieResponse.from(movieRepository.save(movie));
    }

    public void delete(Long userId, Long movieId) {
        movieRepository.delete(getOwnedMovie(userId, movieId));
    }

    private Movie getOwnedMovie(Long userId, Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException("Movie with id " + movieId + " not found"));

        if (!movie.getOwner().getId().equals(userId)) {
            throw new ForbiddenException("You do not own this movie");
        }

        return movie;
    }

    private void applyRequest(Movie movie, MovieRequest request) {
        movie.setTitle(request.title());
        movie.setGenre(request.genre());
        movie.setReleaseYear(request.releaseYear());
        movie.setWatched(request.watched());
        movie.setRating(request.rating());
    }
}
