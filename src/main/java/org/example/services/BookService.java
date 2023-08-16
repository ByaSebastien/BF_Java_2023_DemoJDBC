package org.example.services;

import org.example.models.entities.Author;
import org.example.models.entities.Book;
import org.example.repositories.AuthorRepository;
import org.example.repositories.BookRepository;

import java.util.List;

public class BookService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository,AuthorRepository authorRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Book Add(Book book){

        if(book.getAuthor() != null){
            Author newAuthor = authorRepository.add(book.getAuthor());
            book.setAuthorId(newAuthor.getId());
        }

        return bookRepository.add(book);
    }

    public Book getOne(int id){

        return bookRepository.getOne(id);
    }

    public List<Book> getAll(){

        return bookRepository.getAll();
    }

    public boolean update(Integer id, Book book){

        return bookRepository.update(id,book);
    }

    public boolean delete(Integer id){

        return bookRepository.delete(id);
    }
}


























