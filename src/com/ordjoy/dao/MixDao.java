package com.ordjoy.dao;

import com.ordjoy.entity.MixReview;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.MixFilter;
import com.ordjoy.entity.Mix;
import com.ordjoy.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface MixDao extends GenericDao<Long, Mix, MixFilter> {

    Optional<Mix> findMixByMixName(String mixName) throws DaoException;

    List<MixReview> findMixReviewByMixName(String mixName, DefaultFilter filter) throws DaoException;

    List<MixReview> findMixReviewsByMixId(Long mixId, DefaultFilter filter) throws DaoException;
}