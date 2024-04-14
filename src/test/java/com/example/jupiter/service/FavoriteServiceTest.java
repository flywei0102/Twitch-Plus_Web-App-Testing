package com.example.jupiter.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.jupiter.service.FavoriteService;
import com.example.jupiter.dao.FavoriteDao;
import com.example.jupiter.entity.db.Item;
import com.example.jupiter.entity.db.ItemType;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class FavoriteServiceTest {

    @Mock
    private FavoriteDao favoriteDao;

    @InjectMocks
    private FavoriteService favoriteService;

    @Test
    public void testSetFavoriteItem() {
        String userId = "user123";
        Item item = new Item();
        item.setId("item123");
        favoriteService.setFavoriteItem(userId, item);
        verify(favoriteDao).setFavoriteItem(userId, item);
    }

    @Test
    public void testUnsetFavoriteItem() {
        String userId = "user123";
        String itemId = "item123";
        favoriteService.unsetFavoriteItem(userId, itemId);
        verify(favoriteDao).unsetFavoriteItem(userId, itemId);
    }

    @Test
    public void testGetFavoriteItems() {
        String userId = "user123";
        Set<Item> mockItems = new HashSet<>();
        Item item1 = new Item();
        item1.setId("item1");
        item1.setType(ItemType.STREAM);
        mockItems.add(item1);

        when(favoriteDao.getFavoriteItems(userId)).thenReturn(mockItems);
        Map<String, List<Item>> result = favoriteService.getFavoriteItems(userId);

        assertNotNull(result);
        assertTrue(result.containsKey("STREAM"));
        assertEquals(1, result.get("STREAM").size());
        assertEquals(item1, result.get("STREAM").get(0));
    }
}
