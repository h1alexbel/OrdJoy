CREATE DATABASE ordjoy_db;

CREATE SCHEMA user_storage;

CREATE SCHEMA audio_tracks_storage;

CREATE TABLE user_storage.user_account_data
(
    id                        BIGSERIAL PRIMARY KEY,
    email                     CHARACTER VARYING(64) UNIQUE  NOT NULL,
    login                     CHARACTER VARYING(128) UNIQUE NOT NULL,
    password                  CHARACTER VARYING(128)        NOT NULL,
    discount_percentage_level INT                           NOT NULL,
    role                      CHARACTER VARYING(16)         NOT NULL,
    first_name                CHARACTER VARYING(64)         NOT NULL,
    last_name                 CHARACTER VARYING(64)         NOT NULL,
    age                       INT                           NOT NULL,
    card_number               CHARACTER VARYING(32)         NOT NULL
);

CREATE TABLE audio_tracks_storage.artist
(
    id   BIGSERIAL PRIMARY KEY,
    name CHARACTER VARYING(64) UNIQUE NOT NULL
);

CREATE TABLE audio_tracks_storage.mix
(
    id          BIGSERIAL PRIMARY KEY,
    name        CHARACTER VARYING(64) UNIQUE NOT NULL,
    description CHARACTER VARYING(512)       NOT NULL
);

CREATE TABLE audio_tracks_storage.album
(
    id        BIGSERIAL PRIMARY KEY,
    title     CHARACTER VARYING(128) NOT NULL,
    artist_id BIGINT REFERENCES audio_tracks_storage.artist (id)
);

CREATE TABLE audio_tracks_storage.track
(
    id       BIGSERIAL PRIMARY KEY,
    song_url CHARACTER VARYING(512) NOT NULL UNIQUE,
    title    CHARACTER VARYING(512) NOT NULL,
    album_id BIGINT REFERENCES audio_tracks_storage.album (id)
);

CREATE TABLE user_storage.order
(

    id              BIGSERIAL PRIMARY KEY,
    price           NUMERIC NOT NULL,
    user_account_id BIGINT REFERENCES user_storage.user_account_data (id),
    track_id        BIGINT REFERENCES audio_tracks_storage.track (id)
);

CREATE TABLE user_storage.review
(
    id              BIGSERIAL PRIMARY KEY,
    review_text     CHARACTER VARYING(512) NOT NULL,
    user_account_id BIGINT REFERENCES user_storage.user_account_data (id),
    track_id        BIGINT REFERENCES audio_tracks_storage.track (id)
);

CREATE TABLE audio_tracks_storage.track_mixes
(
    track_id BIGINT REFERENCES audio_tracks_storage.track (id),
    mix_id   BIGINT REFERENCES audio_tracks_storage.mix (id),
    PRIMARY KEY (track_id, mix_id)
);