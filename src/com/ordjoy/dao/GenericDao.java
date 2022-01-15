package com.ordjoy.dao;

import com.ordjoy.entity.Entity;
import com.ordjoy.dao.filter.Filter;

import java.util.List;
import java.util.Optional;

public interface GenericDao<K, E extends Entity, F extends Filter> {

    E save(E e);

    Optional<E> findById(K id);

    List<E> findAll(F filter);

    void update(E entity);

    boolean deleteById(K id);
}