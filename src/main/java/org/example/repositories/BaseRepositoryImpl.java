package org.example.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRepositoryImpl<TEntity,TKey> implements BaseRepository<TEntity,TKey>{

    private String tableName;
    private String columnIdName;

    public BaseRepositoryImpl(String tableName, String columnIdName) {
        this.tableName = tableName;
        this.columnIdName = columnIdName;
    }

    @Override
    public abstract TEntity add(TEntity entity);

    @Override
    public TEntity getOne(TKey id) {

        try(Connection conn = openConnection()){

            PreparedStatement psmt = conn.prepareStatement("SELECT * " +
                                                                "FROM " + tableName + " " +
                                                                "WHERE " + columnIdName + " = ?" );

            if(id instanceof Integer i){
                psmt.setInt(1, i);
            }
            if(id instanceof String s){
                psmt.setString(1,s);
            }

            ResultSet rs = psmt.executeQuery();

            if(!rs.next()){
                throw new RuntimeException("Entity not found!");
            }

            return buildEntity(rs);

        }catch (SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public List<TEntity> getAll() {

        try(Connection conn = openConnection()){

            PreparedStatement psmt = conn.prepareStatement("SELECT * " +
                                                               "FROM " + tableName);

            ResultSet rs = psmt.executeQuery();

            List<TEntity> entities = new ArrayList<>();

            while(rs.next()){
                entities.add(buildEntity(rs));
            }

            return entities;

        }catch (SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public abstract boolean update(TKey id, TEntity entity);

    @Override
    public boolean delete(TKey id) {

        try(Connection conn = openConnection()){

            PreparedStatement psmt = conn.prepareStatement("DELETE FROM " + tableName + " " +
                                                               "WHERE " + columnIdName + " = ?");

            if(id instanceof Integer i){
                psmt.setInt(1, i);
            }
            if(id instanceof String s){
                psmt.setString(1,s);
            }

            return psmt.executeUpdate() == 1;

        }catch (SQLException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    protected abstract TEntity buildEntity(ResultSet rs) throws SQLException;

    protected Connection openConnection(){

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
