package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.itis.models.Customer;

@Data
@AllArgsConstructor
@Builder
public class CustomerDto {
    private Long id;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private Integer age;
    private Long avatarId;

    public static CustomerDto from(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .phoneNumber(customer.getPhoneNumber())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .avatarId(customer.getAvatarId())
                .build();
    }
}
