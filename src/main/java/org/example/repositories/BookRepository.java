package org.example.repositories;

import org.example.models.entities.Book;

import java.util.List;

public interface BookRepository extends BaseRepository<Book,Integer>{

    void add(List<Book> book);

    Book getOneWithInfo(Integer id);
}
