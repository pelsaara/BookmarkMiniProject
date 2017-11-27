package bookmarkdb;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for all data access object classes.
 */
public interface AbstractDAO<T, K> {

    T create(T t) throws SQLException;

    T findOne(T t) throws SQLException;

    List<T> findAll() throws SQLException;

    void update(T t) throws SQLException;

    void delete(T t) throws SQLException;
}
