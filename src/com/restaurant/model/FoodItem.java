package com.restaurant.model;

public class FoodItem extends MenuItem {
    private int calories;

    public FoodItem(int id, String name, double price, int calories) {
        super(id, name, price);
        this.calories = calories;
    }

    @Override
    public String getDescription() {
        return "Food: " + getName() + " (" + calories + " kcal)";
    }

    @Override
    public String getType() {
        return "FOOD";
    }

    @Override
    public boolean isValid() {
        return getPrice() > 0 && getName() != null && !getName().isEmpty() && calories > 0;
    }

    public int getCalories() { return calories; }
}