# Restaurant Ordering System API (Assignment 3)

## A. Project Overview
This project is a Java-based console application (CLI) that simulates a Restaurant Ordering System. It is designed to demonstrate **Advanced OOP principles**, **JDBC Database connectivity** (PostgreSQL), and a **Multi-layer Architecture** (Controller → Service → Repository).

The system allows users to:
- Create different types of menu items (Food and Drinks).
- Read/View the menu from the database.
- Delete items by ID.
- Validate input data using business rules.

## B. OOP Design Documentation

The project strictly follows Object-Oriented Programming principles:

### 1. Abstract Class & Inheritance
- **Base Class:** `MenuItem` (Abstract). It contains common fields (`id`, `name`, `price`) and defines the structure for all items.
- **Subclasses:**
    - `FoodItem`: Extends `MenuItem` and adds specific field `calories`.
    - `DrinkItem`: Extends `MenuItem` and adds specific field `volumeMl`.

### 2. Interfaces
- **Interface:** `Validatable`. It defines the `isValid()` method.
- **Implementation:** The `MenuItem` class implements this interface, forcing all subclasses to define their own validation logic.

### 3. Polymorphism
- The `RestaurantService` and `Main` controller work with the generic `MenuItem` type.
- When `getDescription()` is called, the program automatically executes the specific version for either Food or Drink (Runtime Polymorphism).

### 4. Composition
- The `Order` class contains a `MenuItem` object as a field. This demonstrates the "Has-A" relationship (An Order *has a* Menu Item).

## C. Database Description

**Database:** PostgreSQL
**Tools:** PgAdmin 4, JDBC Driver

### SQL Schema
To run this project, execute the following SQL script to create tables:

```sql
-- 1. Table for Menu Items (Single Table Inheritance Strategy)
CREATE TABLE menu_items (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    item_type VARCHAR(20) NOT NULL, -- 'FOOD' or 'DRINK'
    calories INT,
    volume_ml INT
);

-- 2. Table for Orders (Demonstrates Foreign Key)
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    menu_item_id INT NOT NULL,
    quantity INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_menu_item FOREIGN KEY (menu_item_id) REFERENCES menu_items(id)
);
```
### Sample Data (Insert)

```sql
INSERT INTO menu_items (name, price, item_type, calories, volume_ml) VALUES 
('Caesar Salad', 12.50, 'FOOD', 350, NULL),
('Coca Cola', 2.50, 'DRINK', NULL, 500);
```
## D. Controller (Usage Example)
The application uses a CLI (Command Line Interface).
```java
Action: 2 (Add Food)
Name: Cheese Burger
Price: 15.00
Calories: 800

Result: Успешно добавлено: Cheese Burger
```
#### Example: Validation Error
```java
Action: 2
Name: Soup
Price: -5.00
Calories: 200

Result: Ошибка ввода: Ошибка валидации: Цена должна быть > 0
```
## E. Instructions to Compile and Run
#### 1. Prerequisites:
* Java JDK 17 or higher. 
* PostgreSQL installed and running.
* Database restaurant_db created.
#### 2.Setup:
* Clone the repository.
* Open __src/main/java/com/restaurant/database/DatabaseConnection.java__ and update the PASSWORD field with your PostgreSQL password.
* Add the PostgreSQL JDBC Driver jar to the project libraries.
#### 3.Run:
* Run the Main.java class located in com.restaurant.controller.
## G. Reflection Section
### What I learned
* **JDBC & PreparedStatement:** I learned how to safely send data from Java to PostgreSQL without using raw SQL concatenation.
* **Custom Exceptions:** I implemented InvalidInputException to handle business errors separately from system errors.
* **Architecture:** Splitting the code into Repository (DB), Service (Logic), and Controller (UI) made the code much cleaner.
### Challenges Faced
- **Mapping Subclasses to Database:** One of the main challenges was figuring out how to store inherited classes (`FoodItem` and `DrinkItem`) in a single database table. I solved this by adding an `item_type` column (Discriminator) and using an `if-else` block in the Repository to instantiate the correct class based on this string.
- **Project Structure:** Organizing the project into packages (`model`, `repository`, `service`, `controller`) required planning. I had to ensure that the Controller never talks directly to the Repository, forcing all calls through the Service layer.