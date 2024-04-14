package com.example.jupiter.service;
import com.example.jupiter.dao.FavoriteDao;
import com.example.jupiter.dao.LoginDao;
import com.example.jupiter.dao.RegisterDao;
import com.example.jupiter.entity.db.Item;
import com.example.jupiter.entity.db.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FavoriteService {

    private FavoriteDao favoriteDao;
    private LoginDao loginDao;
    private RegisterDao registerDao;
    @Autowired
    public FavoriteService(FavoriteDao favoriteDao, LoginDao loginDao, RegisterDao registerDao) {
        this.favoriteDao = favoriteDao;
        this.loginDao = loginDao;
        this.registerDao = registerDao;
    }

    public void setFavoriteItem(String userId, Item item) {
        favoriteDao.setFavoriteItem(userId, item);
    }
    public void unsetFavoriteItem(String userId, String itemId) {
        favoriteDao.unsetFavoriteItem(userId, itemId);
    }

    public Map<String, List<Item>> getFavoriteItems(String userId) {
        Map<String, List<Item>> itemMap = new HashMap<>();
        for (ItemType type : ItemType.values()) {
            itemMap.put(type.toString(), new ArrayList<>());
        }
        Set<Item> favorites = favoriteDao.getFavoriteItems(userId);
        for(Item item : favorites) {
            itemMap.get(item.getType().toString()).add(item);
        }
        return itemMap;
    }
}

