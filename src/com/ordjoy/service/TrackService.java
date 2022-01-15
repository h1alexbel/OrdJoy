package com.ordjoy.service;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.TrackFilter;
import com.ordjoy.dao.impl.TrackDaoImpl;
import com.ordjoy.dto.TrackDto;
import com.ordjoy.entity.Mix;
import com.ordjoy.entity.Track;
import com.ordjoy.exception.ServiceException;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

public class TrackService {

    private final TrackDaoImpl trackDao = TrackDaoImpl.getInstance();
    private static final TrackService INSTANCE = new TrackService();

    private TrackService() {

    }

    public static TrackService getInstance() {
        return INSTANCE;
    }

    public Track saveTrack(Track track) throws ServiceException {
        return trackDao.save(track);
    }

    public Optional<TrackDto> findById(Long id) throws ServiceException {
        return trackDao.findById(id).stream()
                .map(track -> new TrackDto(
                        track.getId(),
                        track.getSongUrl(),
                        track.getTitle(),
                        track.getAlbum().getTitle()
                )).findFirst();
    }

    public List<TrackDto> findAllTracksWithLimitOffset(TrackFilter filter) throws ServiceException {
        return trackDao.findAll(filter).stream()
                .map(track -> new TrackDto(
                        track.getId(),
                        track.getSongUrl(),
                        track.getTitle(),
                        track.getAlbum().getTitle()
                )).collect(toList());
    }

    public void updateTrack(Track track) throws ServiceException {
        trackDao.update(track);
    }

    public boolean deleteTrackById(Long id) throws ServiceException {
        return trackDao.deleteById(id);
    }

    public boolean addExistingTrackToMix(Mix mixThatExists, Track trackThatExists) throws ServiceException {
        return trackDao.addExistingTrackToMix(mixThatExists, trackThatExists);
    }

    public Optional<TrackDto> findByTrackTitle(String trackTitle) throws ServiceException {
        return trackDao.findByTrackTitle(trackTitle).stream()
                .map(track -> new TrackDto(
                        track.getId(),
                        track.getSongUrl(),
                        track.getTitle(),
                        track.getAlbum().getTitle()
                )).findFirst();
    }

    public List<TrackDto> findTracksByAlbumId(Long albumId, DefaultFilter filter) throws ServiceException {
        return trackDao.findTracksByAlbumId(albumId, filter).stream()
                .map(track -> new TrackDto(
                        track.getId(),
                        track.getSongUrl(),
                        track.getTitle(),
                        track.getAlbum().getTitle()
                )).collect(toList());
    }

    public List<TrackDto> findTracksByAlbumName(String albumName, DefaultFilter filter) throws ServiceException {
        return trackDao.findTracksByAlbumName(albumName, filter).stream()
                .map(track -> new TrackDto(
                        track.getId(),
                        track.getSongUrl(),
                        track.getTitle(),
                        track.getAlbum().getTitle()
                )).collect(toList());
    }
}