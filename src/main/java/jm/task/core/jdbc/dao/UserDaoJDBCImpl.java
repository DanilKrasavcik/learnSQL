package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection = Util.getConnection();
    private static final String SAVE_NEW_USER = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Statement statement =connection.createStatement()) {
            
            String query2 ="create table if not exists users (id bigint PRIMARY KEY AUTO_INCREMENT" +
                    ", name varchar(45), lastName varchar(45), age tinyint);";
            statement.executeUpdate(query2);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Statement statement =connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String str = String.format("User с именем – %s добавлен в базу данных",name);
        try(Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SAVE_NEW_USER)) {
    
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
    
            System.out.println(str);
        } catch (SQLException e) {
        e.printStackTrace();
        }
    
    }



    public void removeUserById(long id) {

        String query2 = String.format("DELETE FROM users WHERE ID = id");
        try(Statement statement =connection.createStatement()) {
            statement.executeUpdate(query2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement statement =connection.createStatement()) {
            
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                
                userList.add(user);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try(Statement statement =connection.createStatement()) {
            statement.execute("DELETE FROM users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
