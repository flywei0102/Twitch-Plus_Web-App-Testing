package com.example.jupiter.service;

import com.example.jupiter.dao.RegisterDao;
import com.example.jupiter.entity.db.User;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RegisterServiceTest extends TestCase {

    @Mock
    private RegisterDao registerDao;

    @InjectMocks
    private RegisterService registerService;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Initialize mocks and inject them
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testRegister() throws Exception {
        // Create a user to register
        User testUser = new User();
        testUser.setUserId("testUser");
        testUser.setPassword("password"); // Assume this is the original password

        // Assume encryptPassword transforms the password in a predictable way for this test
        String encryptedPassword = "encryptedPassword";
        testUser.setPassword(encryptedPassword);

        // Configure mock to expect a call
        when(registerDao.register(any(User.class))).thenReturn(true);

        // Call the method under test
        boolean result = registerService.register(testUser);

        // Validate the result
        assertTrue(result);

        // Verify the interaction with the mock
        verify(registerDao).register(testUser);
    }
}