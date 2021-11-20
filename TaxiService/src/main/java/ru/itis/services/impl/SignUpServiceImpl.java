package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dto.CustomerForm;
import ru.itis.exceptions.ValidationException;
import ru.itis.models.Customer;
import ru.itis.repositories.CustomersRepository;
import ru.itis.services.PasswordEncoder;
import ru.itis.services.SignUpService;
import ru.itis.services.validation.ErrorEntity;
import ru.itis.services.validation.Validator;

import java.util.Optional;

@Service
public class SignUpServiceImpl implements SignUpService {

    private final CustomersRepository customersRepository;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    @Autowired
    public SignUpServiceImpl(CustomersRepository customersRepository, PasswordEncoder passwordEncoder, Validator validator) {
        this.customersRepository = customersRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    @Override
    public void signUp(CustomerForm form) {
        Optional<ErrorEntity> optionalError = validator.validateRegistration(form);
        if(optionalError.isPresent()) {
            throw new ValidationException(optionalError.get());
        }
        Customer customer = Customer.builder()
                .phoneNumber(form.getPhoneNumber())
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .age(form.getAge())
                .password(passwordEncoder.encode(form.getPassword()))
                .build();
        customersRepository.save(customer);
    }
}
