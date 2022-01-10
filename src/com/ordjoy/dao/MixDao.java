package com.ordjoy.dao;

import com.ordjoy.entity.MixReview;
import com.ordjoy.filter.DefaultFilter;
import com.ordjoy.filter.MixFilter;
import com.ordjoy.entity.Mix;

import java.util.Optional;
import java.util.Set;

public interface MixDao extends GenericDao<Long, Mix, MixFilter> {

    Optional<Mix> findMixByMixName(String mixName);

    Set<MixReview> findMixReviewByMixName(String mixName, DefaultFilter filter);

    Set<MixReview> findMixReviewsByMixId(Long mixId, DefaultFilter filter);
}