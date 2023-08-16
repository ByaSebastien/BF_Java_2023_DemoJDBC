package org.example.models;

public class Author {

    private Integer id;
    private String firstname;
    private String lastname;
    private String pseudo;

    public Author() {
    }

    public Author(String firstname, String lastname, String pseudo) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.pseudo = pseudo;
    }

    public Author(Integer id, String firstname, String lastname, String pseudo) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.pseudo = pseudo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
