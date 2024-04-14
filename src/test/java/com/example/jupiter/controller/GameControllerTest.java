package com.example.jupiter.controller;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;

public class GameControllerTest extends ControllerBase {

 //   private Object MockMvcResultMatchers;

    @Test
    public void testGetGame() throws UnsupportedEncodingException, Exception {
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/game")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .param("game_name","Dota")
        );
        MvcResult mvcResult = resultActions
//        		 .andDo(MockMvcResultHandlers.print())
        		 .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    //    String result = mvcResult.getResponse().getContentAsString();
    //    System.out.println(result);
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
    }
}

