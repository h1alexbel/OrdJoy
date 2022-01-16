package com.ordjoy.mapper;

import com.ordjoy.dto.TrackReviewDto;
import com.ordjoy.entity.TrackReview;

public class TrackReviewMapper implements Mapper<TrackReview, TrackReviewDto> {

    private static final TrackReviewMapper INSTANCE = new TrackReviewMapper();

    private TrackReviewMapper() {

    }

    public static TrackReviewMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public TrackReviewDto mapFrom(TrackReview trackReview) {
        return new TrackReviewDto(
                trackReview.getId(),
                trackReview.getReviewText(),
                TrackMapper.getInstance().mapFrom(trackReview.getTrack()),
                UserAccountMapper.getInstance().mapFrom(trackReview.getUserAccount())
        );
    }
}