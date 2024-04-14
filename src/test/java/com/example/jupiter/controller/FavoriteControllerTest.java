package com.example.jupiter.controller;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.UnsupportedEncodingException;

public class FavoriteControllerTest extends ControllerBase {


    public void setUp() throws Exception {
   //     super.setUp();
    }

    public void tearDown() throws Exception {
    }

    @Test
    public void testSetFavoriteItem() throws Exception{
        String jsonPayload = "{\"favoriteItem\":{\"id\":\"2098889619\"," +
                                                "\"title\":\"xoma55game\",\"gameId\":\"1157427142\"," +
                                                "\"url\":\"https://example.com\"," +
                                                "\"thumbnailUrl\":\"https://example.com/image.jpg\"}}";

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/favorite")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonPayload)
                                .sessionAttr("user_id", "testUserId")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assert.assertEquals(200, mvcResult.getResponse().getStatus());

    }
    @Test
    public void testUnsetFavoriteItem() throws Exception {
        // Prepare a JSON payload for the request body, assuming your application expects the ID of the item to unset
        String jsonPayload = "{\"favorite\":{\"id\":\"2098889619\"}}";

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.delete("/favorite")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonPayload)
                                .sessionAttr("user_id", "testUserId") // Simulate a logged-in user
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        // Assert that the HTTP response status is 200 OK
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }


    @Test
    public void testGetFavoriteItem() throws UnsupportedEncodingException, Exception {
        // Assuming the controller uses session attribute "user_id" to identify the logged-in user
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/favorite")
                                .sessionAttr("user_id", "testUserId") // Simulate a logged-in user
                )
                .andExpect(MockMvcResultMatchers.status().isOk()) // Expect the response status to be 200 OK
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                // .andExpect(MockMvcResultMatchers.jsonPath("$.key").value(expectedValue))
                .andReturn();
        // Assert the response status code
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }
}