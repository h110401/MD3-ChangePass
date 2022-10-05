package rikkei.academy.service;

import java.sql.SQLException;
import java.util.List;

public interface IGeneric<T> {
    List<T> findAll();

    void save(T t);

    void update(T t) throws SQLException;
}
