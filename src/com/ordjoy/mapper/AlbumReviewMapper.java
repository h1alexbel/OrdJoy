package com.ordjoy.mapper;

import com.ordjoy.dto.AlbumReviewDto;
import com.ordjoy.entity.AlbumReview;

public class AlbumReviewMapper implements Mapper<AlbumReview, AlbumReviewDto> {

    private static final AlbumReviewMapper INSTANCE = new AlbumReviewMapper();

    private AlbumReviewMapper() {

    }

    public static AlbumReviewMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public AlbumReviewDto mapFrom(AlbumReview albumReview) {
        return new AlbumReviewDto(
                albumReview.getId(),
                albumReview.getReviewText(),
                AlbumMapper.getInstance().mapFrom(albumReview.getAlbum()),
                UserAccountMapper.getInstance().mapFrom(albumReview.getUserAccount())
        );
    }
}