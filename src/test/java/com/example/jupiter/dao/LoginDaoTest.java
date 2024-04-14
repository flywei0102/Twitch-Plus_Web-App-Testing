package com.example.jupiter.dao;

import com.example.jupiter.entity.db.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginDaoTest {

    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @InjectMocks
    private LoginDao loginDao;
    @BeforeEach
    void setUp() {
        when(sessionFactory.openSession()).thenReturn(session);
    }

    @Test
    void verifyLoginSuccess() {
        // Setup
        String userId = "testUser";
        String password = "testPass";
        User mockUser = new User();
        mockUser.setUserId(userId);
        mockUser.setPassword(password);
        mockUser.setFirstName("Max");

        // Mock behavior
        when(session.get(User.class, userId)).thenReturn(mockUser);
        String result = loginDao.verifyLogin(userId, password);   //ACTION
        assertEquals("Max", result);
    }

    @Test
    void verifyLoginFailure() {
        // Setup
        String userId = "testUser";
        String wrongPassword = "wrongPass";

        // Mock behavior
        when(session.get(User.class, userId)).thenReturn(null); // Simulating user not found
        String result = loginDao.verifyLogin(userId, wrongPassword);
        assertEquals("", result); // Expecting an empty string on authentication failure
    }
}


