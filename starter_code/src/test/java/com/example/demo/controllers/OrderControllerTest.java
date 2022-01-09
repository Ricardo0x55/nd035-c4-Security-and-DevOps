package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;

import static org.mockito.Mockito.mock;

public class OrderControllerTest {

    private OrderController orderController;
    private UserRepository userRepo=mock(UserRepository.class);
    private OrderRepository orderRepo=mock(OrderRepository.class);

    @Before
    public void setUp(){
        orderController=new OrderController();
        TestUtils.injectObject(orderController,"userRepository",userRepo);
        TestUtils.injectObject(orderController,"orderRepository",orderRepo);
    }



}
