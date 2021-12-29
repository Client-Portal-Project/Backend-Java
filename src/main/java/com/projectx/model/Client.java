package com.projectx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    @OneToMany
    @JoinTable(
            name = "ClientUser",
            joinColumns = @JoinColumn(name = "clientId"),
            inverseJoinColumns = @JoinColumn(name = "applicantId"))
    Set<User> clientUser;

    public Client(Integer clientId, String companyName) {
        this.clientId=clientId;
        this.companyName=companyName;
        this.clientUser=new HashSet<>();
    }
}
