package ru.itis.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.models.Customer;
import ru.itis.repositories.CustomersRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;


public class CustomersRepositoryImpl implements CustomersRepository {

    private final static String SQL_INSERT = "insert into customer(first_name, last_name, age, password, phone_number, avatar_id) " +
            "values (?, ?, ?, ?, ?, ?)";
    private final static String SQL_UPDATE = "update customer set first_name = ?, last_name = ?, age = ?, password = ?, phone_number = ?, avatar_id = ? where id = ?";
    private final static String SQL_SELECT_BY_ID = "select * from customer where id = ?";
    private final static String SQL_SELECT_BY_PHONE_NUMBER = "select * from customer where phone_number = ?";
    private final static String SQL_SELECT_ALL = "select * from customer";
    private final static String SQL_UPDATE_AVATAR = "update customer set avatar_id = ? where id = ?";

    private final RowMapper<Customer> rowMapper = (row, rowNumber) ->
            Customer.builder()
                    .id(row.getLong("id"))
                    .firstName(row.getString("first_name"))
                    .lastName(row.getString("last_name"))
                    .age(row.getInt("age"))
                    .password(row.getString("password"))
                    .phoneNumber(row.getString("phone_number"))
                    .avatarId(row.getLong("avatar_id"))
                    .build();

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomersRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, rowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, rowMapper);
    }

    @Override
    public Customer save(Customer item) {
        if(item.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT, new String[]{"id"});
                statement.setString(1, item.getFirstName());
                statement.setString(2, item.getLastName());
                statement.setInt(3, item.getAge());
                statement.setString(4, item.getPassword());
                statement.setString(5, item.getPhoneNumber());
                if(item.getAvatarId() != null) {
                    statement.setLong(6, item.getAvatarId());
                } else {
                    statement.setNull(6, Types.NULL);
                }
                return statement;
            }, keyHolder);
            item.setId(keyHolder.getKey().longValue());
        } else {
            jdbcTemplate.update(SQL_UPDATE,
                    item.getFirstName(),
                    item.getLastName(),
                    item.getAge(),
                    item.getPassword(),
                    item.getPhoneNumber(),
                    item.getAvatarId(),
                    item.getId()
            );
        }
        return item;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<Customer> findByPhoneNumber(String phoneNumber) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_PHONE_NUMBER, rowMapper, phoneNumber));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateAvatar(Long customerId, Long fileId) {
        jdbcTemplate.update(SQL_UPDATE_AVATAR, fileId, customerId);
    }
}
