package br.edu.imepac.administrativo.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/CLINICA_MEDICA_POO";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    /**
     * @return Connection
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Erro ao carregar o driver JDBC do MySQL!", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

