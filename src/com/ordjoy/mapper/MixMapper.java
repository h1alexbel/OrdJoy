package com.ordjoy.mapper;

import com.ordjoy.dto.MixDto;
import com.ordjoy.entity.Mix;

public class MixMapper implements Mapper<Mix, MixDto> {

    private static final MixMapper INSTANCE = new MixMapper();

    private MixMapper() {

    }

    public static MixMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public MixDto mapFrom(Mix mix) {
        return new MixDto(
                mix.getId(),
                mix.getName(),
                mix.getDescription()
        );
    }
}