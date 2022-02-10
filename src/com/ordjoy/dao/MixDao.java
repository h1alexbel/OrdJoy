package com.ordjoy.dao;

import com.ordjoy.entity.MixReview;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.MixFilter;
import com.ordjoy.entity.Mix;
import com.ordjoy.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface MixDao extends GenericDao<Long, Mix, MixFilter> {

    /**
     * Find {@link Mix} from database by it's name
     *
     * @param mixName by which will be found
     * @return {@link Optional} of {@link Mix} if present or empty {@link Optional}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    Optional<Mix> findMixByMixName(String mixName) throws DaoException;

    /**
     * Finds {@link MixReview} by {@link Mix} name from database
     *
     * @param mixName by which will be found
     * @return List of {@link MixReview}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<MixReview> findMixReviewByMixName(String mixName, DefaultFilter filter) throws DaoException;

    /**
     * Finds {@link MixReview} by {@link Mix} id from database
     *
     * @param mixId by which will be found
     * @return List of {@link MixReview}
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    List<MixReview> findMixReviewsByMixId(Long mixId, DefaultFilter filter) throws DaoException;

    /**
     * Gets all table records from database
     *
     * @return Long value that represents table records
     * @throws DaoException if {@link com.ordjoy.exception.DataBaseException} or any Database error be thrown
     */
    Long getTableRecords() throws DaoException;
}