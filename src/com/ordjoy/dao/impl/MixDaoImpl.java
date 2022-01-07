package com.ordjoy.dao.impl;

import com.ordjoy.dao.MixDao;
import com.ordjoy.util.ConnectionManager;
import com.ordjoy.entity.*;
import com.ordjoy.exception.DaoException;
import com.ordjoy.filter.DefaultFilter;
import com.ordjoy.filter.MixFilter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ordjoy.util.ExceptionMessageUtils.*;
import static java.util.stream.Collectors.joining;

public class MixDaoImpl implements MixDao {

    private static final MixDaoImpl INSTANCE = new MixDaoImpl();

    private MixDaoImpl() {

    }

    public static MixDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final String LIMIT_OFFSET = """
            LIMIT ?
            OFFSET ?
            """;

    private static final String SQL_SAVE_MIX = """
            INSERT INTO audio_tracks_storage.mix (name, description, genre_id)
            VALUES (?,?,(SELECT id FROM audio_tracks_storage.genre WHERE genre.name = ?));
            """;

    private static final String SQL_FIND_GENRE_ID = """
            SELECT id FROM audio_tracks_storage.genre
            WHERE name LIKE ?
            """;

    private static final String SQL_FIND_MIX_BY_ID = """
            SELECT mix.id          AS mix_id,
                   mix.name        AS mix_name,
                   mix.description AS mix_description,
                   g.id            AS g_id,
                   g.name          AS genre_name
            FROM audio_tracks_storage.mix mix
                     JOIN audio_tracks_storage.genre g ON g.id = mix.genre_id
            WHERE mix.id = ?
            """;

    private static final String SQL_FIND_MIX_BY_NAME = """
            SELECT mix.id          AS mix_id,
                   mix.name        AS mix_name,
                   mix.description AS mix_description,
                   g.id            AS g_id,
                   g.name          AS genre_name
            FROM audio_tracks_storage.mix mix
                     JOIN audio_tracks_storage.genre g ON g.id = mix.genre_id
            WHERE mix.name LIKE ?
            """;

    private static final String SQL_DELETE_BY_ID = """
            DELETE FROM audio_tracks_storage.mix
            WHERE id = ?
            """;

    private static final String SQL_UPDATE_MIX = """
            UPDATE audio_tracks_storage.mix
            SET name = ?,
                description = ?,
                genre_id = ?
            WHERE id = ?
            """;

    private static final String SQL_FIND_MIX_BY_GENRE_ID = """
            SELECT mix.id          AS mix_id,
                   mix.name        AS mix_name,
                   mix.description AS mix_description,
                   g.id            AS g_id,
                   g.name          AS genre_name
            FROM audio_tracks_storage.mix mix
                     JOIN audio_tracks_storage.genre g ON g.id = mix.genre_id
            WHERE g.id = ?
            """;

    private static final String SQL_FIND_MIX_BY_GENRE_NAME = """
            SELECT mix.id          AS mix_id,
                   mix.name        AS mix_name,
                   mix.description AS mix_description,
                   g.id            AS g_id,
                   g.name          AS genre_name
            FROM audio_tracks_storage.mix mix
                     JOIN audio_tracks_storage.genre g ON g.id = mix.genre_id
            WHERE g.name LIKE ?
            """;

    private static final String SQL_FIND_ALL_MIXES = """
            SELECT mix.id AS mix_id, mix.name AS mix_name, mix.description AS mix_description, g.id AS g_id, g.name AS genre_name
            FROM audio_tracks_storage.mix mix
                    LEFT JOIN audio_tracks_storage.genre g ON g.id = mix.genre_id
            """;

