package com.ordjoy.entity;

import java.util.List;
import java.util.Set;

public class Track implements Entity {

    private int id;
    private String songUrl;
    private String title;
    private Genre genre;
    private Set<Artist> artists;
    private Album album;
    private List<Mix> mixes;

    public Track(String songUrl, String title) {
        this.songUrl = songUrl;
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        if (id != track.id) return false;
        if (songUrl != null ? !songUrl.equals(track.songUrl) : track.songUrl != null) return false;
        if (title != null ? !title.equals(track.title) : track.title != null) return false;
        if (genre != null ? !genre.equals(track.genre) : track.genre != null) return false;
        if (artists != null ? !artists.equals(track.artists) : track.artists != null) return false;
        if (album != null ? !album.equals(track.album) : track.album != null) return false;
        return mixes != null ? mixes.equals(track.mixes) : track.mixes == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (songUrl != null ? songUrl.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (artists != null ? artists.hashCode() : 0);
        result = 31 * result + (album != null ? album.hashCode() : 0);
        result = 31 * result + (mixes != null ? mixes.hashCode() : 0);
        return result;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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