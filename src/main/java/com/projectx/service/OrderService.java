package com.projectx.service;

import com.projectx.exceptions.ResourceDoesNotExist;
import com.projectx.model.Client;
import com.projectx.model.Order;
import com.projectx.model.Owner;
import com.projectx.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepo orderRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo){
        this.orderRepo = orderRepo;
    }


    public List<Order> getAll(){return orderRepo.findAll();}

    public List<Order> getListByClient(Client client){
        return orderRepo.getListByClient(client);
    }

    public List<Order> getListByOwner(Owner owner){
        return orderRepo.findAll().stream().filter(order -> order.getClient().getOwner().getOwnerId().equals(owner.getOwnerId())).collect(Collectors.toList());
    }


    public Order createOrSave(Order order){return orderRepo.save(order);}

    public Order getById(Integer id){
        return orderRepo.findById(id).orElseThrow(()->new ResourceDoesNotExist("No Order with id:" + id));
    }


}
