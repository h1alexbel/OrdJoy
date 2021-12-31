package com.ordjoy.dao;

import com.ordjoy.filter.AlbumFilter;
import com.ordjoy.entity.Album;
import com.ordjoy.entity.Track;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AlbumDao extends GenericDao<Long, Album> {

    Album saveAlbum(Album album);

    Album saveAlbumWithTracks(Album album, Set<Track> tracks);

    List<Album> findAll(AlbumFilter filter);

    Optional<List<Album>> findAlbumsByGenreName(String genreName, AlbumFilter filter);

    Optional<List<Album>> findAlbumsByGenreId(Long genreId, AlbumFilter filter);

    Optional<List<Album>> findAlbumsByArtistName(String artistName, AlbumFilter filter);
}