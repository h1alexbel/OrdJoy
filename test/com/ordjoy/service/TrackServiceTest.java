package com.ordjoy.service;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dto.TrackDto;
import com.ordjoy.entity.Album;
import com.ordjoy.entity.Mix;
import com.ordjoy.entity.Track;
import com.ordjoy.exception.ServiceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrackServiceTest {

    @Test
    public void addNewTrack() {
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

    @Test
   public void findTrackByIdNullCase() {
        TrackService trackService = TrackService.getInstance();
        assertDoesNotThrow(() -> trackService.findTrackById(null));
    }

    @Test
   public void updateTrackNullCase() {
        TrackService trackService = TrackService.getInstance();
        assertDoesNotThrow(() -> trackService.updateTrack(null));
    }

    @Test
   public void deleteTrackByIdNullCase() {
        TrackService trackService = TrackService.getInstance();
        assertDoesNotThrow(() -> trackService.deleteTrackById(null));
    }

    @Test
   public void addExistingTrackToMix() {
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

    @Test
   public void findByTrackTitleNullCase() {
        TrackService trackService = TrackService.getInstance();
        assertDoesNotThrow(() -> trackService.findByTrackTitle(null));
    }

    @Test
   public void findTracksByAlbumIdNullCase() {
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
   public void findTracksByAlbumName() {
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