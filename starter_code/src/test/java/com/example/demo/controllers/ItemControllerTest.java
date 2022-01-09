package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ItemControllerTest {

    private ItemController itemController;
    private ItemRepository itemRepo= Mockito.mock(ItemRepository.class);

    @Before
    public void setUp(){
        itemController=new ItemController();
        TestUtils.injectObject(itemController,"itemRepository",itemRepo);
    }

    @Test
    public  void verify_getItems(){
        Item item=createItem();
        Mockito.when(itemRepo.findAll()).thenReturn(Collections.singletonList(item));
        ResponseEntity<List<Item>> response = itemController.getItems();
        Assert.assertNotNull(response);
        Assert.assertEquals(200,response.getStatusCodeValue());
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(item,response.getBody().get(0));
        Assert.assertEquals(item.getId(),response.getBody().get(0).getId());
        Assert.assertEquals(item.getName(),response.getBody().get(0).getName());
        Assert.assertEquals(item.getPrice(),response.getBody().get(0).getPrice());
        Assert.assertEquals(item.getDescription(),response.getBody().get(0).getDescription());
    }

    @Test
    public void verify_getItemById(){
        Item item=createItem();
        Mockito.when(itemRepo.findById(item.getId())).thenReturn(Optional.of(item));
        ResponseEntity<Item> itemById = itemController.getItemById(item.getId());
        Assert.assertNotNull(itemById);
        Assert.assertEquals(200,itemById.getStatusCodeValue());
        Assert.assertNotNull(itemById.getBody());
        Assert.assertEquals(item.getId(),itemById.getBody().getId());
        Assert.assertEquals(item.getName(),itemById.getBody().getName());
        Assert.assertEquals(item.getPrice(),itemById.getBody().getPrice());
        Assert.assertEquals(item.getDescription(),itemById.getBody().getDescription());
    }

    @Test
    public void verify_getItemById_NotFound(){
        Item item=createItem();
        Mockito.when(itemRepo.findById(item.getId())).thenReturn(Optional.empty());
        ResponseEntity<Item> itemById = itemController.getItemById(item.getId());
        Assert.assertNotNull(itemById);
        Assert.assertEquals(404,itemById.getStatusCodeValue());
    }

    @Test
    public void verify_getItemsByName() {
        Item item = createItem();
        Mockito.when(itemRepo.findByName(item.getName())).thenReturn(Collections.singletonList(item));
        ResponseEntity<List<Item>> itemsByName = itemController.getItemsByName(item.getName());
        Assert.assertNotNull(itemsByName);
        Assert.assertEquals(200,itemsByName.getStatusCodeValue());
        Assert.assertNotNull(itemsByName.getBody());
        Assert.assertEquals(item,itemsByName.getBody().get(0));
        Assert.assertEquals(item.getId(),itemsByName.getBody().get(0).getId());
        Assert.assertEquals(item.getName(),itemsByName.getBody().get(0).getName());
        Assert.assertEquals(item.getPrice(),itemsByName.getBody().get(0).getPrice());
        Assert.assertEquals(item.getDescription(),itemsByName.getBody().get(0).getDescription());
    }

    @Test
    public void verify_getItemsByName_NotFound(){
        Item item = createItem();
        Mockito.when(itemRepo.findByName(item.getName())).thenReturn(Collections.emptyList());
        ResponseEntity<List<Item>> itemsByName = itemController.getItemsByName(item.getName());
        Assert.assertNotNull(itemsByName);
        Assert.assertEquals(404,itemsByName.getStatusCodeValue());
    }

    private Item createItem(){
        Item item=new Item();
        item.setId(1L);
        item.setName("Round Widget");
        item.setPrice(BigDecimal.valueOf(2.99));
        item.setDescription("A widget that is round");
        return item;
    }

}
