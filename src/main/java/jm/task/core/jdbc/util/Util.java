package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Util {

private static final String USER_NAME = "root";
private static final String PASSWORD = "root";
private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";



private static Connection connection;

public Util() {
	

	
}

public static Connection getConnection() {
	try {
		
		connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		System.out.println("Соединение установлено");
	} catch (SQLException e) {
		System.err.println("СОЕДИНЕНИЯ НЕТ");
		e.printStackTrace();
		
	}
	return connection;
}
}
