package com.ordjoy.dto;

import com.ordjoy.entity.AlbumReview;
import com.ordjoy.entity.Track;

import java.util.Set;

public class AlbumDto {

    private final Long id;
    private final String title;
    private final Set<Track> tracks;
    private final Set<AlbumReview> albumReviews;

    public AlbumDto(Long id, String title, Set<Track> tracks, Set<AlbumReview> albumReviews) {
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

    public Set<Track> getTracks() {
        return tracks;
    }

    public Set<AlbumReview> getAlbumReviews() {
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