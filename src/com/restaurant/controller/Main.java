package com.restaurant.controller;

import com.restaurant.exception.InvalidInputException;
import com.restaurant.model.DrinkItem;
import com.restaurant.model.FoodItem;
import com.restaurant.model.MenuItem;
import com.restaurant.service.RestaurantService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RestaurantService service = new RestaurantService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Добро пожаловать в систему управления рестораном!");

        while (true) {
            System.out.println("\n--- ГЛАВНОЕ МЕНЮ ---");
            System.out.println("1. Показать всё меню");
            System.out.println("2. Добавить ЕДУ");
            System.out.println("3. Добавить НАПИТОК");
            System.out.println("4. Удалить блюдо");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Введите число!");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        List<MenuItem> menu = service.getMenu();
                        System.out.println("\n=== СПИСОК МЕНЮ ===");
                        for (MenuItem item : menu) {
                            System.out.println("ID: " + item.getId() + " | " + item.getDescription() + " | $" + item.getPrice());
                        }
                        break;

                    case 2:
                        System.out.print("Название еды: ");
                        String fName = scanner.nextLine();
                        System.out.print("Цена: ");
                        double fPrice = scanner.nextDouble();
                        System.out.print("Калории: ");
                        int cals = scanner.nextInt();

                        service.addMenuItem(new FoodItem(0, fName, fPrice, cals));
                        break;

                    case 3:
                        System.out.print("Название напитка: ");
                        String dName = scanner.nextLine();
                        System.out.print("Цена: ");
                        double dPrice = scanner.nextDouble();
                        System.out.print("Объем (мл): ");
                        int vol = scanner.nextInt();

                        service.addMenuItem(new DrinkItem(0, dName, dPrice, vol));
                        break;

                    case 4:
                        System.out.print("Введите ID для удаления: ");
                        int id = scanner.nextInt();
                        service.deleteItem(id);
                        break;

                    case 0:
                        System.out.println("До свидания!");
                        return;

                    default:
                        System.out.println("Нет такой команды.");
                }
            } catch (InvalidInputException e) {
                System.err.println(" Ошибка ввода: " + e.getMessage());
            } catch (Exception e) {
                System.err.println(" Ошибка системы: " + e.getMessage());
            }
        }
    }
}