package com.example.jupiter.controller;
import com.example.jupiter.entity.request.LoginRequestBody;
import com.example.jupiter.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {
    private MockMvc mockMvc;
    @Mock
    private LoginService loginService;
    @InjectMocks
    private LoginController loginController;
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }


    @Test
    public void login_ShouldReturn200_WhenCredentialsAreCorrect() throws Exception {
        // Given
        LoginRequestBody requestBody = new LoginRequestBody("testUser", "testPass");
        String expectedResponse = "{\"user_id\":\"testUser\",\"name\":\"John\"}"; // Adjusted keys
        // When
        when(loginService.verifyLogin("testUser", "testPass")).thenReturn("John");
        // Then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)) // Adjusted to ignore charset
                .andExpect(content().json(expectedResponse, true)); // true to strictly check all fields
    }
    @Test
    public void login_ShouldReturn401_WhenCredentialsAreIncorrect() throws Exception {
        String userId = "testUser";
        String password = "wrongPass";

        given(loginService.verifyLogin(userId, password)).willReturn("");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"" + userId + "\", \"password\":\"" + password + "\"}"))
                .andExpect(status().isUnauthorized());
    }
}
