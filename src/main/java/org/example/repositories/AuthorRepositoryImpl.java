package org.example.repositories;

import org.example.models.entities.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepositoryImpl extends BaseRepositoryImpl<Author,Integer> implements AuthorRepository{


    public AuthorRepositoryImpl(){
        super("AUTHOR","AUTHOR_ID");
    }
    @Override
    public Author add(Author author) {

        try(Connection conn = openConnection()){

            conn.setAutoCommit(false);

            PreparedStatement psmt = conn.prepareStatement("""
                                                                INSERT INTO Author (firstname,lastname,pseudo)
                                                                VALUES(?,?,?)
                                                                RETURNING *""");

            psmt.setString(1, author.getFirstname());
            psmt.setString(2, author.getLastname());
            psmt.setString(3, author.getPseudo());

            ResultSet rs = psmt.executeQuery();

            conn.commit();

            return buildEntity(rs);

        }catch(SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }

    }

    @Override
    public List<Author> getAll(String tag) {

        try(Connection conn = openConnection()){

            PreparedStatement psmt = conn.prepareStatement("""
                                                    SELECT  * 
                                                    FROM AUTHOR
                                                    WHERE FIRSTNAME LIKE ? OR 
                                                            LASTNAME LIKE ? OR 
                                                            PSEUDO LIKE ?
                                                    """);

            psmt.setString(1,"%" + tag + "%");
            psmt.setString(2,"%" + tag + "%");
            psmt.setString(3,"%" + tag + "%");

            ResultSet rs = psmt.executeQuery();
            List<Author> authors = new ArrayList<>();

            while (rs.next()){
                authors.add(buildEntity(rs));
            }

            return authors;

        }catch(SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }


    @Override
    public boolean update(Integer id, Author author) {

        try(Connection conn = openConnection()){

            PreparedStatement psmt = conn.prepareStatement("""
                                                                UPDATE AUTHOR
                                                                SET FIRSTNAME = ?,
                                                                    LASTNAME = ?,
                                                                    PSEUDO = ?
                                                                WHERE AUTHOR_ID = ?
                                                                """);

            psmt.setString(1, author.getFirstname());
            psmt.setString(2, author.getLastname());
            psmt.setString(3, author.getPseudo());
            psmt.setInt(4,id);

            return psmt.executeUpdate() == 1;

        }catch (SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }


    @Override
    public Author buildEntity(ResultSet rs) throws SQLException {

        Integer id = rs.getInt("Author_id");
        String firstname = rs.getString(("firstname"));
        String lastname = rs.getString(("lastname"));
        String pseudo = rs.getString(("pseudo"));

        return new Author(id,firstname,lastname,pseudo);
    }
}





























