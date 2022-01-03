package com.ordjoy.dao;

import com.ordjoy.filter.TrackFilter;
import com.ordjoy.entity.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TrackDao extends GenericDao<Long, Track> {

    Track saveOrder(Track track);

    Track saveTrackWithArtists(Track track, List<Artist> artists);

    Mix saveTrackInMix(Mix mix, Track track, Album album);

    List<Track> findAll(TrackFilter filter);

    Optional<List<Track>> findTracksByGenreId(Long genreId, TrackFilter filter);

    Optional<List<Track>> findTracksByGenreName(String genreName, TrackFilter filter);

    Optional<Set<Track>> findTracksByAlbumId(Long albumId, TrackFilter filter);

    Optional<Set<Track>> findTracksByAlbumName(String albumName, TrackFilter filter);

    Optional<List<Track>> findTracksByArtistId(Long artistId, TrackFilter filter);

    Optional<List<Track>> findTracksByArtistName(String artistName, TrackFilter filter);
}