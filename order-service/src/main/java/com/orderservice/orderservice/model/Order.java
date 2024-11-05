package com.orderservice.orderservice.model;

import com.orderservice.orderservice.payload.SHIPMENT;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;
    private String userId; //it will come from user-service
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;

    @Enumerated(EnumType.STRING)
    private SHIPMENT shipmentStatus;

    private Boolean paymentStatus;

    @ElementCollection
    @CollectionTable(name = "order_products" , joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "product_id")
    private List<String> productId;

}
