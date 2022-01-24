package com.ordjoy.entity;

import java.util.List;
import java.util.Set;

public class Track implements Entity {

    private Long id;
    private String songUrl;
    private String title;
    private Album album;
    private List<Mix> mixes;
    private Set<TrackReview> reviews;

    public Track() {
    }

    public Track(String songUrl, String title, Album album) {
        this.songUrl = songUrl;
        this.title = title;
        this.album = album;
    }

    public Track(String songUrl, String title, Album album, List<Mix> mixes) {
        this.songUrl = songUrl;
        this.title = title;
        this.album = album;
        this.mixes = mixes;
    }

    public Track(String songUrl, String title, Album album, Set<TrackReview> reviews) {
        this.songUrl = songUrl;
        this.title = title;
        this.album = album;
        this.reviews = reviews;
    }

    public Track(String songUrl, String title) {
        this.songUrl = songUrl;
        this.title = title;
    }

    public Track(Long id, String songUrl, String title, Album album, Set<TrackReview> reviews) {
        this.id = id;
        this.songUrl = songUrl;
        this.title = title;
        this.album = album;
        this.reviews = reviews;
    }

    public Track(Long id, String songUrl, String title, Album album) {
        this.id = id;
        this.songUrl = songUrl;
        this.title = title;
        this.album = album;
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

    public Set<TrackReview> getReviews() {
        return reviews;
    }

    public void setReviews(Set<TrackReview> reviews) {
        this.reviews = reviews;
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
               ", album=" + album +
               '}';
    }
}