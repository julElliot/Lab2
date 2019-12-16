package by.bsuir.dao;

import java.sql.*;
import java.util.List;

public abstract class BaseMysql<T> implements AutoCloseable {
    private Connection connection;

    /**
     * The Statement.
     */
    protected Statement statement;

    /**
     * Instantiates a new Base mysql.
     */
    protected BaseMysql() {
    }


    /**
     * Gets connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Sets connection.
     *
     * @param connection the connection
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
        try {
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets field statement.
     *
     * @param statement the statement
     * @param entity    the entity
     * @throws SQLException the sql exception
     */
    protected abstract void setFieldStatement(PreparedStatement statement, T entity) throws SQLException;


    public int defaultCreate(String sql, T entity) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(sql);
            setFieldStatement(statement, entity);

            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
                throw new DaoException(e.getMessage());
            }
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
