package com.ordjoy.dao;

import com.ordjoy.entity.AlbumReview;
import com.ordjoy.dao.filter.AlbumFilter;
import com.ordjoy.entity.Album;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface AlbumDao extends GenericDao<Long, Album, AlbumFilter> {

    /**
     * Find {@link Album} from database by it's title
     *
     * @param albumTitle by which will be found
     * @return {@link Optional} of {@link Album} if present or empty {@link Optional}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    Optional<Album> findAlbumByTitle(String albumTitle) throws DaoException;

    /**
     * Finds {@link AlbumReview} by Album title from database
     *
     * @param albumTitle by which will be found
     * @return List of {@link AlbumReview}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<AlbumReview> findAlbumReviewsByAlbumTitle(String albumTitle, DefaultFilter filter) throws DaoException;

    /**
     * Finds {@link AlbumReview} by Album id from database
     *
     * @param albumId by which will be found
     * @return List of {@link AlbumReview}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<AlbumReview> findAlbumReviewsByAlbumId(Long albumId, DefaultFilter filter) throws DaoException;

    /**
     * Gets all records from table in database
     * @return Long value of table records
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    Long getTableRecords() throws DaoException;
}