package com.restaurant.service;

import com.restaurant.exception.InvalidInputException;
import com.restaurant.model.MenuItem;
import com.restaurant.repository.MenuRepository;

import java.util.List;

public class RestaurantService {
    private final MenuRepository repository;

    public RestaurantService() {
        this.repository = new MenuRepository();
    }

    public void addMenuItem(MenuItem item) throws InvalidInputException {
        if (!item.isValid()) {
            throw new InvalidInputException("Ошибка валидации: Цена должна быть > 0, а имя не пустым.");
        }

        if (item.getName().length() < 3) {
            throw new InvalidInputException("Ошибка: Название блюда слишком короткое (минимум 3 буквы).");
        }

        repository.create(item);
        System.out.println("✅ Успешно добавлено: " + item.getName());
    }

    public List<MenuItem> getMenu() {
        return repository.getAll();
    }

    public MenuItem getItemById(int id) throws InvalidInputException {
        MenuItem item = repository.getById(id);
        if (item == null) {
            throw new InvalidInputException("Блюдо с ID " + id + " не найдено.");
        }
        return item;
    }

    public void deleteItem(int id) {
        repository.delete(id);
        System.out.println("🗑 Блюдо удалено.");
    }
}