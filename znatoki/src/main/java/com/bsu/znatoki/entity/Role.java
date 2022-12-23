package com.bsu.znatoki.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "roles")
public class Role {
    public static final Role USER_ROLE = new Role(1, "USER");
    public static final Role ADMIN_ROLE = new Role (2, "ADMIN");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;
}
