package ru.itis.repositories;

import ru.itis.models.Customer;
import ru.itis.repositories.base.CrudRepository;

import java.util.Optional;

public interface CustomersRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findByPhoneNumber(String phoneNumber);
    void updateAvatar(Long customerId, Long fileId);
}
