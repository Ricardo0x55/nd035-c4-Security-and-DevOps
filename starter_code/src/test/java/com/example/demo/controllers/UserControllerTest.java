package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;
    private UserRepository userRepo=mock(UserRepository.class);
    private CartRepository cartRepo=mock(CartRepository.class);
    private BCryptPasswordEncoder encoder=mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp(){
        userController=new UserController();
        TestUtils.injectObject(userController,"userRepository",userRepo);
        TestUtils.injectObject(userController,"cartRepository",cartRepo);
        TestUtils.injectObject(userController,"bCryptPasswordEncoder",encoder);
    }

    @Test
    public void creat_user_happy_path() throws Exception {
        when(encoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");
        final ResponseEntity<User> response = userController.createUser(r);
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCodeValue());

        User u = response.getBody();
        Assert.assertNotNull(u);
        Assert.assertEquals(0, u.getId());
        Assert.assertEquals("test", u.getUsername());
        Assert.assertEquals("thisIsHashed", u.getPassword());
    }

    @Test
    public void verify_create_user_bad_password(){
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("Martin");
        r.setPassword("Pass");
        r.setConfirmPassword("Pass");
        ResponseEntity<User> response= userController.createUser(r);
        Assert.assertNotNull(response);
        Assert.assertEquals(400,response.getStatusCodeValue());
    }

    @Test
    public void verify_create_user_diferent_pass_confpass(){
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("Martin");
        r.setPassword("Password");
        r.setConfirmPassword("pASSWORD");
        ResponseEntity<User> response = userController.createUser(r);
        Assert.assertNotNull(response);
        Assert.assertEquals(400,response.getStatusCodeValue());
    }

    @Test
    public void verify_findById(){
        User user=createTestUser();
        when(userRepo.findById(user.getId())).thenReturn(java.util.Optional.of(user));
        ResponseEntity<User> byId = userController.findById(user.getId());
        Assert.assertNotNull(byId);
        Assert.assertEquals(200,byId.getStatusCodeValue());
        Assert.assertNotNull(byId.getBody());
        Assert.assertEquals(user.getId(),byId.getBody().getId());
        Assert.assertEquals(user.getUsername(),byId.getBody().getUsername());
    }

    @Test
    public void verify_findByUserName(){
        User user=createTestUser();
        when(userRepo.findByUsername(user.getUsername())).thenReturn(user);
        ResponseEntity<User> byUserName = userController.findByUserName(user.getUsername());
        Assert.assertNotNull(byUserName);
        Assert.assertEquals(200,byUserName.getStatusCodeValue());
        Assert.assertNotNull(byUserName.getBody());
        Assert.assertEquals(user.getId(),byUserName.getBody().getId());
        Assert.assertEquals(user.getUsername(),byUserName.getBody().getUsername());
    }

    private User createTestUser(){
        User user=new User();
        user.setId(1);
        user.setUsername("Ricardo");
        return user;
    }

}
