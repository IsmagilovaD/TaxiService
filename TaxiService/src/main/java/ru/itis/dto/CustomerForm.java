package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CustomerForm {
    private Long id;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private Integer age;
    private String password;
    private Long avatarId;
}
