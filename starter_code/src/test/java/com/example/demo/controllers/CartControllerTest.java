package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.mock;

public class CartControllerTest {

    private CartController cartController;
    private UserRepository userRepo=mock(UserRepository.class);
    private CartRepository cartRepo=mock(CartRepository.class);
    private ItemRepository itemRepo=mock(ItemRepository.class);

    @Before
    public void setUp(){
        cartController=new CartController();
        TestUtils.injectObject(cartController,"userRepository",userRepo);
        TestUtils.injectObject(cartController,"cartRepository",cartRepo);
        TestUtils.injectObject(cartController,"itemRepository",itemRepo);
    }

    @Test
    public void verify_addToCart(){
        User user=createUser();
        Cart cart=new Cart();
        user.setCart(cart);
        cart.setUser(user);
        Item item=createItem();
        ModifyCartRequest request=createRequest(user.getUsername(),item.getId(),1);
        Mockito.when(userRepo.findByUsername(request.getUsername())).thenReturn(user);
        Mockito.when(itemRepo.findById(request.getItemId())).thenReturn(java.util.Optional.of(item));
        ResponseEntity<Cart> response = cartController.addTocart(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(200,response.getStatusCodeValue());
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(item,response.getBody().getItems().get(0));
        Assert.assertEquals(user,response.getBody().getUser());
    }

    @Test
    public void verify_addToCart_user_NotFound(){
        User user=createUser();
        Cart cart=new Cart();
        user.setCart(cart);
        cart.setUser(user);
        Item item=createItem();
        ModifyCartRequest request=createRequest(user.getUsername(),item.getId(),1);
        Mockito.when(userRepo.findByUsername(request.getUsername())).thenReturn(null);
        Mockito.when(itemRepo.findById(request.getItemId())).thenReturn(java.util.Optional.of(item));
        ResponseEntity<Cart> response = cartController.addTocart(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(404,response.getStatusCodeValue());
    }

    @Test
    public void verify_addToCart_item_NotFound(){
        User user=createUser();
        Cart cart=new Cart();
        user.setCart(cart);
        cart.setUser(user);
        Item item=createItem();
        ModifyCartRequest request=createRequest(user.getUsername(),item.getId(),1);
        Mockito.when(userRepo.findByUsername(request.getUsername())).thenReturn(user);
        Mockito.when(itemRepo.findById(request.getItemId())).thenReturn(Optional.empty());
        ResponseEntity<Cart> response = cartController.addTocart(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(404,response.getStatusCodeValue());
    }

    @Test
    public void verify_removeFromCart(){
        User user=createUser();
        Cart cart=new Cart();
        Item item=createItem();
        cart.setUser(user);
        cart.addItem(item);
        user.setCart(cart);
        ModifyCartRequest request=createRequest(user.getUsername(),item.getId(),1);
        Mockito.when(userRepo.findByUsername(request.getUsername())).thenReturn(user);
        Mockito.when(itemRepo.findById(request.getItemId())).thenReturn(java.util.Optional.of(item));
        ResponseEntity<Cart> response = cartController.removeFromcart(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(200,response.getStatusCodeValue());
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(Collections.emptyList(),response.getBody().getItems());
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

    private ModifyCartRequest createRequest(String username, Long itemId, int quantity){
        ModifyCartRequest request=new ModifyCartRequest();
        request.setUsername(username);
        request.setItemId(itemId);
        request.setQuantity(quantity);
        return request;
    }

}
