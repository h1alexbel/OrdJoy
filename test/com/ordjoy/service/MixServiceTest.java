package com.ordjoy.service;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dto.MixDto;
import com.ordjoy.entity.Mix;
import com.ordjoy.exception.ServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class MixServiceTest {

    @Test
    @DisplayName("save mix test")
    void addNewMix() {
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

    @ParameterizedTest
    @NullSource
    @DisplayName("find mix by id null case test")
    void findMixByIdNullCase(Long id) {
        MixService mixService = MixService.getInstance();
        assertDoesNotThrow(() -> mixService.findMixById(id));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("update mix null case test")
    void updateMixNullCase(Mix mix) {
        MixService mixService = MixService.getInstance();
        assertDoesNotThrow(() -> mixService.updateMix(mix));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("delete mix null case test")
    void deleteMixByIdNullCase(Long id) {
        MixService mixService = MixService.getInstance();
        assertDoesNotThrow(() -> mixService.deleteMixById(id));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("find mix by name null case test")
    void findMixByMixNameNullCase(String name) {
        MixService mixService = MixService.getInstance();
        assertDoesNotThrow(() -> mixService.findMixByMixName(name));
    }

    @Test
    void findMixReviewByMixNameNullCase() {
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
    void findMixReviewByMixIdNullCase() {
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