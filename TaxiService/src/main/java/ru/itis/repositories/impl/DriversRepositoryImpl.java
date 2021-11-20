package ru.itis.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.models.Driver;
import ru.itis.repositories.DriversRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class DriversRepositoryImpl implements DriversRepository {


    private static final String SQL_SELECT_BY_ID = "select * from driver where id = ?";
    private static final String SQL_SELECT_ALL = "select * from driver";
    private static final String SQL_INSERT = "insert into driver (first_name, last_name, avatar_id) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE = "update driver set first_name = ?, last_name = ?, avatar_id = ? where id = ?";

    private final RowMapper<Driver> rowMapper = (row, rowNumber) ->
            Driver.builder()
                    .id(row.getLong("id"))
                    .firstName(row.getString("first_name"))
                    .lastName(row.getString("last_name"))
                    .avatarId(row.getLong("avatar_id"))
                    .build();

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DriversRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Driver> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Driver> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    @Override
    public Driver save(Driver item) {
        if (item.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
                statement.setString(1, item.getFirstName());
                statement.setString(2, item.getLastName());
                statement.setLong(3, item.getAvatarId());
                return statement;
            }, keyHolder);
            item.setId(keyHolder.getKey().longValue());
        } else {
            jdbcTemplate.update(SQL_UPDATE,
                    item.getFirstName(),
                    item.getLastName(),
                    item.getAvatarId(),
                    item.getId()
            );
        }
        return item;
    }

    @Override
    public void delete(Long id) {

    }
}
