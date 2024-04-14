package com.example.jupiter.controller;

import com.example.jupiter.entity.db.Item;
import com.example.jupiter.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SearchControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private SearchController searchController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
    }

    @Test
    public void testSearchEndpoint() throws Exception {
        // Mock data
        Map<String, List<Item>> itemsMap = new HashMap<>();
        List<Item> itemList = Collections.singletonList(new Item());
        itemsMap.put("gameId", itemList);

        // Mock behavior
        when(gameService.searchItems(anyString())).thenReturn(itemsMap);

        // Perform GET request and verify the response
        mockMvc.perform(get("/search")
                        .param("game_id", "gameId"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
