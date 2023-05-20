# Online Store Demo
This API contains users with 3 roles; User, Business, Admin
* Users are able to do basic things like update user info, add to cart, search products, process orders.
* Business accounts are able to create, edit and delete products as well as view user orders.
* Admins have full access of the API being able to update and delete user accounts.

# How it works
The application uses Springboot and Spring JPA with a PostgreSQL database with implemented Spring Security features including a SecurityFilterChain and JWT authentication.

Before any request reaches the DispatcherServlet, the JWT token is extracted and verified for expiry date or authenticity e.t.c before Authentication is set in SecurityContextHolder.

After the roles' permission has been checked if it has access to an endpoint i.e deleting a user, the business logic is executed and a response is given.

# Codebase
The code is split into 4 folders:
1. `api` contains subfolders of all endpoints (/store, /auth, /account e.t.c) with Request/Response mappers
2. `application` contains the service layer - where all business logic is executed
3. `config` contains all Spring Security configuration including JwtService and SecurityFilterChain
4. `core` contains all the entities and repositories that the business layer needs

# Data Structure
* The two main tables are those that contain the user information and product information.
* Additionally, we have two join tables (shopping_cart and order) which both share a many-to-one relationship with User & Products

(note: there may be a better way but the below was my solution)
![](data%20structure.png)

DBML: (ignore - below is used to create the graph above)

Table user {
id integer [primary key]
firstName varchar
lastName varchar
password varchar
email varchar
role varchar
}

TABLE order {
id integer [primary key]
purchase_date timestamp
product_id integer
user_id integer
}

Table product {
id integer [primary key]
product varchar
description varchar
category varchar
price integer
rating integer
}

TABLE shopping_cart {
id integer [primary key]
user_id integer
product_id integer
}

Ref: order.product_id > product.id
Ref: order.user_id > user.id
Ref: shopping_cart.user_id > user.id
Ref: shopping_cart.product_id > product.id