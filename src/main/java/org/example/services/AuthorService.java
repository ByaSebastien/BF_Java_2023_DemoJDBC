package org.example.services;

import org.example.models.entities.Author;
import org.example.repositories.AuthorRepository;

import java.util.List;

public class AuthorService {

    private AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository){

        this.authorRepository = authorRepository;
    }

    public List<Author> getAllFilter(String tag){

        return authorRepository.getAll(tag);
    }
}
