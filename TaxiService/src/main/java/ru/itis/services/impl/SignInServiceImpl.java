package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dto.CustomerDto;
import ru.itis.dto.CustomerForm;
import ru.itis.exceptions.ValidationException;
import ru.itis.models.Customer;
import ru.itis.repositories.CustomersRepository;
import ru.itis.services.PasswordEncoder;
import ru.itis.services.SignInService;
import ru.itis.services.validation.ErrorEntity;

@Service
public class SignInServiceImpl implements SignInService {

    private final CustomersRepository customersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignInServiceImpl(CustomersRepository customersRepository, PasswordEncoder passwordEncoder) {
        this.customersRepository = customersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CustomerDto signIn(CustomerForm customerForm) {
        Customer customer = customersRepository.findByPhoneNumber(customerForm.getPhoneNumber())
                .orElseThrow(() -> new ValidationException(ErrorEntity.NOT_FOUND));
        if (!passwordEncoder.matches(customerForm.getPassword(), customer.getPassword())) {
            throw new ValidationException(ErrorEntity.INCORRECT_PASSWORD);
        }
        return CustomerDto.from(customer);
    }
}
