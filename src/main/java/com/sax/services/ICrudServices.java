package com.sax.services;


import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ICrudServices<T, K> {
    List<T> getAll();
    List<T> getAllByIds(List<K> ids) throws SQLServerException;
    T getById(K id);
    T insert(T e) throws SQLServerException;
    void update(T e) throws SQLServerException;
    void delete(K id) throws SQLServerException;
    void deleteAll(Set<K> ids) throws SQLServerException;
    int getTotalPage(int amount);
    List<T> getPage(Pageable page);
    List<T> searchByKeyword(String keyword);
}
