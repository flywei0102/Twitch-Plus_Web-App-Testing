package com.example.jupiter.service;

import com.example.jupiter.dao.FavoriteDao;
import com.example.jupiter.entity.db.Item;
import com.example.jupiter.entity.db.ItemType;
import com.example.jupiter.entity.response.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RecommendationServiceTest{
    @Mock
    private GameService gameService;

    @Mock
    private FavoriteDao favoriteDao;

    @InjectMocks
    private RecommendationService recommendationService;

    private static final int DEFAULT_GAME_LIMIT = 3;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // Set up mock games
        Game mockGame1 = new Game.Builder().id("123").name("Game Name 1").boxArtUrl("http://box.url/1").build();
        Game mockGame2 = new Game.Builder().id("456").name("Game Name 2").boxArtUrl("http://box.url/2").build();

        // Mock the topGames method to return a list of games
        Mockito.when(gameService.topGames(DEFAULT_GAME_LIMIT)).thenReturn(Arrays.asList(mockGame1, mockGame2));

        // Mock items for each game and type
        for (Game game : Arrays.asList(mockGame1, mockGame2)) {
            for (ItemType type : ItemType.values()) {
                Item mockItem = new Item();
                mockItem.setId(game.getId() + "-" + type.toString());
                mockItem.setTitle("Item Title - " + game.getName());
                mockItem.setType(type);  // (type)
                mockItem.setUrl("http://item.url");
                mockItem.setGameId(game.getId());

                // Mock the searchByType method to return a list of items for each game and type
                Mockito.lenient().when(gameService.searchByType(eq(game.getId()), eq(type), anyInt()))
                        .thenReturn(Arrays.asList(mockItem));
            }
        }
    }

    public void tearDown() throws Exception {
    }


    @Test
    public void testRecommendItemsByUser() throws  Exception{
        // construct the favorite item IDs and game IDs
        Set<String> favoriteItemIds = new HashSet<>(Arrays.asList("item1", "item2"));
        Map<String, List<String>> favoriteGameIds = new HashMap<>();
        favoriteGameIds.put("STREAM", Arrays.asList("123", "456"));
        favoriteGameIds.put("VIDEO", Arrays.asList("123"));
        favoriteGameIds.put("CLIP", Arrays.asList("456"));

        // Mock the favoriteDao's responses
        when(favoriteDao.getFavoriteItemIds(anyString())).thenReturn(favoriteItemIds);
        when(favoriteDao.getFavoriteGameIds(favoriteItemIds)).thenReturn(favoriteGameIds);

        Map<String, List<Item>> recommendedItemsMap = recommendationService.recommendItemsByUser("user1");

        assertNotNull(recommendedItemsMap, "The recommendation map should not be null.");
        assertEquals(3, recommendedItemsMap.size(), "The size of the recommendation map should be 3.");

    }

    @Test
    public void testRecommendItemsByDefault() throws Exception{
        Map<String, List<Item>> recommendedItemsMap = recommendationService.recommendItemsByDefault();

        assertNotNull(recommendedItemsMap);
        assertEquals(ItemType.values().length, recommendedItemsMap.size());

        for (Map.Entry<String, List<Item>> entry : recommendedItemsMap.entrySet()) {
            assertNotNull(entry.getValue());
            assertEquals(2, entry.getValue().size()); // Each type has one item recommended per game
            // Test the 1st recommended item(Assuming DEFAULT_PER_GAME_RECOMMENDATION_LIMIT = 1)
            Item recommendedItem1 = entry.getValue().get(0);
            assertNotNull(recommendedItem1);
            assertEquals("Item Title - Game Name 1", recommendedItem1.getTitle());
            // Test the 2nd recommended item (Assuming DEFAULT_PER_GAME_RECOMMENDATION_LIMIT = 1)
            Item recommendedItem2 = entry.getValue().get(1);
            assertNotNull(recommendedItem2);
            assertEquals("Item Title - Game Name 2", recommendedItem2.getTitle());
        }
    }
}