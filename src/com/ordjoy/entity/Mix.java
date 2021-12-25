package com.ordjoy.entity;

import java.util.Set;

public class Mix {

    private int id;
    private String name;
    private String description;
    private Genre genre;
    private Set<Track> tracks;

    public Mix(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mix mix = (Mix) o;

        if (id != mix.id) return false;
        if (name != null ? !name.equals(mix.name) : mix.name != null) return false;
        if (description != null ? !description.equals(mix.description) : mix.description != null) return false;
        if (genre != null ? !genre.equals(mix.genre) : mix.genre != null) return false;
        return tracks != null ? tracks.equals(mix.tracks) : mix.tracks == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (tracks != null ? tracks.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Mix{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", genre=" + genre +
               ", tracks=" + tracks +
               '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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