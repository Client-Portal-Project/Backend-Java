package com.projectx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Will handle the Client's information and functionality

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "ClientUsers")
public class ClientUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer clientUserId;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Client client;
    
    public ClientUser(Client client, User user) {
    	this.client = client;
    	this.user = user;
    }
}
