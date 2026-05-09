package com.cpsc449.backendapi.movie;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findAllByOwnerId(Long ownerId);
}
