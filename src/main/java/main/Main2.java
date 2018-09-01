package main;

import java.sql.*;
import java.util.Properties;

public class Main2 {
    public static void main(String[] args) {
        new Main2().run();
    }

    private void run() {
        Properties properties = new Properties();
        properties.setProperty("user","Lemetriss");
        properties.setProperty("password","123");

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "Lemetriss", "123");
            createTable(con);
            Student student = new Student("Dmitrij", "Dmitrenko", 35);
            fillTable(con, student);
            dropTable(con);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dropTable(Connection con) throws SQLException {
        Statement statement = con.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS student");
        statement.close();
    }

    private void fillTable(Connection con, Student student) throws SQLException {
        PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO test.student (name, lastName, age) VALUES (?,?,?)");
        preparedStatement.setString(1, student.getName());
        preparedStatement.setString(2, student.getLastName());
        preparedStatement.setInt(3, student.getAge());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    private void createTable(Connection con) throws SQLException {
        Statement statement = con.createStatement();
        statement.executeUpdate(
                "CREATE TABLE student (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR(30), lastName VARCHAR(50), age INT)");
        statement.close();
    }
}