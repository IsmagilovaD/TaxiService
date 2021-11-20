package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.itis.models.Driver;
import ru.itis.models.Shift;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Builder
public class ShiftDto {
    private Long id;
    private String departurePlace;
    private String arrivalPlace;
    private Long userId;
    private Driver driver;
    private Timestamp time;

    public static ShiftDto from(Shift shift) {
        return ShiftDto.builder()
                .id(shift.getId())
                .arrivalPlace(shift.getArrivalPlace())
                .departurePlace(shift.getDeparturePlace())
                .userId(shift.getUserId())
                .driver(shift.getDriver())
                .time(shift.getTime())
                .build();
    }
}
