package com.ordjoy.service;

import com.ordjoy.dao.filter.AlbumFilter;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.impl.AlbumDaoImpl;
import com.ordjoy.dto.AlbumDto;
import com.ordjoy.dto.AlbumReviewDto;
import com.ordjoy.entity.Album;
import com.ordjoy.entity.AlbumReview;
import com.ordjoy.exception.DaoException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.mapper.AlbumMapper;
import com.ordjoy.mapper.AlbumReviewMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static java.util.stream.Collectors.*;

public class AlbumService {

    private final AlbumDaoImpl albumDao = AlbumDaoImpl.getInstance();
    private static final AlbumService INSTANCE = new AlbumService();
    private final AlbumMapper albumMapper = AlbumMapper.getInstance();
    private final AlbumReviewMapper albumReviewMapper = AlbumReviewMapper.getInstance();

    private AlbumService() {

    }

    public static AlbumService getInstance() {
        return INSTANCE;
    }

    public AlbumDto saveAlbum(Album album) throws ServiceException {
        try {
            Album savedAlbum = albumDao.save(album);
            AlbumDto albumDto = albumMapper.mapFrom(savedAlbum);
            return albumDto;
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public Optional<AlbumDto> findAlbumById(Long id) throws ServiceException {
        Optional<AlbumDto> maybeAlbum;
        try {
            maybeAlbum = albumDao.findById(id).stream()
                    .map(album -> new AlbumDto(
                            album.getId(),
                            album.getTitle()
                    )).findFirst();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return maybeAlbum;
    }

    public List<AlbumDto> findAllAlbums(AlbumFilter filter) throws ServiceException {
        List<AlbumDto> albums;
        try {
            albums = albumDao.findAll(filter).stream()
                    .map(album -> new AlbumDto(
                            album.getId(),
                            album.getTitle()
                    )).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return albums;
    }

    public void updateAlbum(Album album) throws ServiceException {
        try {
            albumDao.update(album);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public boolean deleteAlbumById(Long id) throws ServiceException {
        try {
            return albumDao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public Optional<AlbumDto> findAlbumByTitle(String albumTitle) throws ServiceException {
        Optional<AlbumDto> maybeAlbum;
        try {
            maybeAlbum = albumDao.findAlbumByTitle(albumTitle).stream()
                    .map(album -> new AlbumDto(
                            album.getId(),
                            album.getTitle()
                    )).findFirst();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return maybeAlbum;
    }

    public List<AlbumReviewDto> findAlbumReviewsByAlbumTitle(String albumTitle, DefaultFilter filter) throws ServiceException {
        List<AlbumReviewDto> albumReviewDtos = new ArrayList<>();
        try {
            List<AlbumReview> albumReviews = albumDao.findAlbumReviewsByAlbumTitle(albumTitle, filter);
            albumReviews.forEach(albumReview -> {
                AlbumReviewDto albumReviewDto = albumReviewMapper.mapFrom(albumReview);
                albumReviewDtos.add(albumReviewDto);
            });
            return albumReviewDtos;
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<AlbumReviewDto> findAlbumReviewsByAlbumId(Long albumId, DefaultFilter filter) throws ServiceException {
        List<AlbumReviewDto> albumReviewDtos = new ArrayList<>();
        try {
            List<AlbumReview> albumReviews = albumDao.findAlbumReviewsByAlbumId(albumId, filter);
            albumReviews.forEach(albumReview -> {
                AlbumReviewDto albumReviewDto = albumReviewMapper.mapFrom(albumReview);
                albumReviewDtos.add(albumReviewDto);
            });
            return albumReviewDtos;
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }
}