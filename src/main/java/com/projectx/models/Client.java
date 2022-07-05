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
    @Column(name="client_id")
    private int clientId;
    @Column(name="company_name")
    private String companyName;
    @OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name="user_id")
    Set<User> user;

    public Client(int clientId, String companyName) {
        this.clientId=clientId;
        this.companyName=companyName;
        this.user=new HashSet<>();
    }

    public Client(String company_name) {
        this.companyName=company_name;
        this.user=new HashSet<>();
    }

    public Client(int client_id) {
        this.clientId = client_id;
    }
}
