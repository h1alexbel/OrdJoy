package com.ordjoy.dao;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.TrackFilter;
import com.ordjoy.entity.*;
import com.ordjoy.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface TrackDao extends GenericDao<Long, Track, TrackFilter> {

    /**
     * Links {@link Track} and {@link Mix} in mutual table in database
     *
     * @param mixThatExists   {@link Mix} that exists in database
     * @param trackThatExists {@link Track} that exists in database
     * @return boolean value - {@code true} if successfully linked or {@code false} if failed
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    boolean addExistingTrackToMix(Mix mixThatExists, Track trackThatExists) throws DaoException;

    /**
     * Find {@link Track} from database by it's name
     *
     * @param trackTitle by which will be found
     * @return {@link Optional} of {@link Track} if present or empty {@link Optional}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    Optional<Track> findByTrackTitle(String trackTitle) throws DaoException;

    /**
     * Finds List of {@link Track} by {@link Album} id from database
     *
     * @param albumId by which will be found
     * @return List of {@link Track}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<Track> findTracksByAlbumId(Long albumId, DefaultFilter filter) throws DaoException;

    /**
     * Finds List of {@link Track} by {@link Album} name from database
     *
     * @param albumName by which will be found
     * @return List of {@link Track}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<Track> findTracksByAlbumName(String albumName, DefaultFilter filter) throws DaoException;

    /**
     * Gets table records from database
     *
     * @return Long value that represents table records in database
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    Long getTableRecords() throws DaoException;
}