CREATE DATABASE ordjoy_db;

CREATE SCHEMA user_storage;

CREATE SCHEMA audio_tracks_storage;

CREATE TABLE user_storage.user_account
(
    id       BIGSERIAL PRIMARY KEY,
    email    CHARACTER VARYING(64) UNIQUE  NOT NULL,
    login    CHARACTER VARYING(128) UNIQUE NOT NULL,
    password CHARACTER VARYING(128)        NOT NULL,
    role     CHARACTER VARYING             NOT NULL
);

CREATE TABLE user_storage.user_data
(

    first_name      CHARACTER VARYING(64) NOT NULL,
    last_name       CHARACTER VARYING(64) NOT NULL,
    age             INT                   NOT NULL,
    user_account_id BIGINT REFERENCES user_storage.user_account (id),
    PRIMARY KEY (user_account_id)
);

CREATE TABLE audio_tracks_storage.genre
(
    id   BIGSERIAL PRIMARY KEY,
    name CHARACTER VARYING(64) UNIQUE
);

CREATE TABLE audio_tracks_storage.artist
(
    id   BIGSERIAL PRIMARY KEY,
    name CHARACTER VARYING(64) UNIQUE NOT NULL
);

CREATE TABLE audio_tracks_storage.album
(
    id        BIGSERIAL PRIMARY KEY,
    title     CHARACTER VARYING(128) NOT NULL,
    genre_id  BIGINT REFERENCES audio_tracks_storage.genre (id),
    artist_id BIGINT REFERENCES audio_tracks_storage.artist (id)
);

CREATE TABLE audio_tracks_storage.mix
(
    id          BIGSERIAL PRIMARY KEY,
    name        CHARACTER VARYING(64) UNIQUE NOT NULL,
    description TEXT                         NOT NULL,
    genre_id    BIGINT REFERENCES audio_tracks_storage.genre (id)
);

CREATE TABLE audio_tracks_storage.track
(
    id       BIGSERIAL PRIMARY KEY,
    song_url TEXT NOT NULL UNIQUE,
    title    TEXT NOT NULL,
    genre_id BIGINT REFERENCES audio_tracks_storage.genre (id),
    album_id BIGINT REFERENCES audio_tracks_storage.album (id)
);

CREATE TABLE user_storage.order
(

    id              BIGSERIAL PRIMARY KEY,
    price           NUMERIC NOT NULL,
    card_number     BIGINT  NOT NULL,
    user_account_id BIGINT REFERENCES user_storage.user_account (id),
    track_id        BIGINT REFERENCES audio_tracks_storage.track (id)
);

CREATE TABLE audio_tracks_storage.artist_tracks
(
    artist_id BIGINT REFERENCES audio_tracks_storage.artist (id),
    track_id  BIGINT REFERENCES audio_tracks_storage.track (id),
    PRIMARY KEY (artist_id, track_id)
);

CREATE TABLE audio_tracks_storage.track_mixes
(
    track_id BIGINT REFERENCES audio_tracks_storage.track (id),
    mix_id   BIGINT REFERENCES audio_tracks_storage.mix (id),
    PRIMARY KEY (track_id, mix_id)
);

INSERT INTO audio_tracks_storage.genre (name)
VALUES ('POP'),
       ('ELECTRONIC'),
       ('RAP'),
       ('RB'),
       ('LATIN'),
       ('ROCK'),
       ('METAL'),
       ('COUNTRY'),
       ('FOLK'),
       ('JAZZ'),
       ('CLASSICAL'),
       ('NEW_AGE'),
       ('BLUES'),
       ('TRADITIONAL'),
       ('EASY_LISTENING');