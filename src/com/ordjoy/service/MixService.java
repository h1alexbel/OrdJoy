package com.ordjoy.service;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dao.filter.MixFilter;
import com.ordjoy.dao.impl.MixDaoImpl;
import com.ordjoy.dto.MixDto;
import com.ordjoy.dto.MixReviewDto;
import com.ordjoy.entity.Mix;
import com.ordjoy.exception.ServiceException;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

public class MixService {

    private final MixDaoImpl mixDao = MixDaoImpl.getInstance();
    private static final MixService INSTANCE = new MixService();

    private MixService() {

    }

    public static MixService getInstance() {
        return INSTANCE;
    }

    public Mix saveMix(Mix mix) throws ServiceException {
        return mixDao.save(mix);
    }

    public Optional<MixDto> findMixById(Long id) throws ServiceException {
        return mixDao.findById(id).stream()
                .map(mix -> new MixDto(
                        mix.getId(),
                        mix.getName(),
                        mix.getDescription()
                )).findFirst();
    }

    public List<MixDto> findAllMixes(MixFilter filter) throws ServiceException {
        return mixDao.findAll(filter).stream()
                .map(mix -> new MixDto(
                        mix.getId(),
                        mix.getName(),
                        mix.getDescription()
                )).collect(toList());
    }

    public void updateMix(Mix mix) throws ServiceException {
        mixDao.update(mix);
    }

    public boolean deleteMixById(Long id) throws ServiceException {
        return mixDao.deleteById(id);
    }

    public Optional<MixDto> findMixByMixName(String mixName) throws ServiceException {
        return mixDao.findMixByMixName(mixName).stream()
                .map(mix -> new MixDto(
                        mix.getId(),
                        mix.getName(),
                        mix.getDescription()
                )).findFirst();
    }

    public List<MixReviewDto> findMixReviewByMixName(String mixName, DefaultFilter filter) throws ServiceException {
        return mixDao.findMixReviewByMixName(mixName, filter).stream()
                .map(mixReview -> new MixReviewDto(
                        mixReview.getId(),
                        mixReview.getReviewText(),
                        mixReview.getMix().getName(),
                        mixReview.getUserAccount().getLogin()
                )).collect(toList());
    }

    public List<MixReviewDto> findMixReviewsByMixId(Long mixId, DefaultFilter filter) throws ServiceException {
        return mixDao.findMixReviewsByMixId(mixId, filter).stream()
                .map(mixReview -> new MixReviewDto(
                        mixReview.getId(),
                        mixReview.getReviewText(),
                        mixReview.getMix().getName(),
                        mixReview.getUserAccount().getLogin()
                )).collect(toList());
    }
}