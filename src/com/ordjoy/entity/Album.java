package com.ordjoy.entity;

import java.util.Set;

public class Album implements Entity {

    private Long id;
    private String title;
    private Genre genre;
    private Artist artist;
    private Set<Track> tracks;

    public Album(String title) {
        this.title = title;
    }

    public Album(String title, Genre genre) {
        this.title = title;
        this.genre = genre;
    }

    public Album(String title, Genre genre, Artist artist) {
        this.title = title;
        this.genre = genre;
        this.artist = artist;
    }

    public Album(String title, Genre genre, Set<Track> tracks, Artist artist) {
        this.title = title;
        this.genre = genre;
        this.tracks = tracks;
        this.artist = artist;
    }

    public Album() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        return id != null ? id.equals(album.id) : album.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Album{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", genre=" + genre +
               ", tracks=" + tracks +
               ", artist=" + artist +
               '}';
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Set<Track> getTracks() {
        return tracks;
    }

    public void setTracks(Set<Track> tracks) {
        this.tracks = tracks;
    }
}