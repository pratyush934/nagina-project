package com.storeservice.storeservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Entity
@Table(name = "location_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String locationId;

    @NotNull
    private String zipCode;
    @NotNull
    private String address1;
    @NotNull
    private String street;
    @NotNull
    private String city;
    @NotNull
    private String state;

    @OneToOne
    private Store store;
}
