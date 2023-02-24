package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
private static final String SAVE_NEW_USER = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
private Util util;
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Statement statement =util.getConnection().createStatement()) {
            
            String query2 ="create table if not exists users (id bigint PRIMARY KEY AUTO_INCREMENT, name varchar(45), lastName varchar(45), age tinyint);";
            statement.executeUpdate(query2);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Statement statement =util.getConnection().createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

public void saveUser(String name, String lastName, byte age) {
    String str = String.format("User с именем – %s добавлен в базу данных",name);
    try(Connection connection = util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SAVE_NEW_USER)) {
    
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, lastName);
        preparedStatement.setByte(3, age);
        preparedStatement.executeUpdate();
    
        System.out.println(str);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
}

/*public void saveUser(String name, String lastName, byte age) {
        String query = String.format("INSERT INTO users(name,lastName,age) VALUES('%s','%s','%s';",name,lastName,age);
        String str = String.format("User с именем – %s добавлен в базу данных",name);
        try(Statement statement =util.getConnection().createStatement()) {
            statement.executeQuery(query);
            System.out.println(str);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }*/

    public void removeUserById(long id) {
        long l = id;
        String query2 = String.format("DELETE FROM users WHERE id = %s",id);
        try(Statement statement =util.getConnection().createStatement()) {
            statement.executeUpdate(query2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement statement =util.getConnection().createStatement()) {
            
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
        try(Statement statement =util.getConnection().createStatement()) {
            statement.execute("DELETE FROM users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
