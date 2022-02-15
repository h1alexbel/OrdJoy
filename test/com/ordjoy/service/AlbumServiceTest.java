package com.ordjoy.service;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dto.AlbumDto;
import com.ordjoy.entity.Album;
import com.ordjoy.exception.ServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class AlbumServiceTest {

    @Test
    @DisplayName("Save album test")
    void saveAlbum() {
        AlbumService albumService = AlbumService.getInstance();
        Album album = Album.builder()
                .title("New")
                .build();
        try {
            AlbumDto saved = albumService.saveAlbum(album);
            AlbumDto expected = new AlbumDto(null, "New");
            assertEquals(expected.getTitle(), saved.getTitle(), "Album names must be equal!");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("find album by id null case")
    void findAlbumByIdNullCase(Long id) {
        AlbumService albumService = AlbumService.getInstance();
        assertDoesNotThrow(() -> albumService.findAlbumById(id));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Update album null case")
    void updateAlbumNullCase(Album album) {
        AlbumService albumService = AlbumService.getInstance();
        assertDoesNotThrow(() -> albumService.updateAlbum(album));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("delete album null case")
    void deleteAlbumByIdNullCase(Long id) {
        AlbumService albumService = AlbumService.getInstance();
        assertDoesNotThrow(() -> albumService.deleteAlbumById(id));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("find album by id null case")
    void findAlbumByTitleNullCase(String albumTitle) {
        AlbumService albumService = AlbumService.getInstance();
        assertDoesNotThrow(() -> albumService.findAlbumByTitle(albumTitle));
    }

    @Test
    void findAlbumReviewsByAlbumTitle() {
        AlbumService albumService = AlbumService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() ->
                        albumService.findAlbumReviewsByAlbumTitle("New", null)),
                () -> assertDoesNotThrow(() ->
                        albumService.findAlbumReviewsByAlbumTitle(null,
                                new DefaultFilter(20, 0))),
                () -> assertDoesNotThrow(() ->
                        albumService.findAlbumReviewsByAlbumTitle(null, null))
        );
    }

    @Test
    void findAlbumReviewsByAlbumIdNullCase() {
        AlbumService albumService = AlbumService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() ->
                        albumService.findAlbumReviewsByAlbumId(1L, null)),
                () -> assertDoesNotThrow(() ->
                        albumService.findAlbumReviewsByAlbumId(null,
                                new DefaultFilter(20, 0))),
                () -> assertDoesNotThrow(() ->
                        albumService.findAlbumReviewsByAlbumId(null, null))
        );
    }
}