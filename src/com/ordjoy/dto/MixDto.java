package com.ordjoy.dto;

import java.io.Serializable;

public class MixDto implements Serializable {

    private final Long id;
    private final String name;
    private final String description;

    public MixDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String description;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public MixDto build() {
            return new MixDto(id, name, description);
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MixDto mixDto = (MixDto) o;

        return id != null ? id.equals(mixDto.id) : mixDto.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MixDto{" +
               " name='" + name + '\'' +
               ", description='" + description + '\'' +
               '}';
    }
}