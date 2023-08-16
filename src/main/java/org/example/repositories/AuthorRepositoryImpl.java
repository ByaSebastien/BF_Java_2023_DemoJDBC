package org.example.repositories;

import org.example.models.Author;

import javax.management.RuntimeMBeanException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepositoryImpl implements AuthorRepository{


    @Override
    public Author add(Author author) {

        try(Connection conn = openConnection()){

            conn.setAutoCommit(false);

            PreparedStatement psmt = conn.prepareStatement("""
                                                                INSERT INTO Author
                                                                VALUES(?,?,?)
                                                                RETURNING *""");

            psmt.setString(1, author.getFirstname());
            psmt.setString(2, author.getLastname());
            psmt.setString(3, author.getPseudo());

            ResultSet rs = psmt.executeQuery();

            conn.commit();

            return builAuthor(rs);

        }catch(SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }

    }

    @Override
    public Author getOne(Integer id) {

        try(Connection conn = openConnection()){

            PreparedStatement psmt = conn.prepareStatement("""
                                                                  SELECT * 
                                                                  FROM AUTHOR
                                                                  WHERE AUTHOR_ID = ?""");
            psmt.setInt(1,id);

            ResultSet rs = psmt.executeQuery();

            if(!rs.next()){

                throw new RuntimeException("Author doesn't exist");
            }

            return builAuthor(rs);

        }catch (SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public List<Author> getAll() {

        try(Connection conn = openConnection()){

            Statement smt = conn.createStatement();

            ResultSet rs = smt.executeQuery("""
                                                    SELECT  * 
                                                    FROM AUTHOR
                                                    """);
            List<Author> authors = new ArrayList<>();

            while (rs.next()){
                authors.add(builAuthor(rs));
            }

            return authors;

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
                authors.add(builAuthor(rs));
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
    public boolean delete(Integer id) {

        try(Connection conn = openConnection()){

            PreparedStatement psmt = conn.prepareStatement("""
                                                                DELETE AUTHOR
                                                                WHERE AUTHOR_ID = ?
                                                                """);
            psmt.setInt(1,id);

            return psmt.executeUpdate() == 1;

        }catch (SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Author builAuthor(ResultSet rs) throws SQLException {

        Integer id = rs.getInt("Author_id");
        String firstname = rs.getString(("firstname"));
        String lastname = rs.getString(("lastname"));
        String pseudo = rs.getString(("pseudo"));

        return new Author(id,firstname,lastname,pseudo);
    }

    public Connection openConnection(){

        String connectionString = "jdbc:postgresql://localhost:5433/DemoJdbc";
        String user = "postgres";
        String password = "postgres";

        try {

            return DriverManager.getConnection(connectionString,user,password);

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }
}





























