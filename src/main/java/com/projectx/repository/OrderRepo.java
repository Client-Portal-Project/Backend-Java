package com.projectx.repository;

import com.projectx.model.Client;
import com.projectx.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface OrderRepo extends JpaRepository<Order,Integer> {
    List<Order> getListByClient(Client client);

}
