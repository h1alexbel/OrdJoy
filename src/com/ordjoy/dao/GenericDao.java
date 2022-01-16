package com.ordjoy.dao;

import com.ordjoy.entity.Entity;
import com.ordjoy.dao.filter.Filter;
import com.ordjoy.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface GenericDao<K, E extends Entity, F extends Filter> {

    E save(E e) throws DaoException;

    Optional<E> findById(K id) throws DaoException;

    List<E> findAll(F filter) throws DaoException;

    void update(E entity) throws DaoException;

    boolean deleteById(K id) throws DaoException;
}