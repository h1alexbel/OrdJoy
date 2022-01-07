package com.ordjoy.entity;

import java.util.List;
import java.util.Set;

public class Track implements Entity {

    private Long id;
    private String songUrl;
    private String title;
    private Genre genre;
    private Album album;
    private Set<Artist> artists;
    private List<Mix> mixes;

    public Track(String songUrl, String title, Genre genre, Album album) {
        this.songUrl = songUrl;
        this.title = title;
        this.genre = genre;
        this.album = album;
    }

    public Track(String title) {
        this.title = title;
    }

    public Track(String songUrl, String title) {
        this.songUrl = songUrl;
        this.title = title;
    }

    public Track(Long id, String songUrl, String title, Genre genre, Album album) {
        this.id = id;
        this.songUrl = songUrl;
        this.title = title;
        this.genre = genre;
        this.album = album;
    }

    public Track() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        return id != null ? id.equals(track.id) : track.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Track{" +
               "id=" + id +
               ", songUrl='" + songUrl + '\'' +
               ", title='" + title + '\'' +
               ", genre=" + genre +
               ", artists=" + artists +
               '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
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

    public Set<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Mix> getMixes() {
        return mixes;
    }

    public void setMixes(List<Mix> mixes) {
        this.mixes = mixes;
    }
}