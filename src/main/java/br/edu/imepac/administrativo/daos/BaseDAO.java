package br.edu.imepac.administrativo.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDAO<T> {
    protected Connection connection;

    public BaseDAO() throws SQLException {
        this.connection = ConexaoDatabase.getConnection();
    }

    public abstract void save(T entity) throws SQLException;

    public abstract T findById(int id) throws SQLException;

    public abstract void update(T entity) throws SQLException;

    public abstract void delete(int id) throws SQLException;

}
