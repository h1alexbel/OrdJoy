package com.ordjoy.dto;

public class TrackDto {

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
               "id=" + id +
               ", url='" + url + '\'' +
               ", title='" + title + '\'' +
               ", album='" + album + '\'' +
               '}';
    }
}