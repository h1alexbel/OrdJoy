package com.ordjoy.service;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dto.TrackDto;
import com.ordjoy.entity.Album;
import com.ordjoy.entity.Mix;
import com.ordjoy.entity.Track;
import com.ordjoy.exception.ServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

class TrackServiceTest {

    @Test
    @DisplayName("save track test")
    void addNewTrack() {
        TrackService trackService = TrackService.getInstance();
        Track track = Track.builder()
                .title("Test")
                .songUrl("https://www.youtube.com/watch?v=ITncUwWL8zY&wlist=RDMMywR5u5YYCO8&index=22")
                .album(Album.builder()
                        .title("Another")
                        .build())
                .build();
        try {
            TrackDto saved = trackService.addNewTrack(track);
            TrackDto expected = TrackDto.builder()
                    .title("test")
                    .url("https://www.youtube.com/watch?v=ITncUwWL8zY&wlist=RDMMywR5u5YYCO8&index=22")
                    .build();
            assertEquals(expected.getTitle(), saved.getTitle(), "Track titles must be equal!");
            assertEquals(expected.getUrl(), saved.getUrl(), "Track urls must be equal!");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("find track by id null case test")
    void findTrackByIdNullCase(Long id) {
        TrackService trackService = TrackService.getInstance();
        assertDoesNotThrow(() -> trackService.findTrackById(id));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("update track null case")
    void updateTrackNullCase(Track track) {
        TrackService trackService = TrackService.getInstance();
        assertDoesNotThrow(() -> trackService.updateTrack(track));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("delete track null case test")
    void deleteTrackByIdNullCase(Long id) {
        TrackService trackService = TrackService.getInstance();
        assertDoesNotThrow(() -> trackService.deleteTrackById(id));
    }

    @Test
    @DisplayName("add track to mix test")
    void addExistingTrackToMix() {
        TrackService trackService = TrackService.getInstance();
        Mix mix = Mix.builder()
                .name("Best of Metalica2")
                .description("For real Rock fans! Enjoy")
                .build();
        Track track = Track.builder()
                .title("Lil mosey - So fast")
                .songUrl("https://www.youtube.com/watch?v=ITncUwWL8zY&list=RDMMywR5u5YYCO8&index=22")
                .album(Album.builder()
                        .title("DS4EverUpd")
                        .build())
                .build();
        try {
            boolean actual = trackService.addExistingTrackToMix(mix, track);
            boolean expected = true;
            assertEquals(expected, actual);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("find track by title null case test")
    void findByTrackTitleNullCase(String title) {
        TrackService trackService = TrackService.getInstance();
        assertDoesNotThrow(() -> trackService.findByTrackTitle(title));
    }

    @Test
    void findTracksByAlbumIdNullCase() {
        TrackService trackService = TrackService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() ->
                        trackService.findTracksByAlbumId(null, new DefaultFilter(20, 0))),
                () -> assertDoesNotThrow(() ->
                        trackService.findTracksByAlbumId(20L, null)),
                () -> assertDoesNotThrow(() ->
                        trackService.findTracksByAlbumId(null, null))
        );
    }

    @Test
    void findTracksByAlbumName() {
        TrackService trackService = TrackService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() ->
                        trackService.findTracksByAlbumName(null, new DefaultFilter(20, 0))),
                () -> assertDoesNotThrow(() ->
                        trackService.findTracksByAlbumName("test", null)),
                () -> assertDoesNotThrow(() ->
                        trackService.findTracksByAlbumName(null, null))
        );
    }
}