    @Override
    public Mix saveMix(Mix mix) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement saveMixStatement = connection.prepareStatement(SQL_SAVE_MIX, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement selectIdStatement = connection.prepareStatement(SQL_FIND_GENRE_ID)) {
            selectIdStatement.setString(1, mix.getGenre().getName().getGenreName());
            ResultSet resultSet = selectIdStatement.executeQuery();
            if (resultSet.next()) {
                long genreId = resultSet.getLong("id");
                mix.getGenre().setId(genreId);
                saveMixStatement.setString(1, mix.getName());
                saveMixStatement.setString(2, mix.getDescription());
                saveMixStatement.setString(3, mix.getGenre().getName().getGenreName());
                saveMixStatement.executeUpdate();
                ResultSet generatedKeys = saveMixStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong("id");
                    mix.setId(id);
                }
            }
            return mix;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<Mix> findAll(MixFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.genreType() != null) {
            whereSql.add("g.name LIKE ?");
            parameters.add("%" + filter.genreType() + "%");
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String where = whereSql.stream()
                .collect(joining(" AND ", " WHERE ", " LIMIT ?  OFFSET ? "));
        String sql = SQL_FIND_ALL_MIXES + where;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findAllStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                findAllStatement.setObject(i + 1, parameters.get(i));
            }
            ResultSet resultSet = findAllStatement.executeQuery();
            List<Mix> mixes = new ArrayList<>();
            while (resultSet.next()) {
                mixes.add(buildMix(resultSet));
            }
            return mixes;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<List<Mix>> findMixesByGenreName(String genreName, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_MIX_BY_GENRE_NAME + LIMIT_OFFSET;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findMixByGenreName = connection.prepareStatement(sql)) {
            findMixByGenreName.setString(1, genreName);
            findMixByGenreName.setObject(2, (filter.limit()));
            findMixByGenreName.setObject(3, filter.offset());
            ResultSet resultSet = findMixByGenreName.executeQuery();
            List<Mix> mixes = new ArrayList<>();
            while (resultSet.next()) {
                mixes.add(buildMix(resultSet));
            }
            return Optional.of(mixes);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<List<Mix>> findMixesByGenreId(Long genreId, DefaultFilter filter) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String sql = SQL_FIND_MIX_BY_GENRE_ID + LIMIT_OFFSET;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findMixByGenreName = connection.prepareStatement(sql)) {
            findMixByGenreName.setLong(1, genreId);
            findMixByGenreName.setObject(2, (filter.limit()));
            findMixByGenreName.setObject(3, filter.offset());
            ResultSet resultSet = findMixByGenreName.executeQuery();
            List<Mix> mixes = new ArrayList<>();
            while (resultSet.next()) {
                mixes.add(buildMix(resultSet));
            }
            return Optional.of(mixes);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<Mix> findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findByIdStatement = connection.prepareStatement(SQL_FIND_MIX_BY_ID)) {
            findByIdStatement.setLong(1, id);
            ResultSet resultSet = findByIdStatement.executeQuery();
            Mix mix = null;
            if (resultSet.next()) {
                mix = buildMix(resultSet);
            }
            return Optional.ofNullable(mix);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public Optional<Mix> findByName(String name) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement findByNameStatement = connection.prepareStatement(SQL_FIND_MIX_BY_NAME)) {
            findByNameStatement.setString(1, name);
            ResultSet resultSet = findByNameStatement.executeQuery();
            Mix mix = null;
            if (resultSet.next()) {
                mix = buildMix(resultSet);
            }
            return Optional.ofNullable(mix);
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void update(Mix mix) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement updateMixStatement = connection.prepareStatement(SQL_UPDATE_MIX)) {
            updateMixStatement.setString(1, mix.getName());
            updateMixStatement.setString(2, mix.getDescription());
            updateMixStatement.setLong(3, mix.getGenre().getId());
            updateMixStatement.setLong(4, mix.getId());
            updateMixStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement deleteByIdStatement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            deleteByIdStatement.setLong(1, id);
            return deleteByIdStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(DAO_LAYER_EXCEPTION_MESSAGE, e);
        }
    }

    private Mix buildMix(ResultSet resultSet) throws SQLException {
        Genre genre = buildGenre(resultSet);
        return new Mix(
                resultSet.getLong("mix_id"),
                resultSet.getString("mix_name"),
                resultSet.getString("mix_description"),
                genre
        );
    }

    private Genre buildGenre(ResultSet resultSet) throws SQLException {
        return new Genre(
                resultSet.getLong("g_id"),
                GenreType.valueOf(resultSet.getString("genre_name"))
        );
    }
}