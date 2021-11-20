package ru.itis.services.validation;

import org.springframework.stereotype.Service;
import ru.itis.dto.CustomerForm;

import java.util.Optional;

public interface Validator {
    Optional<ErrorEntity> validateRegistration(CustomerForm form);
}
