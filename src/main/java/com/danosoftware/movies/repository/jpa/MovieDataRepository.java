package com.danosoftware.movies.repository.jpa;

import com.danosoftware.movies.dto.MovieEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

/**
 * This will be AUTO IMPLEMENTED by Spring into a Bean called movieDataRepository
 * CRUD refers Create, Read, Update, Delete
 */
@Profile({"postgres", "h2"})
public interface MovieDataRepository extends CrudRepository<MovieEntity, Long> {
}
