package com.ordjoy.service;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.ReviewFilter;
import com.ordjoy.dao.impl.AlbumReviewDaoImpl;
import com.ordjoy.dao.impl.MixReviewDaoImpl;
import com.ordjoy.dao.impl.TrackReviewDaoImpl;
import com.ordjoy.dto.*;
import com.ordjoy.entity.*;
import com.ordjoy.exception.DaoException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.exception.ValidationException;
import com.ordjoy.mapper.AlbumReviewMapper;
import com.ordjoy.mapper.MixReviewMapper;
import com.ordjoy.mapper.TrackReviewMapper;

import java.util.List;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static java.util.stream.Collectors.toList;

public class ReviewService {

    private final TrackReviewDaoImpl trackReviewDao = TrackReviewDaoImpl.getInstance();
    private final AlbumReviewDaoImpl albumReviewDao = AlbumReviewDaoImpl.getInstance();
    private final MixReviewDaoImpl mixReviewDao = MixReviewDaoImpl.getInstance();
    private static final ReviewService INSTANCE = new ReviewService();
    private final MixReviewMapper mixReviewMapper = MixReviewMapper.getInstance();
    private final TrackReviewMapper trackReviewMapper = TrackReviewMapper.getInstance();
    private final AlbumReviewMapper albumReviewMapper = AlbumReviewMapper.getInstance();

    private ReviewService() {

    }

    public static ReviewService getInstance() {
        return INSTANCE;
    }

    public MixReviewDto addMixReview(String reviewText, UserAccount userAccount, Mix mix) throws ServiceException, ValidationException {
        MixReview mixReview = buildMixReview(reviewText, userAccount, mix);
        try {
            MixReview savedMixReview = mixReviewDao.save(mixReview);
            return mixReviewMapper.mapFrom(savedMixReview);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public TrackReviewDto addTrackReview(String reviewText, UserAccount userAccount, Track track) throws ServiceException, ValidationException {
        TrackReview trackReview = buildTrackReview(reviewText, userAccount, track);
        try {
            TrackReview savedTrackReview = trackReviewDao.save(trackReview);
            return trackReviewMapper.mapFrom(savedTrackReview);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public AlbumReviewDto addAlbumReview(String reviewText, UserAccount userAccount, Album album) throws ServiceException, ValidationException {
        AlbumReview albumReview = buildAlbumReview(reviewText, userAccount, album);
        try {
            AlbumReview saveAlbumReview = albumReviewDao.save(albumReview);
            return albumReviewMapper.mapFrom(saveAlbumReview);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public Optional<MixReviewDto> findMixReviewById(Long id) throws ServiceException {
        try {
            Optional<MixReview> maybeMixReview = mixReviewDao.findById(id);
            if (maybeMixReview.isPresent()) {
                MixReview mixReview = maybeMixReview.get();
                return Optional.of(mixReviewMapper.mapFrom(mixReview));
            }
            return Optional.empty();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public Optional<TrackReviewDto> findTrackReviewById(Long id) throws ServiceException {
        try {
            Optional<TrackReview> maybeTrackReview = trackReviewDao.findById(id);
            if (maybeTrackReview.isPresent()) {
                TrackReview trackReview = maybeTrackReview.get();
                return Optional.of(trackReviewMapper.mapFrom(trackReview));
            }
            return Optional.empty();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public Optional<AlbumReviewDto> findAlbumReviewById(Long id) throws ServiceException {
        try {
            Optional<AlbumReview> maybeAlbumReview = albumReviewDao.findById(id);
            if (maybeAlbumReview.isPresent()) {
                AlbumReview albumReview = maybeAlbumReview.get();
                return Optional.of(albumReviewMapper.mapFrom(albumReview));
            }
            return Optional.empty();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<MixReviewDto> findAllMixReviewsWithLimitAndOffset(ReviewFilter filter) throws ServiceException {
        try {
            return mixReviewDao.findAll(filter).stream()
                    .map(mixReviewMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<TrackReviewDto> findAllTrackReviewsWithLimitAndOffset(ReviewFilter filter) throws ServiceException {
        try {
            return trackReviewDao.findAll(filter).stream()
                    .map(trackReviewMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<AlbumReviewDto> findAllAlbumReviewsWithLimitAndOffset(ReviewFilter filter) throws ServiceException {
        try {
            return albumReviewDao.findAll(filter).stream()
                    .map(albumReviewMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public void updateMixReview(MixReview mixReview) throws ServiceException {
        try {
            mixReviewDao.update(mixReview);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public void updateTrackReview(TrackReview trackReview) throws ServiceException {
        try {
            trackReviewDao.update(trackReview);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public void updateAlbumReview(AlbumReview albumReview) throws ServiceException {
        try {
            albumReviewDao.update(albumReview);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public boolean deleteMixReviewById(Long id) throws ServiceException {
        try {
            return mixReviewDao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public boolean deleteTrackReviewById(Long id) throws ServiceException {
        try {
            return trackReviewDao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public boolean deleteAlbumReviewById(Long id) throws ServiceException {
        try {
            return albumReviewDao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<MixReviewDto> findMixReviewsByUserLogin(String login, DefaultFilter filter) throws ServiceException {
        try {
            return mixReviewDao.findMixReviewsByUserLogin(login, filter).stream()
                    .map(mixReviewMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<TrackReviewDto> findTrackReviewsByUserLogin(String login, DefaultFilter filter) throws ServiceException {
        try {
            return trackReviewDao.findTrackReviewsByUserLogin(login, filter).stream()
                    .map(trackReviewMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<AlbumReviewDto> findAlbumReviewsByUserLogin(String login, DefaultFilter filter) throws ServiceException {
        try {
            return albumReviewDao.findAlbumReviewsByUserLogin(login, filter).stream()
                    .map(albumReviewMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<MixReviewDto> findMixReviewsByUserId(Long id, DefaultFilter filter) throws ServiceException {
        try {
            return mixReviewDao.findMixReviewsByUserId(id, filter).stream()
                    .map(mixReviewMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<TrackReviewDto> findTrackReviewsByUserId(Long id, DefaultFilter filter) throws ServiceException {
        try {
            return trackReviewDao.findTrackReviewsByUserId(id, filter).stream()
                    .map(trackReviewMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<AlbumReviewDto> findAlbumReviewsByUserId(Long id, DefaultFilter filter) throws ServiceException {
        try {
            return albumReviewDao.findAlbumReviewsByUserId(id, filter).stream()
                    .map(albumReviewMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<TrackReviewDto> findTrackReviewsByTrackId(Long trackId, DefaultFilter filter) throws ServiceException {
        try {
            return trackReviewDao.findTrackReviewsByTrackId(trackId, filter).stream()
                    .map(trackReviewMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<TrackReviewDto> findTrackReviewsByTrackTitle(String trackTitle, DefaultFilter filter) throws ServiceException {
        try {
            return trackReviewDao.findTrackReviewsByTrackTitle(trackTitle, filter).stream()
                    .map(trackReviewMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    private MixReview buildMixReview(String reviewText, UserAccount userAccount, Mix mix) {
        return new MixReview(
                reviewText, userAccount, mix
        );
    }

    private TrackReview buildTrackReview(String reviewText, UserAccount userAccount, Track track) {
        return new TrackReview(
                reviewText, userAccount, track
        );
    }

    private AlbumReview buildAlbumReview(String reviewText, UserAccount userAccount, Album album) {
        return new AlbumReview(
                reviewText, userAccount, album
        );
    }
}