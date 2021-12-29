package com.projectx.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Will handle the Client's information and functionality

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer clientId;
    @Column
    private String companyName;
}
