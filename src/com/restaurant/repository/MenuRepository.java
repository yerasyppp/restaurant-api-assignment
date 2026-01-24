package com.restaurant.repository;

import com.restaurant.database.DatabaseConnection;
import com.restaurant.exception.DatabaseOperationException;
import com.restaurant.model.DrinkItem;
import com.restaurant.model.FoodItem;
import com.restaurant.model.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuRepository {

    public void create(MenuItem item) {
        String sql = "INSERT INTO menu_items (name, price, item_type, calories, volume_ml) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.setString(3, item.getType());

            if (item instanceof FoodItem) {
                stmt.setInt(4, ((FoodItem) item).getCalories());
                stmt.setObject(5, null);
            } else if (item instanceof DrinkItem) {
                stmt.setObject(4, null);
                stmt.setInt(5, ((DrinkItem) item).getVolumeMl());
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error saving item: " + item.getName(), e);
        }
    }

    public List<MenuItem> getAll() {
        List<MenuItem> items = new ArrayList<>();
        String sql = "SELECT * FROM menu_items ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                items.add(mapRowToItem(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error fetching menu", e);
        }
        return items;
    }

    public MenuItem getById(int id) {
        String sql = "SELECT * FROM menu_items WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToItem(rs);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Error finding item by ID: " + id, e);
        }
        return null;
    }

    public void delete(int id) {
        String sql = "DELETE FROM menu_items WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new DatabaseOperationException("Item with ID " + id + " not found", null);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Error deleting item", e);
        }
    }

    private MenuItem mapRowToItem(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        double price = rs.getDouble("price");
        String type = rs.getString("item_type");

        if ("FOOD".equals(type)) {
            return new FoodItem(id, name, price, rs.getInt("calories"));
        } else {
            return new DrinkItem(id, name, price, rs.getInt("volume_ml"));
        }
    }
}