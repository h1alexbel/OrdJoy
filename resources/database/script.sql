CREATE DATABASE ordjoy_db;

CREATE SCHEMA user_storage;

CREATE SCHEMA audio_tracks_storage;

CREATE TABLE user_storage.client
(
    id        SERIAL PRIMARY KEY,
    user_name CHARACTER VARYING(64) UNIQUE NOT NULL,
    email     CHARACTER VARYING(64) UNIQUE NOT NULL,
    age       INT                          NOT NULL
);

CREATE TABLE audio_tracks_storage.genre
(
    id   SERIAL PRIMARY KEY,
    name CHARACTER VARYING(64)
);

CREATE TABLE audio_tracks_storage.album
(
    id       SERIAL PRIMARY KEY,
    title    CHARACTER VARYING(128) NOT NULL,
    genre_id INT REFERENCES audio_tracks_storage.genre (id)
);

CREATE TABLE audio_tracks_storage.track
(
    id       SERIAL PRIMARY KEY,
    song     BYTEA NOT NULL UNIQUE,
    title    TEXT  NOT NULL,
    genre_id INT REFERENCES audio_tracks_storage.genre (id),
    album_id INT REFERENCES audio_tracks_storage.album (id)
);

CREATE TABLE user_storage.order
(

    id        SERIAL PRIMARY KEY,
    price     INT NOT NULL,
    client_id INT REFERENCES user_storage.client (id),
    track_id  INT REFERENCES audio_tracks_storage.track (id)
);

CREATE TABLE audio_tracks_storage.artist
(
    id   SERIAL PRIMARY KEY,
    name CHARACTER VARYING(64) UNIQUE NOT NULL
);

CREATE TABLE audio_tracks_storage.artist_albums
(
    artist_id INT REFERENCES audio_tracks_storage.artist (id),
    album_id  INT REFERENCES audio_tracks_storage.album (id),
    PRIMARY KEY (artist_id, album_id)
);

CREATE TABLE audio_tracks_storage.artist_tracks
(
    artist_id INT REFERENCES audio_tracks_storage.artist (id),
    track_id  INT REFERENCES audio_tracks_storage.track (id),
    PRIMARY KEY (artist_id, track_id)
);

CREATE TABLE audio_tracks_storage.mix
(
    id          SERIAL PRIMARY KEY,
    name        CHARACTER VARYING(64) UNIQUE NOT NULL,
    description TEXT                         NOT NULL,
    genre_id    INT REFERENCES audio_tracks_storage.genre (id)
);

CREATE TABLE audio_tracks_storage.track_mixes
(
    track_id INT REFERENCES audio_tracks_storage.track (id),
    mix_id   INT REFERENCES audio_tracks_storage.mix (id),
    PRIMARY KEY (track_id, mix_id)
);