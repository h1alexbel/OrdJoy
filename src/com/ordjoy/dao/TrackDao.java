package com.ordjoy.dao;

import com.ordjoy.filter.DefaultFilter;
import com.ordjoy.filter.TrackFilter;
import com.ordjoy.entity.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TrackDao extends GenericDao<Long, Track> {

    Track saveTrackInExistingAlbum(Track trackToSave, Album albumThatExists, Artist artistThatExists);

    Mix saveTrackInExistingMix(Track trackToSave, Album trackAlbumToSave, Mix mixThatExists, Artist artistThatExists);

    List<Track> findAll(TrackFilter filter);

    Optional<List<Track>> findTracksByGenreId(Long genreId, DefaultFilter filter);

    Optional<List<Track>> findTracksByGenreName(String genreName, DefaultFilter filter);

    Optional<Set<Track>> findTracksByAlbumId(Long albumId, DefaultFilter filter);

    Optional<Set<Track>> findTracksByAlbumName(String albumName, DefaultFilter filter);

    Optional<List<Track>> findTracksByArtistId(Long artistId, DefaultFilter filter);

    Optional<List<Track>> findTracksByArtistName(String artistName, DefaultFilter filter);
}