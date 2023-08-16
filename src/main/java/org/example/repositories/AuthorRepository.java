package org.example.repositories;

import org.example.models.entities.Author;

import java.util.List;

public interface AuthorRepository extends BaseRepository<Author,Integer>{
    List<Author> getAll(String tag);
}
