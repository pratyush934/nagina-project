package com.orderservice.orderservice.payload;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductPayload {

    private String productId;
    private String secondaryId;
    private String productName;
    private String description;
    private Double price;
    private Integer stock;

    private List<ProductVariantPayload> productVariantList;
    private List<CategoryPayload> categories;
}
