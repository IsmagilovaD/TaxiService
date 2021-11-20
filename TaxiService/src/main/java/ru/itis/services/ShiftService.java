package ru.itis.services;

import ru.itis.dto.CustomerDto;
import ru.itis.dto.ShiftDto;
import ru.itis.dto.ShiftForm;
import ru.itis.models.Shift;

import java.util.List;

public interface ShiftService {
    ShiftDto takeTrip(ShiftForm shiftForm);
    List<Shift> showShifts(CustomerDto userDto);
}
