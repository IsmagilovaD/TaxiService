package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dto.CustomerForm;
import ru.itis.models.Customer;
import ru.itis.repositories.CustomersRepository;
import ru.itis.services.validation.ErrorEntity;
import ru.itis.services.validation.Validator;


import java.util.Optional;


public class ValidatorImpl implements Validator {
    private final CustomersRepository customersRepository;

    public ValidatorImpl(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    @Override
    public Optional<ErrorEntity> validateRegistration(CustomerForm form) {
        if(form.getPhoneNumber() == null) {
            return Optional.of(ErrorEntity.INVALID_EMAIL);
        } else if(customersRepository.findByPhoneNumber(form.getPhoneNumber()).isPresent()) {
            return Optional.of(ErrorEntity.EMAIL_ALREADY_TAKEN);
        }
        return Optional.empty();
    }
}
