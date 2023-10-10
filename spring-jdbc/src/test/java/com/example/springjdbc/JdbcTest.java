package com.example.springjdbc;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class JdbcTest {

    @Test
    public void test() throws ClassNotFoundException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "123456");
//        connection.createArrayOf()
        System.out.println(connection.getClientInfo().toString());
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        System.out.println(connection.getNetworkTimeout());
        System.out.println(connection.getSchema());
        PreparedStatement preparedStatement = connection.prepareStatement("select * from t_student");
//        preparedStatement
        ResultSet resultSet = preparedStatement.executeQuery();
//        resultSet
        while (resultSet.next()) {
            System.out.println(resultSet.getLong(1));
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

}
