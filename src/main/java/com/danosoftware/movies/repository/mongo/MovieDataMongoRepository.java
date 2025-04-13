package com.danosoftware.movies.repository.mongo;

import com.danosoftware.movies.dto.MovieDocument;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Provides repository methods.
 * Provided by default:
 * Create/Update - `save(entity)` - insert if ID is null, update if ID exists
 * Read - `findById(id)` - find entity by ID
 * Delete - `deleteById` - delete entity by ID
 * Delete - `delete(entity)` - delete entity
 */
@Profile("mongo")
public interface MovieDataMongoRepository extends MongoRepository<MovieDocument, String> {
    List<MovieDocument> findByName(String name);

    List<MovieDocument> findByGenre(String genre);

    List<MovieDocument> findByNameAndGenre(String name, String genre);
}
