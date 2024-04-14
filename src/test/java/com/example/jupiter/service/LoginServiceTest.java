package com.example.jupiter.service;

import com.example.jupiter.dao.LoginDao;
import com.example.jupiter.util.Util;
import junit.framework.TestCase;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @Mock
    private LoginDao loginDao;

    @InjectMocks
    private LoginService loginService;

    @Test
    public void testVerifyLogin() throws Exception {
        String userId = "user123";
        String password = "password";
        String encryptedPassword = "encryptedPassword";

        // Explicitly using MockedStatic<Util> for mocking static methods
        try (MockedStatic<Util> mocked = mockStatic(Util.class)) {
            mocked.when(() -> Util.encryptPassword(userId, password)).thenReturn(encryptedPassword);

            // Assuming 'verifyLogin' returns "Success" on successful authentication
            when(loginDao.verifyLogin(userId, encryptedPassword)).thenReturn("Success");

            String result = loginService.verifyLogin(userId, password);

            assertEquals("Success", result);
        }
    }



}