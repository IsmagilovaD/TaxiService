package ru.itis.services;

import ru.itis.dto.CustomerForm;
import ru.itis.models.Customer;

public interface SignUpService {
    void signUp(CustomerForm form);
}
