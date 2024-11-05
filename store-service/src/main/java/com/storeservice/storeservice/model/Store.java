package com.storeservice.storeservice.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "store_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String storeId;

    @NotNull
    private String storeName;
    
    @OneToOne
    private Location location;
    private Integer staff;

    @ElementCollection
    @CollectionTable(name = "product_available", joinColumns = @JoinColumn(name = "store_id"))
    @Column(name = "product_id")
    private List<String> productId; //inventory


    @ElementCollection
    @CollectionTable(name = "order_place", joinColumns = @JoinColumn(name = "store_id"))
    @Column(name = "order_id")
    private List<String> orderId;

    @ElementCollection
    @CollectionTable(name = "return_item", joinColumns = @JoinColumn(name = "store_id"))
    @Column(name = "return_id")
    private List<String> returnProductId;



}
