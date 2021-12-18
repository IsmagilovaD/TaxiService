package ru.itis.repositories.impl;

import org.springframework.stereotype.Repository;
import ru.itis.models.Car;
import ru.itis.repositories.CarsRepository;

import java.util.List;
import java.util.Optional;


public class CarsRepositoryImpl implements CarsRepository {
    @Override
    public Optional<Car> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Car> findAll() {
        return null;
    }

    @Override
    public Car save(Car item) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
