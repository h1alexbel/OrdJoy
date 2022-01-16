package com.ordjoy.mapper;

import com.ordjoy.dto.AlbumDto;
import com.ordjoy.entity.Album;

public class AlbumMapper implements Mapper<Album, AlbumDto> {

    private static final AlbumMapper INSTANCE = new AlbumMapper();

    private AlbumMapper() {

    }

    public static AlbumMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public AlbumDto mapFrom(Album album) {
        return new AlbumDto(
                album.getId(),
                album.getTitle()
        );
    }
}