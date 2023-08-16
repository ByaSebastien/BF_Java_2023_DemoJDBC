package org.example.repositories;

import org.example.models.entities.User;

public interface UserRepository extends BaseRepository<User,Integer>{

    User getByLogin(String login);
}
