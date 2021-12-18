package ru.itis.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dto.CustomerDto;
import ru.itis.dto.ShiftDto;
import ru.itis.dto.ShiftForm;
import ru.itis.exceptions.NotFoundException;
import ru.itis.models.Driver;
import ru.itis.models.Shift;
import ru.itis.repositories.DriversRepository;
import ru.itis.repositories.ShiftsRepository;
import ru.itis.services.ShiftService;

import java.util.List;
import java.util.Optional;


public class ShiftServiceImpl implements ShiftService {
    private final ShiftsRepository shiftsRepository;
    private final DriversRepository driversRepository;

    public ShiftServiceImpl(ShiftsRepository shiftsRepository, DriversRepository driversRepository) {
        this.shiftsRepository = shiftsRepository;
        this.driversRepository = driversRepository;
    }


    @Override
    public ShiftDto takeTrip(ShiftForm shiftForm) {
        Shift shift = Shift.builder()
                .departurePlace(shiftForm.getDeparturePlace())
                .arrivalPlace(shiftForm.getArrivalPlace())
                .userId(shiftForm.getUserId())
                .driver(generateDriver(3))
                .build();
        shift = shiftsRepository.save(shift);
        return ShiftDto.from(shift);
    }

    @Override
    public List<Shift> showShifts(CustomerDto customerDto) {
        return shiftsRepository.findByCustomerId(customerDto.getId());
    }

    private Driver generateDriver(int max) {
        Double rnd = Math.random() * ++max;
        Optional<Driver> driver = driversRepository.findById(rnd.longValue());
        if (driver.isPresent()) return driver.get();
        else throw new NotFoundException("");
    }

}
