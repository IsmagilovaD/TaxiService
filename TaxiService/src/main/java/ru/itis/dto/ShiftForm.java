package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ShiftForm {
    private Long userId;
    private String departurePlace;
    private String arrivalPlace;
}
