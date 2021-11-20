package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shift {
    private Long id;
    private String departurePlace;
    private String arrivalPlace;
    private Long userId;
    private Driver driver;
    private Timestamp time;
}
