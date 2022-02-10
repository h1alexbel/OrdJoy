package com.ordjoy.service;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.MixFilter;
import com.ordjoy.dao.impl.MixDaoImpl;
import com.ordjoy.dto.MixDto;
import com.ordjoy.dto.MixReviewDto;
import com.ordjoy.entity.Mix;
import com.ordjoy.exception.DaoException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.mapper.MixMapper;
import com.ordjoy.mapper.MixReviewMapper;
import com.ordjoy.util.LogginUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.SERVICE_LAYER_EXCEPTION_MESSAGE;
import static java.util.stream.Collectors.*;

public class MixService {

    private static final Logger LOGGER = LogManager.getLogger(MixService.class);
    private final MixDaoImpl mixDao = MixDaoImpl.getInstance();
    private static final MixService INSTANCE = new MixService();
    private final MixMapper mixMapper = MixMapper.getInstance();
    private final MixReviewMapper mixReviewMapper = MixReviewMapper.getInstance();

    private MixService() {

    }

    /**
     * @return instance of {@link MixService}
     */
    public static MixService getInstance() {
        return INSTANCE;
    }

    /**
     * Save {@link Mix} in database
     *
     * @param mix that be saved in database
     * @return {@link MixDto} that represents {@link Mix} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public MixDto addNewMix(Mix mix) throws ServiceException {
        try {
            Mix savedMix = mixDao.save(mix);
            return mixMapper.mapFrom(savedMix);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, LogginUtils.ENTITY_SAVE_ERROR, mix, e);
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Check is Mix exists in database or not by mixName
     *
     * @param mixName {@link Mix} name
     * @return boolean value {@code true} if exists or {@code false} if not exists in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public boolean isMixExists(String mixName) throws ServiceException {
        boolean result = false;
        try {
            Optional<Mix> maybeMix = mixDao.findMixByMixName(mixName);
            if (maybeMix.isPresent()) {
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return result;
    }

    /**
     * Find {@link MixDto} from database {@link Mix} id
     *
     * @param id {@link Mix} id
     * @return {@link Optional} of {@link MixDto} if present or {@link Optional} empty
     * @throws ServiceException if Dao layer can not execute method
     */
    public Optional<MixDto> findMixById(Long id) throws ServiceException {
        Optional<MixDto> maybeMix;
        try {
            maybeMix = mixDao.findById(id).stream()
                    .map(mix -> MixDto.builder()
                            .id(mix.getId())
                            .name(mix.getName())
                            .description(mix.getDescription())
                            .build())
                    .findFirst();
            return maybeMix;
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Finds all {@link MixDto}
     *
     * @param filter sets limit/offset
     * @return List of {@link MixDto} that presents {@link Mix} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<MixDto> findAllMixes(MixFilter filter) throws ServiceException {
        List<MixDto> mixes;
        try {
            mixes = mixDao.findAll(filter).stream()
                    .map(mix -> MixDto.builder()
                            .id(mix.getId())
                            .name(mix.getName())
                            .description(mix.getDescription())
                            .build())
                    .collect(toList());
            return mixes;
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Gets all table records from database
     *
     * @return Long value that represents table records
     * @throws ServiceException if Dao layer can not execute method
     */
    public Long getRecords() throws ServiceException {
        try {
            return mixDao.getTableRecords();
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, LogginUtils.FETCH_RECORDS_ERROR, e);
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Update {@link Mix} in database
     *
     * @param mix new value of {@link Mix}
     * @throws ServiceException if Dao layer can not execute method
     */
    public void updateMix(Mix mix) throws ServiceException {
        try {
            mixDao.update(mix);
        } catch (DaoException e) {
            LOGGER.log(Level.ERROR, LogginUtils.ENTITY_UPDATE_ERROR, e);
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Transactional Delete {@link Mix} from database
     *
     * @param id {@link Mix} id from database
     * @return boolean value {@code true} if {@link Mix} successfully deleted {@code false} if it failed
     * @throws ServiceException if Dao layer can not execute method
     */
    public boolean deleteMixById(Long id) throws ServiceException {
        try {
            return mixDao.deleteById(id);
        } catch (DaoException e) {
            LOGGER.log(Level.WARN, LogginUtils.ENTITY_DELETE_WARN, e);
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Find {@link MixDto} by {@link Mix} name
     *
     * @param mixName {@link Mix} name in database
     * @return {@link Optional} of {@link MixDto} if present or {@link Optional} empty
     * @throws ServiceException if Dao layer can not execute method
     */
    public Optional<MixDto> findMixByMixName(String mixName) throws ServiceException {
        Optional<MixDto> maybeMix;
        try {
            maybeMix = mixDao.findMixByMixName(mixName).stream()
                    .map(mix -> MixDto.builder()
                            .id(mix.getId())
                            .name(mix.getName())
                            .description(mix.getDescription())
                            .build())
                    .findFirst();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return maybeMix;
    }

    /**
     * Finds all {@link MixReviewDto} by {@link Mix} name
     *
     * @param mixName {@link Mix} mixName
     * @param filter  sets limit/offset
     * @return List {@link MixReviewDto} that represents {@link com.ordjoy.entity.MixReview} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<MixReviewDto> findMixReviewByMixName(String mixName, DefaultFilter filter) throws ServiceException {
        try {
            return mixDao.findMixReviewByMixName(mixName, filter).stream()
                    .map(mixReviewMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Finds all {@link MixReviewDto} by {@link Mix} id
     *
     * @param mixId  {@link Mix} id
     * @param filter sets limit/offset
     * @return List {@link MixReviewDto} that represents {@link com.ordjoy.entity.MixReview} in database
     * @throws ServiceException if Dao layer can not execute method
     */
    public List<MixReviewDto> findMixReviewByMixId(Long mixId, DefaultFilter filter) throws ServiceException {
        try {
            return mixDao.findMixReviewsByMixId(mixId, filter).stream()
                    .map(mixReviewMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    /**
     * Make {@link Mix} from data
     *
     * @param name        Mix's name
     * @param description Mix's description
     * @return {@link Mix} that ready to use entity in database manipulations
     */
    public Mix buildMix(String name, String description) {
        return Mix.builder()
                .name(name)
                .description(description)
                .build();
    }
}