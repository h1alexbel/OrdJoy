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
import com.ordjoy.mapper.AlbumReviewMapper;
import com.ordjoy.mapper.MixReviewMapper;
import com.ordjoy.mapper.TrackReviewMapper;
import com.ordjoy.util.LogginUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static java.util.stream.Collectors.toList;

public class ReviewService {

    private static final Logger LOGGER = LogManager.getLogger(ReviewService.class);
    private final TrackReviewDaoImpl trackReviewDao = TrackReviewDaoImpl.getInstance();
    private final AlbumReviewDaoImpl albumReviewDao = AlbumReviewDaoImpl.getInstance();
    private final MixReviewDaoImpl mixReviewDao = MixReviewDaoImpl.getInstance();
    private static final ReviewService INSTANCE = new ReviewService();
    private final MixReviewMapper mixReviewMapper = MixReviewMapper.getInstance();
    private final TrackReviewMapper trackReviewMapper = TrackReviewMapper.getInstance();
    private final AlbumReviewMapper albumReviewMapper = AlbumReviewMapper.getInstance();

    private ReviewService() {

    }

    /**
     * @return instance of {@link ReviewService}
     */
    public static ReviewService getInstance() {
        return INSTANCE;
    }

    /**
     * Save {@link MixReview} in database
     *
     * @param mixReview {@link MixReview} entity that be saved
     * @return {@link MixReviewDto} that represents {@link MixReview}
     * @throws ServiceException if Dao layer can not execute method
     */
    public MixReviewDto addMixReview(MixReview mixReview) throws ServiceException {
        try {
            MixReview savedMixReview = mixReviewDao.save(mixReview);
            return mixReviewMapper.mapFrom(savedMixReview);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Save {@link TrackReview} in database
     *
     * @param trackReview {@link TrackReview} entity that be saved
     * @return {@link TrackReviewDto} that represents {@link TrackReview}
     * @throws ServiceException if Dao layer can not execute method
     */
    public TrackReviewDto addTrackReview(TrackReview trackReview) throws ServiceException {
        try {
            TrackReview savedTrackReview = trackReviewDao.save(trackReview);
            return trackReviewMapper.mapFrom(savedTrackReview);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Save {@link AlbumReview} in database
     *
     * @param albumReview {@link AlbumReview} entity that be saved
     * @return {@link AlbumReviewDto} that represents {@link AlbumReview}
     * @throws ServiceException if Dao layer can not execute method
     */
    public AlbumReviewDto addAlbumReview(AlbumReview albumReview) throws ServiceException {
        try {
            AlbumReview saveAlbumReview = albumReviewDao.save(albumReview);
            return albumReviewMapper.mapFrom(saveAlbumReview);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Find {@link MixReviewDto} from database by {@link MixReview} id
     *
     * @param id {@link MixReview}
     * @return {@link Optional} of {@link MixReview} if present or {@link Optional} empty
     * @throws ServiceException if Dao layer can not execute method
     */
    public Optional<MixReviewDto> findMixReviewById(Long id) throws ServiceException {
        if (id != null) {
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
        return Optional.empty();
    }

    /**
     * Find {@link TrackReviewDto} from database by {@link TrackReview} id
     *
     * @param id {@link TrackReview}
     * @return {@link Optional} of {@link TrackReview} if present or {@link Optional} empty
     * @throws ServiceException if Dao layer can not execute method
     */
    public Optional<TrackReviewDto> findTrackReviewById(Long id) throws ServiceException {
        if (id != null) {
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
        return Optional.empty();
    }

    /**
     * Find {@link AlbumReviewDto} from database by {@link AlbumReview} id
     *
     * @param id {@link AlbumReview}
     * @return {@link Optional} of {@link AlbumReview} if present or {@link Optional} empty
     * @throws ServiceException if Dao layer can not execute method
     */
    public Optional<AlbumReviewDto> findAlbumReviewById(Long id) throws ServiceException {
        if (id != null) {
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
        return Optional.empty();
    }

    /**
     * Finds all {@link MixReviewDto}
     *
     * @param filter sets limit/offset
     * @return List of {@link MixReviewDto} if present or {@link Optional} empty
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<MixReviewDto> findAllMixReviewsWithLimitAndOffset(ReviewFilter filter) throws ServiceException {
        if (filter != null) {
            try {
                return mixReviewDao.findAll(filter).stream()
                        .map(mixReviewMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Finds all {@link TrackReviewDto}
     *
     * @param filter sets limit/offset
     * @return List of {@link TrackReview} if present or {@link Optional} empty
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<TrackReviewDto> findAllTrackReviewsWithLimitAndOffset(ReviewFilter filter) throws ServiceException {
        if (filter != null) {
            try {
                return trackReviewDao.findAll(filter).stream()
                        .map(trackReviewMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Finds all {@link AlbumReviewDto}
     *
     * @param filter sets limit/offset
     * @return List of {@link AlbumReview} if present or {@link Optional} empty
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<AlbumReviewDto> findAllAlbumReviewsWithLimitAndOffset(ReviewFilter filter) throws ServiceException {
        if (filter != null) {
            try {
                return albumReviewDao.findAll(filter).stream()
                        .map(albumReviewMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Gets all {@link AlbumReview} table records from database
     *
     * @return Long value that represents all table records from database
     * @throws ServiceException if Dao layer can not execute method
     */
    public Long getAlbumReviewRecords() throws ServiceException {
        try {
            return albumReviewDao.getTableRecords();
        } catch (DaoException e) {
            LOGGER.error(LogginUtils.FETCH_RECORDS_ERROR, e);
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Gets all {@link MixReview} table records from database
     *
     * @return Long value that represents all table records from database
     * @throws ServiceException if Dao layer can not execute method
     */
    public Long getMixReviewRecords() throws ServiceException {
        try {
            return mixReviewDao.getTableRecords();
        } catch (DaoException e) {
            LOGGER.error(LogginUtils.FETCH_RECORDS_ERROR, e);
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Gets all {@link TrackReview} table records from database
     *
     * @return Long value that represents all table records from database
     * @throws ServiceException if Dao layer can not execute method
     */
    public Long getTrackReviewRecords() throws ServiceException {
        try {
            return trackReviewDao.getTableRecords();
        } catch (DaoException e) {
            LOGGER.error(LogginUtils.FETCH_RECORDS_ERROR, e);
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Update {@link MixReview} from database
     *
     * @param mixReview new value of {@link  MixReview}
     * @throws ServiceException if Dao layer can not execute method
     */
    public void updateMixReview(MixReview mixReview) throws ServiceException {
        if (mixReview != null) {
            try {
                mixReviewDao.update(mixReview);
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
    }

    /**
     * Update {@link TrackReview} from database
     *
     * @param trackReview new value of {@link  TrackReview}
     * @throws ServiceException if Dao layer can not execute method
     */
    public void updateTrackReview(TrackReview trackReview) throws ServiceException {
        if (trackReview != null) {
            try {
                trackReviewDao.update(trackReview);
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
    }

    /**
     * Update {@link AlbumReview} from database
     *
     * @param albumReview new value of {@link  AlbumReview}
     * @throws ServiceException if Dao layer can not execute method
     */
    public void updateAlbumReview(AlbumReview albumReview) throws ServiceException {
        if (albumReview != null) {
            try {
                albumReviewDao.update(albumReview);
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
    }

    /**
     * Delete {@link MixReview} from database
     *
     * @param id {@link MixReview} id in database
     * @return boolean value {@code true} if successfully deleted or {@code false} if it failed
     * @throws ServiceException if Dao layer can not execute method
     */
    public boolean deleteMixReviewById(Long id) throws ServiceException {
        boolean result = false;
        if (id != null) {
            try {
                result = mixReviewDao.deleteById(id);
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return result;
    }

    /**
     * Delete {@link TrackReview} from database
     *
     * @param id {@link TrackReview} id in database
     * @return boolean value {@code true} if successfully deleted or {@code false} if it failed
     * @throws ServiceException if Dao layer can not execute method
     */
    public boolean deleteTrackReviewById(Long id) throws ServiceException {
        boolean result = false;
        if (id != null) {
            try {
                result = trackReviewDao.deleteById(id);
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return result;
    }

    /**
     * Delete {@link AlbumReview} from database
     *
     * @param id {@link Album} id in database
     * @return boolean value {@code true} if successfully deleted or {@code false} if it failed
     * @throws ServiceException if Dao layer can not execute method
     */
    public boolean deleteAlbumReviewById(Long id) throws ServiceException {
        boolean result = false;
        if (id != null) {
            try {
                result = albumReviewDao.deleteById(id);
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return result;
    }

    /**
     * Finds all {@link MixReviewDto}
     *
     * @param login  {@link UserAccount} login in database
     * @param filter sets limit/offset
     * @return List of {@link MixReviewDto} that represents {@link MixReview} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<MixReviewDto> findMixReviewsByUserLogin(String login, DefaultFilter filter) throws ServiceException {
        if (login != null && filter != null) {
            try {
                return mixReviewDao.findMixReviewsByUserLogin(login, filter).stream()
                        .map(mixReviewMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Finds all {@link TrackReviewDto}
     *
     * @param login  {@link UserAccount} login in database
     * @param filter sets limit/offset
     * @return List of {@link TrackReviewDto} that represents {@link TrackReview} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<TrackReviewDto> findTrackReviewsByUserLogin(String login, DefaultFilter filter)
            throws ServiceException {
        if (login != null && filter != null) {
            try {
                return trackReviewDao.findTrackReviewsByUserLogin(login, filter).stream()
                        .map(trackReviewMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Finds all {@link AlbumReviewDto}
     *
     * @param login  {@link UserAccount} login in database
     * @param filter sets limit/offset
     * @return List of {@link AlbumReviewDto} that represents {@link AlbumReview} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<AlbumReviewDto> findAlbumReviewsByUserLogin(String login, DefaultFilter filter)
            throws ServiceException {
        if (login != null && filter != null) {
            try {
                return albumReviewDao.findAlbumReviewsByUserLogin(login, filter).stream()
                        .map(albumReviewMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Finds all {@link MixReviewDto}
     *
     * @param id     {@link UserAccount} id in database
     * @param filter sets limit/offset
     * @return List of {@link MixReviewDto} that represents {@link MixReview} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<MixReviewDto> findMixReviewsByUserId(Long id, DefaultFilter filter) throws ServiceException {
        if (id != null && filter != null) {
            try {
                return mixReviewDao.findMixReviewsByUserId(id, filter).stream()
                        .map(mixReviewMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Finds all {@link TrackReviewDto}
     *
     * @param id     {@link UserAccount} id in database
     * @param filter sets limit/offset
     * @return List of {@link TrackReviewDto} that represents {@link TrackReview} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<TrackReviewDto> findTrackReviewsByUserId(Long id, DefaultFilter filter) throws ServiceException {
        if (id != null && filter != null) {
            try {
                return trackReviewDao.findTrackReviewsByUserId(id, filter).stream()
                        .map(trackReviewMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Finds all {@link AlbumReviewDto}
     *
     * @param id     {@link UserAccount} id in database
     * @param filter sets limit/offset
     * @return List of {@link AlbumReviewDto} that represents {@link AlbumReview} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<AlbumReviewDto> findAlbumReviewsByUserId(Long id, DefaultFilter filter) throws ServiceException {
        if (id != null && filter != null) {
            try {
                return albumReviewDao.findAlbumReviewsByUserId(id, filter).stream()
                        .map(albumReviewMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Finds all {@link TrackReviewDto}
     *
     * @param trackId {@link Track} id in database
     * @param filter  sets limit/offset
     * @return List of {@link TrackReviewDto} that represents {@link TrackReview} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<TrackReviewDto> findTrackReviewsByTrackId(Long trackId, DefaultFilter filter)
            throws ServiceException {
        if (trackId != null && filter != null) {
            try {
                return trackReviewDao.findTrackReviewsByTrackId(trackId, filter).stream()
                        .map(trackReviewMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Finds all {@link TrackReviewDto}
     *
     * @param trackTitle {@link Track} title in database
     * @param filter     sets limit/offset
     * @return List of {@link TrackReviewDto} that represents {@link TrackReview} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<TrackReviewDto> findTrackReviewsByTrackTitle(String trackTitle, DefaultFilter filter)
            throws ServiceException {
        if (trackTitle != null && filter != null) {
            try {
                return trackReviewDao.findTrackReviewsByTrackTitle(trackTitle, filter).stream()
                        .map(trackReviewMapper::mapFrom).collect(toList());
            } catch (DaoException e) {
                throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Make {@link MixReview} from data
     *
     * @param reviewText  {@link MixReview} reviewText
     * @param userAccount {@link MixReview} userAccount
     * @param mix         {@link MixReview} mix
     * @return {@link MixReview} ready to use in database manipulations
     */
    public MixReview buildMixReview(String reviewText, UserAccount userAccount, Mix mix) {
        return MixReview.builder()
                .reviewText(reviewText)
                .userAccount(userAccount)
                .mix(mix)
                .build();
    }

    /**
     * Make {@link TrackReview} from data
     *
     * @param reviewText  {@link TrackReview} reviewText
     * @param userAccount {@link TrackReview} userAccount
     * @param track       {@link TrackReview} track
     * @return {@link TrackReview} ready to use in database manipulations
     */
    public TrackReview buildTrackReview(String reviewText, UserAccount userAccount, Track track) {
        return TrackReview.builder()
                .reviewText(reviewText)
                .userAccount(userAccount)
                .track(track)
                .build();
    }

    /**
     * Make {@link AlbumReview} from data
     *
     * @param reviewText  {@link AlbumReview} reviewText
     * @param userAccount {@link AlbumReview} userAccount
     * @param album       {@link AlbumReview} album
     * @return {@link AlbumReview} ready to use in database manipulations
     */
    public AlbumReview buildAlbumReview(String reviewText, UserAccount userAccount, Album album) {
        return AlbumReview.builder()
                .reviewText(reviewText)
                .userAccount(userAccount)
                .album(album)
                .build();
    }
}