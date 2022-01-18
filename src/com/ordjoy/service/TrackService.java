package com.ordjoy.service;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.TrackFilter;
import com.ordjoy.dao.impl.TrackDaoImpl;
import com.ordjoy.dto.TrackDto;
import com.ordjoy.entity.Mix;
import com.ordjoy.entity.Track;
import com.ordjoy.exception.DaoException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.mapper.TrackMapper;

import java.util.List;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static java.util.stream.Collectors.*;

public class TrackService {

    private final TrackDaoImpl trackDao = TrackDaoImpl.getInstance();
    private static final TrackService INSTANCE = new TrackService();
    private final TrackMapper trackMapper = TrackMapper.getInstance();

    private TrackService() {

    }

    public static TrackService getInstance() {
        return INSTANCE;
    }

    public TrackDto addNewTrack(Track track) throws ServiceException {
        try {
            Track savedTrack = trackDao.save(track);
            return trackMapper.mapFrom(savedTrack);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
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
        try {
            trackDao.update(track);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public boolean deleteTrackById(Long id) throws ServiceException {
        try {
            return trackDao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public boolean addExistingTrackToMix(Mix mixThatExists, Track trackThatExists) throws ServiceException {
        try {
            return trackDao.addExistingTrackToMix(mixThatExists, trackThatExists);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
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
}