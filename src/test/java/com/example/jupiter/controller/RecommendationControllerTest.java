package com.example.jupiter.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        // 指定jupiter-servlet.xml的绝对路径
        "file:/Users/eric/Desktop/Twitch/jupiter/src/main/webapp/WEB-INF/jupiter-servlet.xml"
})
public class RecommendationControllerTest {

    @Autowired
    private WebApplicationContext wac;  // This should autowire the context

    private MockMvc mockMvc;
    private MockHttpSession mockSession;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build(); // Builds the MockMvc using the autowired WebApplicationContext
        mockSession = new MockHttpSession(wac.getServletContext(), "mySession");
    }

    @Test
    public void testRecommendationWithSession() throws Exception {
        mockMvc.perform(get("/recommendation")
                        .session(mockSession))
                .andExpect(status().isOk());
    }
}
