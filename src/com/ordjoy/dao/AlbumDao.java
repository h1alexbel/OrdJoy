package com.ordjoy.dao;

import com.ordjoy.filter.AlbumFilter;
import com.ordjoy.entity.Album;
import com.ordjoy.filter.DefaultFilter;

import java.util.List;
import java.util.Optional;

public interface AlbumDao extends GenericDao<Long, Album> {

    Album uploadNewAlbumWithData(Album album);

    List<Album> findAll(AlbumFilter filter);

    Optional<List<Album>> findAlbumsByGenreName(String genreName, DefaultFilter filter);

    Optional<List<Album>> findAlbumsByGenreId(Long genreId, DefaultFilter filter);

    Optional<List<Album>> findAlbumsByArtistName(String artistName, DefaultFilter filter);
}