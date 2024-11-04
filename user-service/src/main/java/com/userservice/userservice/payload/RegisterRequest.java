package com.userservice.userservice.payload;

import com.userservice.userservice.model.Address;
import com.userservice.userservice.model.Role;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String email;
    private String firstName;
    private String lastName;
    private String passWord;
    private Set<Role> roles;
    private Address address;
}
