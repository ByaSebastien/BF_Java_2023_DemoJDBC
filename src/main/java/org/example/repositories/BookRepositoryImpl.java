package org.example.repositories;

import org.example.models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {

    public Book add(Book book){

        try(Connection conn = openConnection()){

            PreparedStatement psmt = conn.prepareStatement("INSERT INTO BOOK (TITLE,DESCRIPTION,AUTHOR_ID)" +
                                                               "VALUES (?,?,?) RETURNING *");
            psmt.setString(1,book.getTitle());
            psmt.setString(2,book.getDescription());
            psmt.setInt(3,book.getAuthorId());

            ResultSet rs = psmt.executeQuery();

            if(!rs.next()){
                throw new RuntimeException("Failed");
            }

            return buildBook(rs);

        }catch (SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void add(List<Book> books){

        try(Connection conn = openConnection()){

            PreparedStatement psmt = conn.prepareStatement("INSERT INTO BOOK (TITLE,DESCRIPTION,AUTHOR_ID)" +
                    "VALUES (?,?,?)");

            for(Book book : books){
                psmt.setString(1,book.getTitle());
                psmt.setString(2,book.getDescription());
                psmt.setInt(3,book.getAuthorId());

                psmt.addBatch();
                psmt.clearParameters();
            }

            int[] nbRows = psmt.executeBatch();

        }catch (SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<Book> getAll(){

        List<Book> books = new ArrayList<>();
        try(Connection conn = openConnection()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Book");
            while (rs.next()){
                books.add(buildBook(rs));
            }
            conn.close();
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Book getOne(Integer id){

        Book book;
        try(Connection conn = openConnection()){

            PreparedStatement psmt = conn.prepareStatement("SELECT * FROM BOOK WHERE BOOK_ID = ?");

            psmt.setInt(1,id);

            ResultSet rs = psmt.executeQuery();

            if(rs.next()){
                 return buildBook(rs);
            }

            throw new RuntimeException("Le livre n'existe pas");

        }catch (SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public boolean update(Integer id,Book newBook){

        try(Connection conn = openConnection()){

            PreparedStatement psmt = conn.prepareStatement("UPDATE BOOK " +
                                                                    "SET TITLE = ? , " +
                                                                    "DESCRIPTION = ? , " +
                                                                    "AUTHOR_ID = ? " +
                                                                    "WHERE BOOK_ID = ?");

            psmt.setString(1,newBook.getTitle());
            psmt.setString(2, newBook.getDescription());
            psmt.setInt(3,newBook.getAuthorId());
            psmt.setInt(4,id);

            return psmt.executeUpdate() == 1;

        }catch (SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public boolean delete(Integer id){

        try(Connection conn = openConnection()){

            PreparedStatement psmt = conn.prepareStatement("DELETE FROM BOOK WHERE BOOK_ID = ?");
            psmt.setInt(1,id);
            return psmt.executeUpdate() == 1;

        }catch (SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Book buildBook(ResultSet rs) throws SQLException {

        Integer id = rs.getInt("BOOK_ID");
        String title = rs.getString("TITLE");
        String description = rs.getString("DESCRIPTION");
        Integer authorId = rs.getInt("AUTHOR_ID");

        return new Book(id,title,description,authorId);
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
