package org.example.repositories;

import org.example.models.Author;
import org.example.models.Book;

import java.util.List;

public interface AuthorRepository {

    Author add(Author author);

    Author getOne(Integer id);

    List<Author> getAll();
    List<Author> getAll(String tag);

    boolean update(Integer id, Author author);

    boolean delete(Integer id);
}
