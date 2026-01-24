package com.restaurant.model;

public class DrinkItem extends MenuItem {
    private int volumeMl;

    public DrinkItem(int id, String name, double price, int volumeMl) {
        super(id, name, price);
        this.volumeMl = volumeMl;
    }

    @Override
    public String getDescription() {
        return "Drink: " + getName() + " (" + volumeMl + " ml)";
    }

    @Override
    public String getType() {
        return "DRINK";
    }

    @Override
    public boolean isValid() {
        return getPrice() > 0 && getName() != null && !getName().isEmpty() && volumeMl > 0;
    }

    public int getVolumeMl() { return volumeMl; }
}