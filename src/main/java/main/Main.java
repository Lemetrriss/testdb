package main;

import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        Properties properties = new Properties();
        properties.setProperty("user", "Lemetriss");
        properties.setProperty("password", "123");

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", properties);
            createTable(con);
            addStudent(con);
            readStudent(con);
            deleteTable(con);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void readStudent(Connection con) throws SQLException {
        Statement stm = con.createStatement();
        ResultSet resultSet = stm.executeQuery("SELECT id, name, lastName, age FROM test.student WHERE name='Dima'");
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String lastName = resultSet.getString("lastName");
            int age = resultSet.getInt("age");
            System.out.println(id+") Name is: "+name+", lastName is: "+lastName+", Age of student is: "+age);
        }
        stm.close();
    }

    private void deleteTable(Connection con) throws SQLException {
        Statement st = con.createStatement();
        st.executeUpdate("DROP TABLE IF EXISTS student");
        st.close();
    }

    private void createTable(Connection con) throws SQLException {
        Statement stm = con.createStatement();
        stm.executeUpdate("CREATE TABLE student (id int NOT NULL PRIMARY KEY auto_increment, name varchar(30), lastName VARCHAR(50), age int)");
        stm.close();
    }

    private void addStudent(Connection con) throws SQLException {
        PreparedStatement preStm = con.prepareStatement("INSERT INTO student (name, lastName, age) VALUES (?,?,?)");
        preStm.setString(1, "Dima");
        preStm.setString(2, "Dmitrenko");
        preStm.setInt(3, 35);
        preStm.executeUpdate();
        preStm.close();
    }
}
