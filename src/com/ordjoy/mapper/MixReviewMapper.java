package com.ordjoy.mapper;

import com.ordjoy.dto.MixReviewDto;
import com.ordjoy.entity.MixReview;

public class MixReviewMapper implements Mapper<MixReview, MixReviewDto> {

    private static final MixReviewMapper INSTANCE = new MixReviewMapper();

    public static MixReviewMapper getInstance() {
        return INSTANCE;
    }

    private MixReviewMapper() {

    }

    @Override
    public MixReviewDto mapFrom(MixReview mixReview) {
        return new MixReviewDto(
                mixReview.getId(),
                mixReview.getReviewText(),
                MixMapper.getInstance().mapFrom(mixReview.getMix()),
                UserAccountMapper.getInstance().mapFrom(mixReview.getUserAccount())
        );
    }
}