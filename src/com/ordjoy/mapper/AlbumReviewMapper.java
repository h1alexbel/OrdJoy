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
        return AlbumReviewDto.builder()
                .id(albumReview.getId())
                .reviewText(albumReview.getReviewText())
                .album(AlbumMapper.getInstance().mapFrom(albumReview.getAlbum()))
                .userAccount(UserAccountMapper.getInstance().mapFrom(albumReview.getUserAccount()))
                .build();
    }
}