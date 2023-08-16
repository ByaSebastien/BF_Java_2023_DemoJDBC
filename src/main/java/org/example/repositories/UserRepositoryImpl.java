package org.example.repositories;

import org.example.models.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepositoryImpl extends BaseRepositoryImpl<User,Integer> implements UserRepository{

    public UserRepositoryImpl(){
        super("SECURITY_USER","USER_ID");
    }

    @Override
    public User add(User user) {

        try(Connection conn = openConnection()){

            PreparedStatement psmt = conn.prepareStatement("""
                                                            INSERT INTO SECURITY_USER(USERNAME,EMAIL,PWD)
                                                            VALUES (?,?,?)
                                                               """);

            psmt.setString(1, user.getUsername());
            psmt.setString(2, user.getEmail());
            psmt.setString(3, user.getPassword());

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
    public boolean update(Integer id, User user) {

        try(Connection conn = openConnection()){

            PreparedStatement psmt = conn.prepareStatement("""
                                                                UPDATE SECURITY_USER 
                                                                SET USERNAME = ?,
                                                                    EMAIL = ?,
                                                                    PWD = ?
                                                                WHERE USER_ID = ?
                                                                """);

            psmt.setString(1, user.getUsername());
            psmt.setString(2, user.getEmail());
            psmt.setString(3, user.getPassword());
            psmt.setInt(4,id);

            return psmt.executeUpdate() == 1;

        }catch (SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    protected User buildEntity(ResultSet rs) throws SQLException {

        Integer id = rs.getInt("USER_ID");
        String username = rs.getString("USERNAME");
        String email = rs.getString("EMAIL");
        String password = rs.getString("PWD");

        return new User(id,username,email,password);
    }

    @Override
    public User getByLogin(String login) {

        try(Connection conn = openConnection()){

            PreparedStatement psmt = conn.prepareStatement("""
                                                                SELECT *
                                                                FROM SECURITY_USER
                                                                WHERE USERNAME like ?
                                                                    OR EMAIl like ?
                                                                """);
            psmt.setString(1,login);
            psmt.setString(2,login);

            ResultSet rs = psmt.executeQuery();

            if(!rs.next()){

                throw new RuntimeException("User not found");
            }

            return buildEntity(rs);


        }catch (SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }
}
