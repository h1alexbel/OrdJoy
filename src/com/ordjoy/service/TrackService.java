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
import com.ordjoy.exception.ValidationException;
import com.ordjoy.mapper.TrackMapper;
import com.ordjoy.validation.ValidationResult;
import com.ordjoy.validation.impl.AlbumValidator;
import com.ordjoy.validation.impl.MixValidator;
import com.ordjoy.validation.impl.TrackValidator;

import java.util.List;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static java.util.stream.Collectors.*;

public class TrackService {

    private final TrackDaoImpl trackDao = TrackDaoImpl.getInstance();
    private static final TrackService INSTANCE = new TrackService();
    private final TrackMapper trackMapper = TrackMapper.getInstance();
    private final TrackValidator trackValidator = TrackValidator.getInstance();
    private final MixValidator mixValidator = MixValidator.getInstance();

    private TrackService() {

    }

    public static TrackService getInstance() {
        return INSTANCE;
    }

    public TrackDto addNewTrack(String songUrl, String title, Album album) throws ServiceException, ValidationException {
        Track track = buildTrack(songUrl, title, album);
        ValidationResult validationResult = trackValidator.isValid(track);
        if (validationResult.isValid()) {
            try {
                Track savedTrack = trackDao.save(track);
                return trackMapper.mapFrom(savedTrack);
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        throw new ValidationException(VALIDATION_EXCEPTION_MESSAGE);
    }

    public boolean isTrackExists(String title) throws ServiceException {
        boolean result = false;
        try {
            Optional<Track> maybeTrack = trackDao.findByTrackTitle(title);
            if (maybeTrack.isPresent()) {
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    public Optional<TrackDto> findTrackById(Long id) throws ServiceException {
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

    public List<TrackDto> findAllTracksWithLimitOffset(TrackFilter filter) throws ServiceException {
        try {
            return trackDao.findAll(filter).stream()
                    .map(trackMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public void updateTrack(Track track) throws ServiceException {
        ValidationResult validationResult = trackValidator.isValid(track);
        if (validationResult.isValid()) {
            try {
                trackDao.update(track);
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
    }

    public boolean deleteTrackById(Long id) throws ServiceException {
        try {
            return trackDao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public boolean addExistingTrackToMix(Mix mixThatExists, Track trackThatExists) throws ServiceException, ValidationException {
        ValidationResult trackValidationResult = trackValidator.isValid(trackThatExists);
        ValidationResult mixValidationResult = mixValidator.isValid(mixThatExists);
        if (trackValidationResult.isValid() && mixValidationResult.isValid()) {
            try {
                return trackDao.addExistingTrackToMix(mixThatExists, trackThatExists);
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        throw new ValidationException(VALIDATION_EXCEPTION_MESSAGE);
    }

    public Optional<TrackDto> findByTrackTitle(String trackTitle) throws ServiceException {
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

    public List<TrackDto> findTracksByAlbumId(Long albumId, DefaultFilter filter) throws ServiceException {
        try {
            return trackDao.findTracksByAlbumId(albumId, filter).stream()
                    .map(trackMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<TrackDto> findTracksByAlbumName(String albumName, DefaultFilter filter) throws ServiceException {
        try {
            return trackDao.findTracksByAlbumName(albumName, filter).stream()
                    .map(trackMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    private Track buildTrack(String songUrl, String title, Album album) {
        return new Track(
                songUrl, title, album
        );
    }
}