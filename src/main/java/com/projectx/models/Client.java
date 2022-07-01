package com.projectx.models;

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
@Table(name = "clients")
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
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    Set<User> clientUser;

    public Client(Integer clientId, String companyName) {
        this.clientId=clientId;
        this.companyName=companyName;
        this.clientUser=new HashSet<>();
    }

    public Client(String company_name) {
        this.companyName=company_name;
        this.clientUser=new HashSet<>();
    }

    public Client(Integer client_id) {
        this.clientId = client_id;
    }
}
