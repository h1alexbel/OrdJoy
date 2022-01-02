package com.ordjoy.entity;

public class Genre implements Entity {

    private Long id;
    private GenreList name;

    public Genre(GenreList name) {
        this.name = name;
    }

    public Genre() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genre genre = (Genre) o;

        return id != null ? id.equals(genre.id) : genre.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Genre{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GenreList getName() {
        return name;
    }

    public void setName(GenreList name) {
        this.name = name;
    }
}