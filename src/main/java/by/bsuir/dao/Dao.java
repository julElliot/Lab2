package by.bsuir.dao;

import java.util.List;

/**
 * The interface Dao.
 *
 * @param <T> the type parameter
 */
public interface Dao<T> {
    /**
     * Get t.
     *
     * @param id the id
     * @return the t
     * @throws Exception the exception
     */
    T get(int id) throws Exception;

    /**
     * Gets all.
     *
     * @return the all
     * @throws Exception the exception
     */
    List<T> getAll() throws Exception;
}