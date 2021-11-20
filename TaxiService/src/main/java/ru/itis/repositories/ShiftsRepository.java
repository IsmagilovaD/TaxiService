package ru.itis.repositories;

import ru.itis.dto.CustomerDto;
import ru.itis.models.Shift;
import ru.itis.repositories.base.CrudRepository;

import java.util.List;

public interface ShiftsRepository extends CrudRepository<Shift, Long> {
    List<Shift> findByCustomerId(Long id);
}
