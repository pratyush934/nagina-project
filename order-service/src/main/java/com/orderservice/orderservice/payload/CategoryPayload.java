package com.orderservice.orderservice.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CategoryPayload {

    private String variantId;
    private String color;
    private String material;
    private String size;
}
