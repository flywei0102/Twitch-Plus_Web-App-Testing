package com.example.jupiter.dao;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.jupiter.entity.db.Item;
import com.example.jupiter.entity.db.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.Arrays;
import java.util.HashSet;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.List;
import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavoriteDaoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @InjectMocks
    private FavoriteDao favoriteDao;

    @BeforeEach
    void setUp() {
        when(sessionFactory.openSession()).thenReturn(session);
        Mockito.lenient().when(session.getTransaction()).thenReturn(transaction); // Use lenient only for those methods not used in every test
    //    when(session.getTransaction()).thenReturn(transaction);
    }

    @Test
    void testSetFavoriteItem() {
        String userId = "user1";
        Item item = new Item();
        User user = new User();

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);  // Mock the beginning of the transaction
        when(session.get(User.class, userId)).thenReturn(user);
        when(session.getTransaction()).thenReturn(transaction);

        favoriteDao.setFavoriteItem(userId, item);

        verify(session).beginTransaction();  // Verify that the transaction was indeed started
        verify(transaction).commit();        // Verify commit is called
        verify(session).close();             // Ensure session is closed
    }


    @Test
    void testUnsetFavoriteItem() {
        // Arrange
        String userId = "user1";
        String itemId = "item1";
        Item item = new Item();
        item.setId(itemId);
        User user = new User();
        user.setItemSet(new HashSet<>(Arrays.asList(item)));

        when(session.get(User.class, userId)).thenReturn(user);
        when(session.get(Item.class, itemId)).thenReturn(item);

        // Act
        favoriteDao.unsetFavoriteItem(userId, itemId);

        // Assert
        verify(session).beginTransaction(); // Ensure transaction is started
        verify(session).get(User.class, userId);
        verify(session).get(Item.class, itemId);
        verify(transaction).commit(); // Ensure transaction is committed
        verify(session).close(); // Ensure session is closed
    }

    @Test
    void testGetFavoriteItems() {
        String userId = "user1";
        Set<Item> mockItemSet = new HashSet<>();
        Item mockItem = new Item();
        mockItem.setId("item1");
        mockItemSet.add(mockItem);

        User user = new User();
        user.setItemSet(mockItemSet);

        when(session.get(User.class, userId)).thenReturn(user);

        Set<Item> result = favoriteDao.getFavoriteItems(userId);

        assertNotNull(result);
        assertTrue(result.contains(mockItem));
        verify(session).get(User.class, userId);
        verify(session).close();
    }

    @Test
    void testGetFavoriteItemIds() {
        String userId = "user1";
        Set<Item> mockItemSet = new HashSet<>();
        Item mockItem = new Item();
        mockItem.setId("item1");
        mockItemSet.add(mockItem);

        User user = new User();
        user.setItemSet(mockItemSet);

        when(session.get(User.class, userId)).thenReturn(user);

        Set<String> itemIds = favoriteDao.getFavoriteItemIds(userId);

        assertNotNull(itemIds);
        assertTrue(itemIds.contains("item1"));
        verify(session).get(User.class, userId);
        verify(session).close();
    }
}

