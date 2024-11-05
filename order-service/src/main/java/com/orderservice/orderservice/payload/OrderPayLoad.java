package com.orderservice.orderservice.payload;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderPayLoad {

    private String orderId;
    private String userId;
    private Date createdAt;
    private Date updatedAt;
    private SHIPMENT shipmentStatus;
    private Boolean paymentStatus;

    private List<ProductPayload> products;

    public OrderPayLoad(OrderPayLoad orderPayLoad, List<ProductPayload> products) {
        this.orderId = orderPayLoad.getOrderId();
        this.userId = orderPayLoad.getUserId();
        this.createdAt = orderPayLoad.getCreatedAt();
        this.updatedAt = orderPayLoad.getUpdatedAt();
        this.shipmentStatus = orderPayLoad.getShipmentStatus();
        this.paymentStatus = orderPayLoad.getPaymentStatus();
        this.products = products;
    }


}
