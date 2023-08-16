package org.example;

import org.example.models.entities.Book;
import org.example.repositories.AuthorRepositoryImpl;
import org.example.repositories.BookRepositoryImpl;
import org.example.services.BookService;

import java.util.List;

public class Main {
    public static void main(String[] args) {


        BookService bookService = new BookService(new BookRepositoryImpl(),new AuthorRepositoryImpl());

        Book newBook = new Book("Yasmine","Ce reve bleu...",1);
        bookService.update(55,newBook);

        List<Book> books = bookService.getAll();

        books.forEach(System.out::println);

//        Book book = bookService.getOne(1);
//
//        System.out.println(book);

        System.out.println(bookService.delete(31) + " Lignes afféctées");


//        System.out.println(bookService.add(newBook));
    }
}