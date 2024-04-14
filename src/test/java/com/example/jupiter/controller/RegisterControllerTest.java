package com.example.jupiter.controller;

import com.example.jupiter.entity.db.User;
import com.example.jupiter.service.RegisterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:/Users/eric/Desktop/Twitch/jupiter/src/main/webapp/WEB-INF/jupiter-servlet.xml"})
public class RegisterControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Mock
    private RegisterService registerService;

    @InjectMocks
    private RegisterController registerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialize the controller with mocks, if not using the web application context
        this.mockMvc = MockMvcBuilders.standaloneSetup(registerController)
                .build();
    }

    @Test
    public void register_ShouldReturn200_WhenRegistrationIsSuccessful() throws Exception {
        User user = new User();
        // Setup object
        doReturn(true).when(registerService).register(any(User.class));

        mockMvc.perform(post("/register")
                        .content(new ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void register_ShouldReturn409_WhenRegistrationFails() throws Exception {
        User user = new User();
        // Setup object
        doReturn(false).when(registerService).register(any(User.class));

        mockMvc.perform(post("/register")
                        .content(new ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }
}
