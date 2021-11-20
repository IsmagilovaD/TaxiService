package ru.itis.services;

import ru.itis.dto.CustomerDto;
import ru.itis.dto.CustomerForm;

public interface SignInService {
    CustomerDto signIn(CustomerForm signInForm);
}
