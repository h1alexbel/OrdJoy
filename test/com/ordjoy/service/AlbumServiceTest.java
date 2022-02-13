package com.ordjoy.service;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dto.AlbumDto;
import com.ordjoy.entity.Album;
import com.ordjoy.exception.ServiceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlbumServiceTest {

    @Test
    public void saveAlbum() {
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

    @Test
    public void findAlbumByIdNullCase() {
        AlbumService albumService = AlbumService.getInstance();
        assertDoesNotThrow(() -> albumService.findAlbumById(null));
    }

    @Test
    public void updateAlbumNullCase() {
        AlbumService albumService = AlbumService.getInstance();
        assertDoesNotThrow(() -> albumService.updateAlbum(null));
    }

    @Test
    public void deleteAlbumByIdNullCase() {
        AlbumService albumService = AlbumService.getInstance();
        assertDoesNotThrow(() -> albumService.deleteAlbumById(null));
    }

    @Test
    public void findAlbumByTitleNullCase() {
        AlbumService albumService = AlbumService.getInstance();
        assertDoesNotThrow(() -> albumService.findAlbumByTitle(null));
    }

    @Test
    public void findAlbumReviewsByAlbumTitle() {
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
    public void findAlbumReviewsByAlbumIdNullCase() {
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