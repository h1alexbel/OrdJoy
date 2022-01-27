package com.ordjoy.dto;

import java.io.Serializable;

public class AlbumDto implements Serializable {

    private final Long id;
    private final String title;

    public AlbumDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
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
               "title='" + title + '\'' +
               '}';
    }
}