package org.example;

import org.example.models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        MiniCRUD miniCRUD = new MiniCRUD();

        Book newBook = new Book("Yasmine","Ce reve bleu...",1);
        miniCRUD.update(55,newBook);

        List<Book> books = miniCRUD.getAll();

        books.forEach(System.out::println);

//        Book book = miniCRUD.getOne(1);
//
//        System.out.println(book);

        System.out.println(miniCRUD.delete(31) + " Lignes afféctées");


//        System.out.println(miniCRUD.add(newBook));
    }
}