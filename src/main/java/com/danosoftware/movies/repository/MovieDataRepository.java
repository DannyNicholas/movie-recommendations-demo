package com.danosoftware.movies.repository;

import com.danosoftware.movies.dto.MovieEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * This will be AUTO IMPLEMENTED by Spring into a Bean called movieDataRepository
 * CRUD refers Create, Read, Update, Delete
 */
public interface MovieDataRepository extends CrudRepository<MovieEntity, Long> {
}
