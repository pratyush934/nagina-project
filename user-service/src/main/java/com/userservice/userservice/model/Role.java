package com.userservice.userservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "role")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String roleId;

    @NotNull
    private String roleName;

    private String message;

    @ManyToOne
    private User user;

    @JsonCreator
    public static Role fromString(String value) {
        Role role = new Role();
        role.setRoleName(value);
        return role;
    }

}
