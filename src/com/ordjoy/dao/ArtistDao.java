package com.ordjoy.dao;

import com.ordjoy.filter.ArtistFilter;
import com.ordjoy.entity.Artist;

import java.util.List;

public interface ArtistDao extends GenericDao<Long, Artist> {

    List<Artist> findAll(ArtistFilter filter);
}