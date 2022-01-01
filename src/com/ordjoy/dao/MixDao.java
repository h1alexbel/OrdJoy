package com.ordjoy.dao;

import com.ordjoy.entity.Genre;
import com.ordjoy.filter.MixFilter;
import com.ordjoy.entity.Mix;
import com.ordjoy.entity.Track;

import java.util.List;
import java.util.Optional;

public interface MixDao extends GenericDao<Long, Track> {

    Mix saveMix(Mix mix);

    Genre saveGenre(Genre genre);

    Mix saveMixWithTracks(Mix mix, List<Track> tracks);

    List<Mix> findAll(MixFilter filter);

    Optional<List<Mix>> findMixesByGenreName(String genreName, MixFilter filter);

    Optional<List<Mix>> findMixesByGenreId(Long genreId, MixFilter filter);
}