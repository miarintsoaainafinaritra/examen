INSERT INTO Ingredient (name, unit, current_stock) VALUES
('Tomato', 'kg', 100.00),
('Cheese', 'kg', 50.00),
('Dough', 'kg', 75.00),
('Lettuce', 'kg', 30.00),
('Chicken', 'kg', 60.00);

INSERT INTO StockMovement (ingredient_id, movement_type, quantity) VALUES
(1, 'IN', 20.00),
(2, 'IN', 15.00),
(3, 'IN', 25.00),
(4, 'IN', 10.00),
(5, 'IN', 30.00);

INSERT INTO Dish (name, price) VALUES
('Margherita Pizza', 8.50),
('Caesar Salad', 6.00),
('Grilled Chicken', 10.00),
('Pasta Carbonara', 9.00),
('Tomato Soup', 5.00);

INSERT INTO DishIngredient (dish_id, ingredient_id, quantity, unit) VALUES
(1, 1, 0.20, 'kg'),
(1, 2, 0.15, 'kg'),
(1, 3, 0.30, 'kg'),
(2, 4, 0.10, 'kg'),
(3, 5, 0.25, 'kg'),
(4, 1, 0.15, 'kg'),
(4, 2, 0.10, 'kg'),
(5, 1, 0.30, 'kg');

INSERT INTO `Order` (reference, customer_name, total_amount, payment_status) VALUES
('ORD001', 'John Doe', 22.00, 'PAID'),
('ORD002', 'Jane Smith', 16.00, 'UNPAID'),
('ORD003', 'Alice Johnson', 26.50, 'PAID');

INSERT INTO OrderItem (order_id, dish_id, quantity, subtotal) VALUES
(1, 1, 2, 17.00),
(1, 5, 1, 5.00),
(2, 2, 1, 6.00),
(2, 3, 1, 10.00),
(3, 4, 2, 18.00),
(3, 1, 1, 8.50);

INSERT INTO Sale (order_id) VALUES
(1),
(3);