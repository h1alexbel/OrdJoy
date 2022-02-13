package com.ordjoy.service;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dto.MixDto;
import com.ordjoy.entity.Mix;
import com.ordjoy.exception.ServiceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MixServiceTest {

    @Test
    public void addNewMix() {
        MixService mixService = MixService.getInstance();
        Mix mix = Mix.builder()
                .name("NameForTest")
                .description("Test description")
                .build();
        try {
            MixDto saved = mixService.addNewMix(mix);
            MixDto expected = MixDto.builder()
                    .name("NameForTest")
                    .description("Test description")
                    .build();
            assertEquals(expected.getName(), saved.getName(), "Mix names must be equal");
            assertEquals(expected.getDescription(), saved.getDescription(), "Mix descriptions must be equal");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findMixByIdNullCase() {
        MixService mixService = MixService.getInstance();
        assertDoesNotThrow(() -> mixService.findMixById(null));
    }

    @Test
    public void updateMixNullCase() {
        MixService mixService = MixService.getInstance();
        assertDoesNotThrow(() -> mixService.updateMix(null));
    }

    @Test
    public void deleteMixByIdNullCase() {
        MixService mixService = MixService.getInstance();
        assertDoesNotThrow(() -> mixService.deleteMixById(null));
    }

    @Test
    public void findMixByMixNameNullCase() {
        MixService mixService = MixService.getInstance();
        assertDoesNotThrow(() -> mixService.findMixByMixName(null));
    }

    @Test
    public void findMixReviewByMixNameNullCase() {
        MixService mixService = MixService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() ->
                        mixService.findMixReviewByMixName("Name", null)),
                () -> assertDoesNotThrow(() ->
                        mixService.findMixReviewByMixName(null,
                                new DefaultFilter(20, 0))),
                () -> assertDoesNotThrow(() ->
                        mixService.findMixReviewByMixName(null, null))
        );
    }

    @Test
    public void findMixReviewByMixIdNullCase() {
        MixService mixService = MixService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() ->
                        mixService.findMixReviewByMixId(1L, null)),
                () -> assertDoesNotThrow(() ->
                        mixService.findMixReviewByMixId(null,
                                new DefaultFilter(20, 0))),
                () -> assertDoesNotThrow(() ->
                        mixService.findMixReviewByMixId(null, null))
        );
    }
}