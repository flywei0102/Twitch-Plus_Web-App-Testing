package com.example.jupiter.dao;

import com.example.jupiter.entity.db.Item;
import com.example.jupiter.entity.db.ItemType;
import com.example.jupiter.entity.db.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class FavoriteDao {
    @Autowired
    private SessionFactory sessionFactory;
    // Insert a favorite record to the database
    public void setFavoriteItem(String userId, Item item) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();  // This should be right after opening the session
            User user = session.get(User.class, userId);
            if (user != null) {
                user.getItemSet().add(item);
                session.saveOrUpdate(user);
            }
            session.getTransaction().commit();  // Commit transaction after all modifications
        } catch (Exception ex) {
            ex.printStackTrace();
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();  // Rollback in case of exception
            }
        } finally {
            if (session != null) {
                session.close();  // Ensure session is always closed
            }
        }
    }

    public void unsetFavoriteItem(String userId, String itemId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction(); // Ensure to begin transaction before doing operations
            User user = session.get(User.class, userId);
            Item item = session.get(Item.class, itemId);
            user.getItemSet().remove(item);
            session.update(user); // No need to save, just update if it's a managed entity
            session.getTransaction().commit(); // Commit the transaction
        } catch (Exception ex) {
            ex.printStackTrace();
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback(); // Proper rollback check
            }
        } finally {
            if (session != null) session.close();
        }
    }

    /*
    public void setFavoriteItem(String userId, Item item) {

        Session session = null;
        try {
            session = sessionFactory.openSession();
            User user = session.get(User.class, userId);
            user.getItemSet().add(item);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }

    }
    // Remove a favorite record from the database
    public void unsetFavoriteItem(String userId, String itemId) {
        Session session = null;

        try {
            session = sessionFactory.openSession();
            User user = session.get(User.class, userId);
            Item item = session.get(Item.class, itemId);
            user.getItemSet().remove(item);
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
    }
*/
    public Set<Item> getFavoriteItems(String userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, userId).getItemSet();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new HashSet<>();
    }

    // Get favorite item ids for the given user
    public Set<String> getFavoriteItemIds(String userId) {
        Set<String> itemIds = new HashSet<>();

        try (Session session = sessionFactory.openSession()) {
            Set<Item> items = session.get(User.class, userId).getItemSet();
            for(Item item : items) {
                itemIds.add(item.getId());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return itemIds;
    }

    // Get favorite items for the given user. The returned map includes three entries like {"Video": [item1, item2, item3], "Stream": [item4, item5, item6], "Clip": [item7, item8, ...]}
    public Map<String, List<String>> getFavoriteGameIds(Set<String> favoriteItemIds) {
        Map<String, List<String>> itemMap = new HashMap<>();
        for (ItemType type : ItemType.values()) {
            itemMap.put(type.toString(), new ArrayList<>());
        }

        try (Session session = sessionFactory.openSession()) {
            for(String itemId : favoriteItemIds) {
                Item item = session.get(Item.class, itemId);
                itemMap.get(item.getType().toString()).add(item.getGameId());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return itemMap;
    }
}
