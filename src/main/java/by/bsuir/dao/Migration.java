package by.bsuir.dao;

import java.sql.SQLException;

public interface Migration<T> {
    /**
     * Create.
     *
     * @param entity the entity
     * @throws SQLException the sql exception
     */
    void create(T entity) throws SQLException;
}