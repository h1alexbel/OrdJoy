package com.ordjoy.service;

import com.ordjoy.dao.filter.AlbumFilter;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.impl.AlbumDaoImpl;
import com.ordjoy.dto.AlbumDto;
import com.ordjoy.dto.AlbumReviewDto;
import com.ordjoy.entity.Album;
import com.ordjoy.exception.ServiceException;

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

    public Album saveAlbum(Album album) throws ServiceException {
        return albumDao.save(album);
    }

    public Optional<AlbumDto> findAlbumById(Long id) throws ServiceException {
        return albumDao.findById(id).stream()
                .map(album -> new AlbumDto(
                        album.getId(),
                        album.getTitle()
                )).findFirst();
    }

    public List<AlbumDto> findAllAlbums(AlbumFilter filter) throws ServiceException {
        return albumDao.findAll(filter).stream()
                .map(album -> new AlbumDto(
                        album.getId(),
                        album.getTitle()
                )).collect(toList());
    }

    public void updateAlbum(Album album) throws ServiceException {
        albumDao.update(album);
    }

    public boolean deleteAlbumById(Long id) throws ServiceException {
        return albumDao.deleteById(id);
    }

    public Optional<AlbumDto> findAlbumByTitle(String albumTitle) throws ServiceException {
        return albumDao.findAlbumByTitle(albumTitle).stream()
                .map(album -> new AlbumDto(
                        album.getId(),
                        album.getTitle()
                )).findFirst();
    }

    public List<AlbumReviewDto> findAlbumReviewsByAlbumTitle(String albumTitle, DefaultFilter filter) throws ServiceException {
        return albumDao.findAlbumReviewsByAlbumTitle(albumTitle, filter).stream()
                .map(albumReview -> new AlbumReviewDto(
                        albumReview.getId(),
                        albumReview.getReviewText(),
                        albumReview.getAlbum().getTitle(),
                        albumReview.getUserAccount().getLogin()
                )).collect(toList());
    }

    public List<AlbumReviewDto> findAlbumReviewsByAlbumId(Long albumId, DefaultFilter filter) throws ServiceException {
        return albumDao.findAlbumReviewsByAlbumId(albumId, filter).stream()
                .map(albumReview -> new AlbumReviewDto(
                        albumReview.getId(),
                        albumReview.getReviewText(),
                        albumReview.getAlbum().getTitle(),
                        albumReview.getUserAccount().getLogin()
                )).collect(toList());
    }
}