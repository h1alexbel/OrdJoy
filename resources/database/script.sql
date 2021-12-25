CREATE DATABASE ordjoy_db;

CREATE SCHEMA user_storage;

CREATE SCHEMA audio_tracks_storage;

CREATE TABLE user_storage.user_account
(
    id       SERIAL PRIMARY KEY,
    email    CHARACTER VARYING(64) UNIQUE                                                                     NOT NULL,
    login    CHARACTER VARYING(128)                                                                           NOT NULL UNIQUE,
    password CHARACTER VARYING(128)                                                                           NOT NULL,
    role     CHARACTER VARYING CHECK (role = 'Admin' OR role = 'Client' OR role = 'admin' OR role = 'client') NOT NULL
);

CREATE TABLE user_storage.user_data
(
    id              SERIAL PRIMARY KEY,
    first_name      CHARACTER VARYING(64) NOT NULL,
    last_name       CHARACTER VARYING(64) NOT NULL,
    age             INT                   NOT NULL,
    user_account_id INT REFERENCES user_storage.user_account (id)
);

ALTER TABLE user_storage.user_account
    ADD CONSTRAINT user_data_id FOREIGN KEY (id) REFERENCES user_storage.user_data;

CREATE TABLE audio_tracks_storage.genre
(
    id   SERIAL PRIMARY KEY,
    name CHARACTER VARYING(64) UNIQUE
);

CREATE TABLE audio_tracks_storage.album
(
    id       SERIAL PRIMARY KEY,
    title    CHARACTER VARYING(128) NOT NULL,
    genre_id INT REFERENCES audio_tracks_storage.genre (id)
);

CREATE TABLE audio_tracks_storage.mix
(
    id          SERIAL PRIMARY KEY,
    name        CHARACTER VARYING(64) UNIQUE NOT NULL,
    description TEXT                         NOT NULL,
    genre_id    INT REFERENCES audio_tracks_storage.genre (id)
);

CREATE TABLE audio_tracks_storage.track
(
    id       SERIAL PRIMARY KEY,
    song_url TEXT NOT NULL UNIQUE,
    title    TEXT NOT NULL,
    genre_id INT REFERENCES audio_tracks_storage.genre (id),
    album_id INT REFERENCES audio_tracks_storage.album (id),
    mix_id   INT REFERENCES audio_tracks_storage.mix (id)
);

CREATE TABLE user_storage.order
(

    id              SERIAL PRIMARY KEY,
    price           NUMERIC NOT NULL,
    card_number     BIGINT  NOT NULL,
    user_account_id INT REFERENCES user_storage.user_account (id),
    track_id        INT REFERENCES audio_tracks_storage.track (id)
);

CREATE TABLE audio_tracks_storage.artist
(
    id   SERIAL PRIMARY KEY,
    name CHARACTER VARYING(64) UNIQUE NOT NULL
);

CREATE TABLE audio_tracks_storage.artist_tracks
(
    artist_id INT REFERENCES audio_tracks_storage.artist (id),
    track_id  INT REFERENCES audio_tracks_storage.track (id),
    PRIMARY KEY (artist_id, track_id)
);

CREATE TABLE audio_tracks_storage.track_mixes
(
    track_id INT REFERENCES audio_tracks_storage.track (id),
    mix_id   INT REFERENCES audio_tracks_storage.mix (id),
    PRIMARY KEY (track_id, mix_id)
);