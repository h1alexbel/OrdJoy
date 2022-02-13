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
import com.ordjoy.util.LogginUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static java.util.stream.Collectors.*;

public class AlbumService {

    private static final Logger LOGGER = LogManager.getLogger(AlbumService.class);
    private final AlbumDaoImpl albumDao = AlbumDaoImpl.getInstance();
    private final AlbumMapper albumMapper = AlbumMapper.getInstance();
    private final AlbumReviewMapper albumReviewMapper = AlbumReviewMapper.getInstance();
    private static final AlbumService INSTANCE = new AlbumService();

    private AlbumService() {

    }

    /**
     * @return instance of {@link AlbumService}
     */
    public static AlbumService getInstance() {
        return INSTANCE;
    }

    /**
     * Save {@link Album}
     *
     * @param album {@link Album} entity that be saved in database
     * @return {@link AlbumDto} that represents {@link Album} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public AlbumDto saveAlbum(Album album) throws ServiceException {
        try {
            Album savedAlbum = albumDao.save(album);
            return albumMapper.mapFrom(savedAlbum);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Check is {@link Album} exists in database or not
     *
     * @param title {@link Album} name
     * @return boolean value {@code true} if exists {@code false} if not
     * @throws ServiceException if Dao layer can not execute method
     */
    public boolean isAlbumExists(String title) throws ServiceException {
        boolean result = false;
        if (title != null) {
            try {
                Optional<Album> maybeAlbum = albumDao.findAlbumByTitle(title);
                if (maybeAlbum.isPresent()) {
                    result = true;
                }
            } catch (DaoException e) {
                LOGGER.debug(LogginUtils.ALBUM_EXISTS, e);
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return result;
    }

    /**
     * Find {@link AlbumDto} from database by {@link Album} id
     *
     * @param id {@link Album} id
     * @return {@link Optional} of {@link AlbumDto} if present or {@link Optional} empty
     * @throws ServiceException if Dao layer can not execute method
     */
    public Optional<AlbumDto> findAlbumById(Long id) throws ServiceException {
        Optional<AlbumDto> maybeAlbum = Optional.empty();
        if (id != null) {
            try {
                maybeAlbum = albumDao.findById(id).stream()
                        .map(album -> new AlbumDto(
                                album.getId(),
                                album.getTitle()
                        )).findFirst();
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return maybeAlbum;
    }

    /**
     * Finds all {@link AlbumDto}
     *
     * @param filter sets limit/offset
     * @return List of {@link AlbumDto} that presents {@link Album} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<AlbumDto> findAllAlbums(AlbumFilter filter) throws ServiceException {
        if (filter != null) {
            try {
                return albumDao.findAll(filter).stream()
                        .map(album -> new AlbumDto(
                                album.getId(),
                                album.getTitle()
                        )).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Gets all records from table in database
     *
     * @return Long value of table records
     * @throws ServiceException if Dao layer can not execute method
     */
    public Long getRecords() throws ServiceException {
        try {
            return albumDao.getTableRecords();
        } catch (DaoException e) {
            LOGGER.error(LogginUtils.FETCH_RECORDS_ERROR, e);
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Update {@link Album} in database
     *
     * @param album new value of {@link Album}
     * @throws ServiceException if Dao layer can not execute method
     */
    public void updateAlbum(Album album) throws ServiceException {
        if (album != null) {
            try {
                albumDao.update(album);
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
    }

    /**
     * Transactional Delete {@link Album} from database
     *
     * @param id {@link Album} id from database
     * @return boolean value {@code true} if {@link Album} successfully deleted {@code false} if it failed
     * @throws ServiceException if Dao layer can not execute method
     */
    public boolean deleteAlbumById(Long id) throws ServiceException {
        boolean result = false;
        if (id != null) {
            try {
                result = albumDao.deleteById(id);
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return result;
    }

    /**
     * Find all {@link AlbumDto} by {@link Album} title
     *
     * @param albumTitle {@link Album} name
     * @return {@link Optional} of {@link AlbumDto} if present or {@link Optional} empty
     * @throws ServiceException if Dao layer can not execute method
     */
    public Optional<AlbumDto> findAlbumByTitle(String albumTitle) throws ServiceException {
        Optional<AlbumDto> maybeAlbum = Optional.empty();
        if (albumTitle != null) {
            try {
                maybeAlbum = albumDao.findAlbumByTitle(albumTitle).stream()
                        .map(album -> new AlbumDto(
                                album.getId(),
                                album.getTitle()
                        )).findFirst();
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return maybeAlbum;
    }

    /**
     * Finds all {@link AlbumReviewDto} by {@link Album} name
     *
     * @param albumTitle {@link Album} name
     * @param filter     sets limit/offset
     * @return List {@link AlbumReviewDto} that represents {@link com.ordjoy.entity.AlbumReview} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<AlbumReviewDto> findAlbumReviewsByAlbumTitle(String albumTitle, DefaultFilter filter)
            throws ServiceException {
        if (albumTitle != null && filter != null) {
            try {
                return albumDao.findAlbumReviewsByAlbumTitle(albumTitle, filter).stream()
                        .map(albumReviewMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Finds all {@link AlbumReviewDto} by {@link Album} id
     *
     * @param albumId {@link Album} id
     * @param filter  sets limit/offset
     * @return List {@link AlbumReviewDto} that represents {@link com.ordjoy.entity.AlbumReview} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<AlbumReviewDto> findAlbumReviewsByAlbumId(Long albumId, DefaultFilter filter)
            throws ServiceException {
        if (albumId != null && filter != null) {
            try {
                return albumDao.findAlbumReviewsByAlbumId(albumId, filter).stream()
                        .map(albumReviewMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Make {@link Album} from data
     *
     * @param title {@link Album} title
     * @return ready to use {@link Album} entity
     */
    public Album buildAlbum(String title) {
        return new Album(title);
    }
}