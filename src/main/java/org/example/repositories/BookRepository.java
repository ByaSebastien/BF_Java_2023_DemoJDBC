package org.example.repositories;

import org.example.models.Book;

import java.util.List;

public interface BookRepository {

    Book add(Book book);

    void add(List<Book> book);

    Book getOne(Integer id);

    List<Book> getAll();

    boolean update(Integer id, Book book);

    boolean delete(Integer id);
}
