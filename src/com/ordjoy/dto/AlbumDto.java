package com.ordjoy.dto;

import java.util.Set;

public class AlbumDto {

    private final Long id;
    private final String title;
    private final Set<TrackDto> tracks;
    private final Set<AlbumReviewDto> albumReviews;

    public AlbumDto(Long id, String title, Set<TrackDto> tracks, Set<AlbumReviewDto> albumReviews) {
        this.id = id;
        this.title = title;
        this.tracks = tracks;
        this.albumReviews = albumReviews;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Set<TrackDto> getTracks() {
        return tracks;
    }

    public Set<AlbumReviewDto> getAlbumReviews() {
        return albumReviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlbumDto albumDto = (AlbumDto) o;

        return id != null ? id.equals(albumDto.id) : albumDto.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AlbumDto{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", tracks=" + tracks +
               ", albumReviews=" + albumReviews +
               '}';
    }
}