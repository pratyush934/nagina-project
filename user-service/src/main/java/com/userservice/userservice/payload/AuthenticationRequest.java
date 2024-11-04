package com.userservice.userservice.payload;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AuthenticationRequest {

    private String email;
    private String password;
}
