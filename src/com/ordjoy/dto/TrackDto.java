package com.ordjoy.dto;

import java.io.Serializable;

public class TrackDto implements Serializable {

    private final Long id;
    private final String url;
    private final String title;
    private final AlbumDto album;

    public TrackDto(Long id, String url, String title, AlbumDto album) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.album = album;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String url;
        private String title;
        private AlbumDto album;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder album(AlbumDto album) {
            this.album = album;
            return this;
        }

        public TrackDto build() {
            return new TrackDto(id, url, title, album);
        }
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public AlbumDto getAlbum() {
        return album;
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
               "url='" + url + '\'' +
               ", title='" + title + '\'' +
               ", album=" + album +
               '}';
    }
}