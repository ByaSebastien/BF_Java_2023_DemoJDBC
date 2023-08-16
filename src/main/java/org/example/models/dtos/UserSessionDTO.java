package org.example.models.dtos;

import org.example.models.entities.User;

public class UserSessionDTO {

    private Integer id;
    private String username;
    private String token;

    public UserSessionDTO(User user){

        this.id = user.getId();
        this.username = user.getUsername();
        // generate token
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
