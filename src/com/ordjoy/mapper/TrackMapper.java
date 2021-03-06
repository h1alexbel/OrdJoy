package com.ordjoy.mapper;

import com.ordjoy.dto.TrackDto;
import com.ordjoy.entity.Track;

public class TrackMapper implements Mapper<Track, TrackDto> {

    private static final TrackMapper INSTANCE = new TrackMapper();

    private TrackMapper() {

    }

    public static TrackMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public TrackDto mapFrom(Track track) {
        return TrackDto.builder()
                .id(track.getId())
                .url(track.getSongUrl())
                .title(track.getTitle())
                .album(AlbumMapper.getInstance().mapFrom(track.getAlbum()))
                .build();
    }
}