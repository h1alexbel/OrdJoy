package com.ordjoy.service;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.TrackFilter;
import com.ordjoy.dao.impl.TrackDaoImpl;
import com.ordjoy.dto.TrackDto;
import com.ordjoy.entity.Album;
import com.ordjoy.entity.Mix;
import com.ordjoy.entity.Track;
import com.ordjoy.exception.DaoException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.mapper.TrackMapper;
import com.ordjoy.util.LogginUtils;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.impl.TrackValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static java.util.stream.Collectors.*;

public class TrackService {

    private static final Logger LOGGER = LogManager.getLogger(TrackService.class);
    private final TrackDaoImpl trackDao = TrackDaoImpl.getInstance();
    private static final TrackService INSTANCE = new TrackService();
    private final TrackMapper trackMapper = TrackMapper.getInstance();
    private final TrackValidator trackValidator = TrackValidator.getInstance();

    private TrackService() {

    }

    /**
     * @return instance of {@link TrackService}
     */
    public static TrackService getInstance() {
        return INSTANCE;
    }

    /**
     * Save {@link Track} in database
     *
     * @param track Track that be saved in database
     * @return {@link TrackDto} that represents {@link Track} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public TrackDto addNewTrack(Track track) throws ServiceException {
        try {
            Track savedTrack = trackDao.save(track);
            return trackMapper.mapFrom(savedTrack);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Check is Track with relevant title exists or not
     *
     * @param title {@link Track} title
     * @return boolean value {@code true} if exist {@code false} if track not exist in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public boolean isTrackExists(String title) throws ServiceException {
        boolean result = false;
        if (title != null) {
            try {
                Optional<Track> maybeTrack = trackDao.findByTrackTitle(title);
                if (maybeTrack.isPresent()) {
                    result = true;
                }
            } catch (DaoException e) {
                LOGGER.debug(LogginUtils.TRACK_EXISTS, e);
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return result;
    }

    /**
     * Find Track from database by it's id
     *
     * @param id {@link Track} id from database
     * @return {@link TrackDto} that represents Track in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public Optional<TrackDto> findTrackById(Long id) throws ServiceException {
        if (id != null) {
            try {
                Optional<Track> maybeTrack = trackDao.findById(id);
                if (maybeTrack.isPresent()) {
                    Track track = maybeTrack.get();
                    TrackDto trackDto = trackMapper.mapFrom(track);
                    return Optional.of(trackDto);
                }
                return Optional.empty();
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Optional.empty();
    }

    /**
     * Finds all {@link TrackDto}
     *
     * @param filter that sets limit and offset, (albumTitle)
     * @return List of {@link TrackDto} from database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<TrackDto> findAllTracksWithLimitOffset(TrackFilter filter) throws ServiceException {
        if (filter != null) {
            try {
                return trackDao.findAll(filter).stream()
                        .map(trackMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Gets table records from database
     *
     * @return Long value that represents table records in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public Long getRecords() throws ServiceException {
        try {
            return trackDao.getTableRecords();
        } catch (DaoException e) {
            LOGGER.error(LogginUtils.FETCH_RECORDS_ERROR, e);
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Updates {@link Track} in database
     *
     * @param track new value of {@link Track}
     * @throws ServiceException if Dao layer can not execute method
     */
    public void updateTrack(Track track) throws ServiceException {
        if (track != null) {
            ValidationResult validationResult = trackValidator.isValid(track);
            if (validationResult.isValid()) {
                try {
                    trackDao.update(track);
                } catch (DaoException e) {
                    throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
                }
            }
        }
    }

    /**
     * Transactional Delete {@link Track} from database
     *
     * @param id {@link Track} id from database
     * @return boolean value {@code true} if {@link Track} successfully deleted {@code false} if it failed
     * @throws ServiceException if Dao layer can not execute method
     */
    public boolean deleteTrackById(Long id) throws ServiceException {
        boolean result = false;
        if (id != null) {
            try {
                result = trackDao.deleteById(id);
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return result;
    }

    /**
     * Link {@link Track} with {@link Mix} in mutual table in database
     *
     * @param mixThatExists   {@link Mix} in database
     * @param trackThatExists {@link Track} in database
     * @return boolean value {@code true} if {@link Track} successfully linked with {@link Mix}
     * {@code false} if it failed
     * @throws ServiceException if Dao layer can not execute method
     */
    public boolean addExistingTrackToMix(Mix mixThatExists, Track trackThatExists) throws ServiceException {
        boolean result = false;
        if (mixThatExists != null && trackThatExists != null) {
            try {
                result = trackDao.addExistingTrackToMix(mixThatExists, trackThatExists);
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return result;
    }

    /**
     * Find {@link Track} from database by title
     *
     * @param trackTitle {@link Track} title from database
     * @return {@link Optional} of {@link TrackDto} if presents or {@link Optional} empty
     * @throws ServiceException if Dao layer can not execute method
     */
    public Optional<TrackDto> findByTrackTitle(String trackTitle) throws ServiceException {
        if (trackTitle != null) {
            try {
                Optional<Track> maybeTrack = trackDao.findByTrackTitle(trackTitle);
                if (maybeTrack.isPresent()) {
                    Track track = maybeTrack.get();
                    TrackDto trackDto = trackMapper.mapFrom(track);
                    return Optional.of(trackDto);
                }
                return Optional.empty();
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Optional.empty();
    }

    /**
     * Find Tracks from database by {@link Album} id
     *
     * @param albumId {@link Album} id from database
     * @param filter  sets limit offset
     * @return List of {@link TrackDto} that represents Track in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<TrackDto> findTracksByAlbumId(Long albumId, DefaultFilter filter) throws ServiceException {
        if (albumId != null && filter != null) {
            try {
                return trackDao.findTracksByAlbumId(albumId, filter).stream()
                        .map(trackMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Find Tracks from database by {@link Album} name
     *
     * @param albumName {@link Album} name from database
     * @param filter    sets limit offset
     * @return List of {@link TrackDto} that represents Track in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<TrackDto> findTracksByAlbumName(String albumName, DefaultFilter filter) throws ServiceException {
        if (albumName != null && filter != null) {
            try {
                return trackDao.findTracksByAlbumName(albumName, filter).stream()
                        .map(trackMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Make {@link Track} from data
     *
     * @param songUrl    songUrl
     * @param title      title
     * @param albumTitle albumTitle
     * @return {@link Track} that ready to use in database manipulations
     */
    public Track buildTrack(String songUrl, String title, String albumTitle) {
        Album album = new Album(albumTitle);
        return Track.builder()
                .songUrl(songUrl)
                .title(title)
                .album(album)
                .build();
    }
}