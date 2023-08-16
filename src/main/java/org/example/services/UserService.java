package org.example.services;

import org.example.models.dtos.UserSessionDTO;
import org.example.models.entities.User;
import org.example.models.forms.UserForm;
import org.example.models.forms.UserLoginForm;
import org.example.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(UserForm user){

        if(!user.getPassword().equals(user.getConfirmPwd())){
            throw new RuntimeException();
        }
        User newUser = user.toEntity();
        String hashPwd = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        newUser.setPassword(hashPwd);

        return userRepository.add(newUser);
    }

    public UserSessionDTO login(UserLoginForm user){

        User existingUser = userRepository.getByLogin(user.getLogin());

        if(existingUser == null){
            throw new RuntimeException();
        }

        if (!BCrypt.checkpw(user.getPassword(), existingUser.getPassword())) {
            throw new RuntimeException();
        }

        return new UserSessionDTO(existingUser);
    }
}
