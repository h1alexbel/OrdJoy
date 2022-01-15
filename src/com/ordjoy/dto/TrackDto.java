package com.ordjoy.dto;

import java.util.Set;

public class TrackDto {

    private final Long id;
    private final String title;
    private final AlbumDto album;
    private final Set<TrackReviewDto> trackReviews;

    public TrackDto(Long id, String title, AlbumDto album, Set<TrackReviewDto> trackReviews) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.trackReviews = trackReviews;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public AlbumDto getAlbum() {
        return album;
    }

    public Set<TrackReviewDto> getTrackReviews() {
        return trackReviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrackDto trackDto = (TrackDto) o;

        return id != null ? id.equals(trackDto.id) : trackDto.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TrackDto{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", trackReviews=" + trackReviews +
               '}';
    }
}