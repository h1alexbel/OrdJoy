package com.ordjoy.service;

import com.ordjoy.dao.filter.DefaultFilter;
import com.ordjoy.dto.*;
import com.ordjoy.entity.*;
import com.ordjoy.exception.ServiceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReviewServiceTest {

    @Test
    public void addMixReview() {
        ReviewService reviewService = ReviewService.getInstance();
        MixReview mixReview = MixReview.builder()
                .mix(Mix.builder()
                        .name("NameForTest")
                        .description("Test description")
                        .build())
                .reviewText("Review text for test!")
                .userAccount(UserAccount.builder()
                        .email("test@gmail.com")
                        .login("test")
                        .userData(UserData.builder()
                                .userRole(UserRole.CLIENT_ROLE)
                                .build())
                        .build())
                .build();
        try {
            MixReviewDto saved = reviewService.addMixReview(mixReview);
            MixReviewDto expected = MixReviewDto.builder()
                    .mix(MixDto.builder()
                            .name("NameForTest")
                            .description("Test description")
                            .build())
                    .reviewText("Review text for test!")
                    .userAccount(UserAccountDto.builder()
                            .email("test@gmail.com")
                            .login("test")
                            .userRole(UserRole.CLIENT_ROLE)
                            .build())
                    .build();
            assertEquals(expected.getMix().getName(), saved.getMix().getName(),
                    "Mix names must be equal!");
            assertEquals(expected.getMix().getDescription(), saved.getMix().getDescription(),
                    "Mix descriptions must be equal!");
            assertEquals(expected.getReviewText(), saved.getReviewText(),
                    "Review texts must be equal!");
            assertEquals(expected.getUserAccount().getEmail(), saved.getUserAccount().getEmail(),
                    "User emails must be equal!");
            assertEquals(expected.getUserAccount().getLogin(), saved.getUserAccount().getLogin(),
                    "User names must be equal!");
            assertEquals(expected.getUserAccount().getRole(), saved.getUserAccount().getRole(),
                    "User roles must be equal!");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addTrackReview() {
        ReviewService reviewService = ReviewService.getInstance();
        TrackReview trackReview = TrackReview.builder()
                .track(Track.builder()
                        .title("Test")
                        .songUrl("https://www.youtube.com/watch?v=ITncUwWL8zY&wlist=RDMMywR5u5YYCO8&index=22")
                        .album(Album.builder()
                                .title("Another")
                                .build())
                        .build())
                .reviewText("Review text for test!")
                .userAccount(UserAccount.builder()
                        .email("test@gmail.com")
                        .login("test")
                        .userData(UserData.builder()
                                .userRole(UserRole.CLIENT_ROLE)
                                .build())
                        .build())
                .build();
        try {
            TrackReviewDto saved = reviewService.addTrackReview(trackReview);
            TrackReviewDto expected = TrackReviewDto.builder()
                    .track(TrackDto.builder()
                            .title("Test")
                            .url("https://www.youtube.com/watch?v=ITncUwWL8zY&wlist=RDMMywR5u5YYCO8&index=22")
                            .build())
                    .reviewText("Review text for test!")
                    .userAccount(UserAccountDto.builder()
                            .email("test@gmail.com")
                            .login("test")
                            .userRole(UserRole.CLIENT_ROLE)
                            .build())
                    .build();
            assertEquals(expected.getTrack().getTitle(), saved.getTrack().getTitle(),
                    "Track titles must be equal!");
            assertEquals(expected.getTrack().getUrl(), saved.getTrack().getUrl(),
                    "Track urls must be equal!");
            assertEquals(expected.getReviewText(), saved.getReviewText(),
                    "Review texts must be equal!");
            assertEquals(expected.getUserAccount().getEmail(), saved.getUserAccount().getEmail(),
                    "User emails must be equal!");
            assertEquals(expected.getUserAccount().getLogin(), saved.getUserAccount().getLogin(),
                    "User names must be equal!");
            assertEquals(expected.getUserAccount().getRole(), saved.getUserAccount().getRole(),
                    "User roles must be equal!");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addAlbumReview() {
        ReviewService reviewService = ReviewService.getInstance();
        AlbumReview albumReview = AlbumReview.builder()
                .album(Album.builder()
                        .title("Another")
                        .build())
                .reviewText("Review text for test!")
                .userAccount(UserAccount.builder()
                        .email("test@gmail.com")
                        .login("test")
                        .userData(UserData.builder()
                                .userRole(UserRole.CLIENT_ROLE)
                                .build())
                        .build())
                .build();
        try {
            AlbumReviewDto saved = reviewService.addAlbumReview(albumReview);
            AlbumReviewDto expected = AlbumReviewDto.builder()
                    .album(new AlbumDto(null, "Another"))
                    .reviewText("Review text for test!")
                    .userAccount(UserAccountDto.builder()
                            .email("test@gmail.com")
                            .login("test")
                            .userRole(UserRole.CLIENT_ROLE)
                            .build())
                    .build();
            assertEquals(expected.getAlbum().getTitle(), saved.getAlbum().getTitle(),
                    "Album names must be equal!");
            assertEquals(expected.getReviewText(), saved.getReviewText(),
                    "Review texts must be equal!");
            assertEquals(expected.getUserAccount().getEmail(), saved.getUserAccount().getEmail(),
                    "User emails must be equal!");
            assertEquals(expected.getUserAccount().getLogin(), saved.getUserAccount().getLogin(),
                    "User names must be equal!");
            assertEquals(expected.getUserAccount().getRole(), saved.getUserAccount().getRole(),
                    "User roles must be equal!");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findMixReviewByIdNullCase() {
        ReviewService reviewService = ReviewService.getInstance();
        assertDoesNotThrow(() -> reviewService.findMixReviewById(null));
    }

    @Test
    public void findTrackReviewByIdNullCase() {
        ReviewService reviewService = ReviewService.getInstance();
        assertDoesNotThrow(() -> reviewService.findTrackReviewById(null));
    }

    @Test
    public void findAlbumReviewByIdNullCase() {
        ReviewService reviewService = ReviewService.getInstance();
        assertDoesNotThrow(() -> reviewService.findAlbumReviewById(null));
    }

    @Test
    public void updateMixReviewNullCase() {
        ReviewService reviewService = ReviewService.getInstance();
        assertDoesNotThrow(() -> reviewService.updateMixReview(null));
    }

    @Test
    public void updateTrackReviewNullCase() {
        ReviewService reviewService = ReviewService.getInstance();
        assertDoesNotThrow(() -> reviewService.updateTrackReview(null));
    }

    @Test
    public void updateAlbumReviewNullCase() {
        ReviewService reviewService = ReviewService.getInstance();
        assertDoesNotThrow(() -> reviewService.updateAlbumReview(null));
    }

    @Test
    public void deleteMixReviewByIdNullCase() {
        ReviewService reviewService = ReviewService.getInstance();
        assertDoesNotThrow(() -> reviewService.deleteMixReviewById(null));
    }

    @Test
    public void deleteTrackReviewByIdNullCase() {
        ReviewService reviewService = ReviewService.getInstance();
        assertDoesNotThrow(() -> reviewService.deleteTrackReviewById(null));
    }

    @Test
    public void deleteAlbumReviewByIdNullCase() {
        ReviewService reviewService = ReviewService.getInstance();
        assertDoesNotThrow(() -> reviewService.deleteAlbumReviewById(null));
    }

    @Test
    public void findMixReviewsByUserLoginNullCase() {
        ReviewService reviewService = ReviewService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() -> reviewService.findMixReviewsByUserLogin("test", null)),
                () -> assertDoesNotThrow(() -> reviewService.findMixReviewsByUserLogin(null, null)),
                () -> assertDoesNotThrow(() -> reviewService.findMixReviewsByUserLogin(null,
                        new DefaultFilter(20, 0)))
        );
    }

    @Test
    public void findTrackReviewsByUserLoginNullCase() {
        ReviewService reviewService = ReviewService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() -> reviewService.findTrackReviewsByUserLogin("test", null)),
                () -> assertDoesNotThrow(() -> reviewService.findTrackReviewsByUserLogin(null, null)),
                () -> assertDoesNotThrow(() -> reviewService.findTrackReviewsByUserLogin(null,
                        new DefaultFilter(20, 0)))
        );
    }

    @Test
    public void findAlbumReviewsByUserLoginNullCase() {
        ReviewService reviewService = ReviewService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() -> reviewService.findAlbumReviewsByUserLogin("test", null)),
                () -> assertDoesNotThrow(() -> reviewService.findAlbumReviewsByUserLogin(null, null)),
                () -> assertDoesNotThrow(() -> reviewService.findAlbumReviewsByUserLogin(null,
                        new DefaultFilter(20, 0)))
        );
    }

    @Test
    public void findMixReviewsByUserIdNullCase() {
        ReviewService reviewService = ReviewService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() -> reviewService.findMixReviewsByUserId(1L, null)),
                () -> assertDoesNotThrow(() -> reviewService.findMixReviewsByUserId(null, null)),
                () -> assertDoesNotThrow(() -> reviewService.findMixReviewsByUserId(null,
                        new DefaultFilter(20, 0)))
        );
    }

    @Test
    public void findTrackReviewsByUserIdNullCase() {
        ReviewService reviewService = ReviewService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() -> reviewService.findTrackReviewsByUserId(1L, null)),
                () -> assertDoesNotThrow(() -> reviewService.findTrackReviewsByUserId(null, null)),
                () -> assertDoesNotThrow(() -> reviewService.findTrackReviewsByUserId(null,
                        new DefaultFilter(20, 0)))
        );
    }

    @Test
    public void findAlbumReviewsByUserIdNullCase() {
        ReviewService reviewService = ReviewService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() -> reviewService.findAlbumReviewsByUserId(1L, null)),
                () -> assertDoesNotThrow(() -> reviewService.findAlbumReviewsByUserId(null, null)),
                () -> assertDoesNotThrow(() -> reviewService.findAlbumReviewsByUserId(null,
                        new DefaultFilter(20, 0)))
        );
    }

    @Test
    public void findTrackReviewsByTrackIdNullCase() {
        ReviewService reviewService = ReviewService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() -> reviewService.findTrackReviewsByTrackId(1L, null)),
                () -> assertDoesNotThrow(() -> reviewService.findTrackReviewsByTrackId(null, null)),
                () -> assertDoesNotThrow(() -> reviewService.findTrackReviewsByTrackId(null,
                        new DefaultFilter(20, 0)))
        );
    }

    @Test
    public void findTrackReviewsByTrackTitleNullCase() {
        ReviewService reviewService = ReviewService.getInstance();
        assertAll(
                () -> assertDoesNotThrow(() -> reviewService.findTrackReviewsByTrackTitle("test", null)),
                () -> assertDoesNotThrow(() -> reviewService.findTrackReviewsByTrackTitle(null, null)),
                () -> assertDoesNotThrow(() -> reviewService.findTrackReviewsByTrackTitle(null,
                        new DefaultFilter(20, 0)))
        );
    }
}