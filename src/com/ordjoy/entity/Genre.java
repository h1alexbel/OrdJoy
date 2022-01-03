package com.ordjoy.entity;

public class Genre implements Entity {

    private Long id;
    private GenreType name;

    public Genre(GenreType name) {
        this.name = name;
    }

    public Genre(Long id, GenreType name) {
        this.id = id;
        this.name = name;
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

    public GenreType getName() {
        return name;
    }

    public void setName(GenreType name) {
        this.name = name;
    }
}