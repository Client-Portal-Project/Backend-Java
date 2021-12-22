package com.projectx.controller;

import com.projectx.model.Client;
import com.projectx.model.Order;
import com.projectx.service.ClientService;
import com.projectx.service.OrderService;
import com.projectx.utility.CrossOriginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
@CrossOrigin(value = CrossOriginUtil.CROSS_ORIGIN_VALUE, allowCredentials = "true")
public class OrderController {
    private final OrderService orderService;
    private final ClientService clientService;

    @Autowired
    public OrderController(OrderService orderService, ClientService clientService){
        this.orderService = orderService;
        this.clientService = clientService;
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getALL(){
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        return ResponseEntity.ok(orderService.getById(id));
    }

    @PostMapping("/orders/client/{id}")
    public ResponseEntity<?> create(@RequestBody Order order, @PathVariable Integer id){
        Client client = clientService.findClientById(id);
        order.setClient(client);
        Order newOrder = orderService.createOrSave(order);
        client.getOrders().add(newOrder);
        clientService.createClient(client);
        return ResponseEntity.ok(newOrder);
    }


}
