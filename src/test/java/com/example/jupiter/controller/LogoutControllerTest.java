package com.example.jupiter.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class LogoutControllerTest {

    private MockMvc mockMvc;
    private MockHttpSession mockSession;

    @BeforeEach
    public void setUp() {
        LogoutController logoutController = new LogoutController();
        mockMvc = MockMvcBuilders.standaloneSetup(logoutController).build();
        mockSession = new MockHttpSession();
    }

    @Test
    public void logout_ShouldInvalidateSession_And_ClearJSESSIONIDCookie() throws Exception {
        mockMvc.perform(post("/logout")
                        .session(mockSession))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("JSESSIONID"))
                .andExpect(cookie().maxAge("JSESSIONID", 0));
    }
}
