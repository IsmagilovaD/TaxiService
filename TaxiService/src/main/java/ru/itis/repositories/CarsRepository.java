package ru.itis.repositories;

import ru.itis.models.Car;
import ru.itis.repositories.base.CrudRepository;

public interface CarsRepository extends CrudRepository<Car,Long> {
}
