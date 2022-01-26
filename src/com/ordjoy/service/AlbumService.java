package com.ordjoy.service;

import com.ordjoy.dao.filter.AlbumFilter;
import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.impl.AlbumDaoImpl;
import com.ordjoy.dto.AlbumDto;
import com.ordjoy.dto.AlbumReviewDto;
import com.ordjoy.entity.Album;
import com.ordjoy.exception.DaoException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.mapper.AlbumMapper;
import com.ordjoy.mapper.AlbumReviewMapper;

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

    public AlbumDto saveAlbum(String title) throws ServiceException {
        Album album = buildAlbum(title);
        try {
            Album savedAlbum = albumDao.save(album);
            return albumMapper.mapFrom(savedAlbum);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public boolean isAlbumExists(String title) throws ServiceException {
        boolean result = false;
        try {
            Optional<Album> maybeAlbum = albumDao.findAlbumByTitle(title);
            if (maybeAlbum.isPresent()) {
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return result;
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
        try {
            return albumDao.findAlbumReviewsByAlbumTitle(albumTitle, filter).stream()
                    .map(albumReviewMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<AlbumReviewDto> findAlbumReviewsByAlbumId(Long albumId, DefaultFilter filter) throws ServiceException {
        try {
            return albumDao.findAlbumReviewsByAlbumId(albumId, filter).stream()
                    .map(albumReviewMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    private Album buildAlbum(String title) {
        return new Album(title);
    }
}