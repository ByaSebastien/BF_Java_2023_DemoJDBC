package org.example.repositories;

import org.example.models.entities.Author;
import org.example.models.entities.Book;

import java.sql.*;
import java.util.List;

public class BookRepositoryImpl extends BaseRepositoryImpl<Book,Integer> implements BookRepository {

    public BookRepositoryImpl(){
        super("BOOK","BOOK_ID");
    }

    @Override
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

            return buildEntity(rs);

        }catch (SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
    @Override
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

    @Override
    public Book getOneWithInfo(Integer id) {

        try(Connection conn = openConnection()){

            PreparedStatement psmt = conn.prepareStatement("""
        SELECT BOOK_ID,TITLE,DESCRIPTION,BOOK.AUTHOR_ID,FIRSTNAME,LASTNAME,PSEUDO
        FROM BOOK JOIN AUTHOR ON BOOK.AUTHOR_ID = AUTHOR.AUTHOR_ID
        WHERE BOOK_ID = ?
""");

            psmt.setInt(1,id);

            ResultSet rs = psmt.executeQuery();

            if(!rs.next()){
                throw new RuntimeException();
            }

            return buildEntityWithInfo(rs);

        }catch (SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
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

    @Override
    public Book buildEntity(ResultSet rs) throws SQLException {

        Integer id = rs.getInt("BOOK_ID");
        String title = rs.getString("TITLE");
        String description = rs.getString("DESCRIPTION");
        Integer authorId = rs.getInt("AUTHOR_ID");

        return new Book(id,title,description,authorId);
    }

    public Book buildEntityWithInfo(ResultSet rs) throws SQLException {

        Integer id = rs.getInt("BOOK_ID");
        String title = rs.getString("TITLE");
        String description = rs.getString("DESCRIPTION");
        Integer authorId = rs.getInt("AUTHOR_ID");
        Book book = new Book(id,title,description,authorId);
        String firstname = rs.getString("FIRSTNAME");
        String lastname = rs.getString("LASTNAME");
        String pseudo = rs.getString("PSEUDO");
        book.setAuthor(new Author(authorId,firstname,lastname,pseudo));

        return book;
    }
}
