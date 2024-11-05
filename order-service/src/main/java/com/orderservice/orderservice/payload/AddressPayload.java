package com.orderservice.orderservice.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AddressPayload {

    private String userId;
    private String pinCode;
    private String address;
    private String city;
    private String state;
    private String landMark;

}
