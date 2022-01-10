package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

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

    @Test
    public void verify_submit(){
        User user=createUser();
        Item item=createItem();
        Cart cart=new Cart();
        cart.setUser(user);
        cart.addItem(item);
        user.setCart(cart);
        Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(user);
        ResponseEntity<UserOrder> response = orderController.submit(user.getUsername());
        Assert.assertNotNull(response);
        Assert.assertEquals(200,response.getStatusCodeValue());
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(user,response.getBody().getUser());
        Assert.assertEquals(item,response.getBody().getItems().get(0));
    }

    @Test
    public void verify_submit_user_notFound(){
        User user=createUser();
        Item item=createItem();
        Cart cart=new Cart();
        cart.setUser(user);
        cart.addItem(item);
        user.setCart(cart);
        Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(null);
        ResponseEntity<UserOrder> response = orderController.submit(user.getUsername());
        Assert.assertNotNull(response);
        Assert.assertEquals(404,response.getStatusCodeValue());
    }

    @Test
    public void verify_getOrdersForUser(){
        User user=createUser();
        Item item=createItem();
        Cart cart=new Cart();
        cart.setUser(user);
        cart.addItem(item);
        user.setCart(cart);
        UserOrder order=UserOrder.createFromCart(cart);
        Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(orderRepo.findByUser(user)).thenReturn(Collections.singletonList(order));
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser(user.getUsername());
        Assert.assertNotNull(response);
        Assert.assertEquals(200,response.getStatusCodeValue());
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(Collections.singletonList(order),response.getBody());
    }

    @Test
    public void verify_getOrdersForUser_user_notFound(){
        User user=createUser();
        Item item=createItem();
        Cart cart=new Cart();
        cart.setUser(user);
        cart.addItem(item);
        user.setCart(cart);
        Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(null);
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser(user.getUsername());
        Assert.assertNotNull(response);
        Assert.assertEquals(404,response.getStatusCodeValue());
    }

    private User createUser(){
        User user=new User();
        user.setUsername("Ricardo");
        return user;
    }

    private Item createItem(){
        Item item=new Item();
        item.setId(1L);
        item.setPrice(BigDecimal.valueOf(2.99));
        return item;
    }

}
