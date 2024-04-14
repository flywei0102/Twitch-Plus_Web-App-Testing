package com.example.jupiter.service;

import java.util.*;

import com.example.jupiter.entity.db.Item;
import com.example.jupiter.entity.db.ItemType;
import com.example.jupiter.entity.response.Game;
import com.example.jupiter.external.TwitchException;
import org.json.JSONStringer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.example.jupiter.entity.db.ItemType.*;
import static com.example.jupiter.service.GameService.DEFAULT_SEARCH_LIMIT;
import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        // 指定jupiter-servlet.xml的绝对路径
        "file:/Users/eric/Desktop/Twitch/jupiter/src/main/webapp/WEB-INF/jupiter-servlet.xml"
})
public class GameServiceTest {

    @Autowired
    private GameService gameService;


    @Test
    public void testTopGames() throws Exception {
        // Call the service method
        List<Game> result = gameService.topGames(10);
        // Assertions
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(10, result.size());
    }

    @Test
    public void testSearchGame() throws Exception{
        String gameName = "Dota";
        // Call the method under test
        Game game = gameService.searchGame(gameName);
        // Validate the result
        assertNotNull(game);
        assertEquals(gameName, game.getName());
    }

    @Test
    public void testSearchItems() throws Exception{
        /*
            "id": "490422",
            "name": "StarCraft II",
            "box_art_url": "https://static-cdn.jtvnw.net/ttv-boxart/490422-{width}x{height}.jpg"
         */
        String gameId = "490422";
        Map<String, List<Item>> itemMap = new HashMap<>();
        for (ItemType type : values()) {
            itemMap.put(type.toString(), gameService.searchByType(gameId, type, DEFAULT_SEARCH_LIMIT));
        }
        assertNotNull(itemMap);
        // Assert that every game in each list has the name "StarCraft II"
        for (List<Item> items : itemMap.values()) {
            for (Item item : items) {
                assertEquals("Game ID should be '490422'", "490422", item.getGameId());
            }
        }
    }

}
