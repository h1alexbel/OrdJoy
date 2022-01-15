package com.ordjoy.service;

import com.ordjoy.dao.filter.AlbumFilter;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.impl.AlbumDaoImpl;
import com.ordjoy.dto.AlbumDto;
import com.ordjoy.dto.AlbumReviewDto;
import com.ordjoy.entity.Album;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

public class AlbumService {

    private final AlbumDaoImpl albumDao = AlbumDaoImpl.getInstance();

    private static final AlbumService INSTANCE = new AlbumService();

    private AlbumService() {

    }

    public static AlbumService getInstance() {
        return INSTANCE;
    }

    public Album saveAlbum(Album album) {
        return albumDao.save(album);
    }

    public Optional<AlbumDto> findAlbumById(Long id) {
        return albumDao.findById(id).stream()
                .map(album -> new AlbumDto(
                        album.getId(),
                        album.getTitle()
                )).findFirst();
    }

    public List<AlbumDto> findAllAlbums(AlbumFilter filter) {
        return albumDao.findAll(filter).stream()
                .map(album -> new AlbumDto(
                        album.getId(),
                        album.getTitle()
                )).collect(toList());
    }

    public void updateAlbum(Album album) {
        albumDao.update(album);
    }

    public boolean deleteAlbumById(Long id) {
        return albumDao.deleteById(id);
    }

    public Optional<AlbumDto> findAlbumByTitle(String albumTitle) {
        return albumDao.findAlbumByTitle(albumTitle).stream()
                .map(album -> new AlbumDto(
                        album.getId(),
                        album.getTitle()
                )).findFirst();
    }

    public List<AlbumReviewDto> findAlbumReviewsByAlbumTitle(String albumTitle, DefaultFilter filter) {
        return albumDao.findAlbumReviewsByAlbumTitle(albumTitle, filter).stream()
                .map(albumReview -> new AlbumReviewDto(
                        albumReview.getId(),
                        albumReview.getReviewText(),
                        albumReview.getAlbum().getTitle(),
                        albumReview.getUserAccount().getLogin()
                )).collect(toList());
    }

    public List<AlbumReviewDto> findAlbumReviewsByAlbumId(Long albumId, DefaultFilter filter) {
        return albumDao.findAlbumReviewsByAlbumId(albumId, filter).stream()
                .map(albumReview -> new AlbumReviewDto(
                        albumReview.getId(),
                        albumReview.getReviewText(),
                        albumReview.getAlbum().getTitle(),
                        albumReview.getUserAccount().getLogin()
                )).collect(toList());
    }
}