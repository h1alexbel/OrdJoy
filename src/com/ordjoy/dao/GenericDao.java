package com.ordjoy.dao;

import com.ordjoy.entity.Entity;
import com.ordjoy.dao.filter.Filter;
import com.ordjoy.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface GenericDao<K, E extends Entity, F extends Filter> {

    /**
     * Saves Entity in database
     *
     * @param e Entity to save
     * @return Entity with established id
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    E save(E e) throws DaoException;

    /**
     * Find Entity by it id and return {@link Optional} value of it or empty {@link Optional}
     *
     * @param id Entity id by which it will be found
     * @return {@link Optional} value of Entity if present in or empty {@link Optional}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    Optional<E> findById(K id) throws DaoException;

    /**
     * Find all Entities in database
     * @return List of Entities
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<E> findAll(F filter) throws DaoException;

    /**
     * Update Entity in database
     * @param entity new value of Entity
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    void update(E entity) throws DaoException;

    /**
     * Delete Entity by id
     * @param id Entity id which need to delete it
     * @return boolean value - {@code true} if success or {@code false} if it failed
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    boolean deleteById(K id) throws DaoException;
}