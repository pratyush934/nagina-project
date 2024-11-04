package com.userservice.userservice.payload;

import com.userservice.userservice.model.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthenticationResponse {

    private String token;
    private String email;
    private String firstName;
    private String lastName;
    private Address address;

}
