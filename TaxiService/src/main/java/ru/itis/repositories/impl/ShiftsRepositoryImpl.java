package ru.itis.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.dto.CustomerDto;
import ru.itis.models.Driver;
import ru.itis.models.Shift;
import ru.itis.repositories.ShiftsRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class ShiftsRepositoryImpl implements ShiftsRepository {

    private static final String SQL_INSERT = "insert into shift(customer_id, driver_id, departure_place, arrival_place, date) values (?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE = "update shift set customer_id = ?, driver_id =?, departure_place = ?, arrival_place = ?, date = ? where id = ?";

    private static final String SQL_SELECT_BY_CUSTOMER_ID = "select shift.id as shift_id, * from shift left join driver d on shift.driver_id = d.id where customer_id = ?";

    private final String SQL_SELECT_BY_ID = "select shift.id as shift_id, * from shift left join driver d on shift.driver_id = d.id where shift.id = ?";

    private final String SQL_SELECT_ALL = "select shift.id as shift_id, * from shift left join driver d on shift.driver_id = d.id";


    private final RowMapper<Shift> rowMapper = (row, rowNumber) ->
            Shift.builder()
                    .id(row.getLong("shift_id"))
                    .departurePlace(row.getString("departure_place"))
                    .arrivalPlace(row.getString("arrival_place"))
                    .userId(row.getLong("customer_id"))
                    .time(row.getTimestamp("date"))
                    .driver(Driver.builder()
                            .id(row.getLong("driver_id"))
                            .firstName(row.getString("first_name"))
                            .lastName(row.getString("last_name"))
                            .avatarId(row.getLong("avatar_id"))
                            .build())
                    .build();

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ShiftsRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Shift> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Shift> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    @Override
    public Shift save(Shift item) {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        if (item.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
                statement.setLong(1, item.getUserId());
                statement.setLong(2, item.getDriver().getId());
                statement.setString(3, item.getDeparturePlace());
                statement.setString(4, item.getArrivalPlace());
                statement.setTimestamp(5, time);
                return statement;
            }, keyHolder);
            item.setId(keyHolder.getKey().longValue());
            item.setTime(time);
        } else {
            jdbcTemplate.update(SQL_UPDATE,
                    item.getUserId(),
                    item.getDriver().getId(),
                    item.getDeparturePlace(),
                    item.getArrivalPlace(),
                    item.getTime(),
                    item.getId()
            );
        }
        return item;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Shift> findByCustomerId(Long id) {
        return jdbcTemplate.query(SQL_SELECT_BY_CUSTOMER_ID, rowMapper, id);
    }
}
