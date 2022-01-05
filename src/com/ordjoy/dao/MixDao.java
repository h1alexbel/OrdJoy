package com.ordjoy.dao;

import com.ordjoy.filter.DefaultFilter;
import com.ordjoy.filter.MixFilter;
import com.ordjoy.entity.Mix;

import java.util.List;
import java.util.Optional;

public interface MixDao extends GenericDao<Long, Mix> {

    Mix saveMix(Mix mix);

    List<Mix> findAll(MixFilter filter);

    Optional<List<Mix>> findMixesByGenreName(String genreName, DefaultFilter filter);

    Optional<List<Mix>> findMixesByGenreId(Long genreId, DefaultFilter filter);
}