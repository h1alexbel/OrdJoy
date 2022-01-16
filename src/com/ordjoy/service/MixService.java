package com.ordjoy.service;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.MixFilter;
import com.ordjoy.dao.impl.MixDaoImpl;
import com.ordjoy.dto.MixDto;
import com.ordjoy.dto.MixReviewDto;
import com.ordjoy.entity.Mix;
import com.ordjoy.entity.MixReview;
import com.ordjoy.exception.DaoException;
import com.ordjoy.exception.ServiceException;
import com.ordjoy.mapper.MixMapper;
import com.ordjoy.mapper.MixReviewMapper;

import java.util.List;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.SERVICE_LAYER_EXCEPTION_MESSAGE;
import static java.util.stream.Collectors.*;

public class MixService {

    private final MixDaoImpl mixDao = MixDaoImpl.getInstance();
    private static final MixService INSTANCE = new MixService();
    private final MixMapper mixMapper = MixMapper.getInstance();
    private final MixReviewMapper mixReviewMapper = MixReviewMapper.getInstance();

    private MixService() {

    }

    public static MixService getInstance() {
        return INSTANCE;
    }

    public MixDto addNewMix(Mix mix) throws ServiceException {
        try {
            Mix savedMix = mixDao.save(mix);
            MixDto mixDto = mixMapper.mapFrom(savedMix);
            return mixDto;
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public Optional<MixDto> findMixById(Long id) throws ServiceException {
        Optional<MixDto> maybeMix;
        try {
            maybeMix = mixDao.findById(id).stream()
                    .map(mix -> new MixDto(
                            mix.getId(),
                            mix.getName(),
                            mix.getDescription()
                    )).findFirst();
            return maybeMix;
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<MixDto> findAllMixes(MixFilter filter) throws ServiceException {
        List<MixDto> mixes;
        try {
            mixes = mixDao.findAll(filter).stream()
                    .map(mix -> new MixDto(
                            mix.getId(),
                            mix.getName(),
                            mix.getDescription()
                    )).collect(toList());
            return mixes;
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public void updateMix(Mix mix) throws ServiceException {
        try {
            mixDao.update(mix);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public boolean deleteMixById(Long id) throws ServiceException {
        try {
            return mixDao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public Optional<MixDto> findMixByMixName(String mixName) throws ServiceException {
        Optional<MixDto> maybeMix;
        try {
            maybeMix = mixDao.findMixByMixName(mixName).stream()
                    .map(mix -> new MixDto(
                            mix.getId(),
                            mix.getName(),
                            mix.getDescription()
                    )).findFirst();
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
        return maybeMix;
    }

    public List<MixReviewDto> findMixReviewByMixName(String mixName, DefaultFilter filter) throws ServiceException {
        try {
            return mixDao.findMixReviewByMixName(mixName, filter).stream()
                    .map(mixReviewMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    public List<MixReviewDto> findMixReviewByMixId(Long mixId, DefaultFilter filter) throws ServiceException {
        try {
            return mixDao.findMixReviewsByMixId(mixId, filter).stream()
                    .map(mixReviewMapper::mapFrom).collect(toList());
        } catch (DaoException e) {
            throw new ServiceException(SERVICE_LAYER_EXCEPTION_MESSAGE, e);
        }
    }
}