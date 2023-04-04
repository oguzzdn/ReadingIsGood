

# Reading is Good

ReadingIsGood is an online books retail firm that aims to deliver books to customers on the same day by using a centralized warehouse. To support their vision operations, they require stock consistency. Reading is Good is a user-friendly and efficient small-scale book delivery system that is designed to help ReadingIsGood manage the different aspects of their operations, including adding books, customers, and authors inside books, placing orders, and retrieving book, order, and monthly order details. This application is useful for anyone who wants to set up an online book retail business and enhance their operations with an organized and automated system.

The application has different endpoints with specific functionalities.

## Customer Endpoint

- Adding a customer

- Getting orders of a particular customer

The email property of customers is unique. If a customer tries to add an email address that already exists in the database, the application returns an error message and does not persist the customer object to the database.
## Book Endpoint

- Adding a book with an author
- Updating the stock amount of a book
- Getting the details of the particular book by id value
There are rules in updating the stock amount of book. Update amount should be valid and greater than zero, and if not, the application returns an error message and does not update the stock amount.
## Order Endpoint
- Adding orders

- Getting order details

- Getting orders by date interval

User can add orders with two foreign keys, book id and customer id. The order can contain multiple books with multiple quantities. If the book id or customer id does not exist in the database, the application returns an error message, and the order object does not persist. Also, if one of the ordered books is out of stock or has less stock than the requested quantity, the application returns an error message, and the order object does not persist.

User can query orders with id and get all the details of the orders.

User can get orders by a date interval. They must pass two date parameters without time, the start date and end date, respectively.
## Stats Endpoint
- Stats Endpoint:

- Getting monthly stats of customers

In this endpoint, users can query the monthly stats of a customer. With the customer id, the application gets the orders of the customer and returns cumulative stats of the orders.
## Runing

To run the application, you can find example requests under the request folder. These requests must be imported into Postman by using the “File -> Import” option. Then drag the requests to the opening window, and the request will be ready to use.

The application is running on the default port: 

```bash
  http://localhost:8080
```
Reading is Good’s case work includes a Dockerfile. To build the project, use the following command:

```bash
  docker-compose up -d --build
```
After building, run:
```bash
  docker-compose up
```
and the application will stand up with all the logs. If you prefer detaching from logs, use:

```bash
  docker-compose up -d
```
Lastly, for additional documentation of the application, you can check this link:
```bash
    http://localhost:8080/swagger-ui.html#
```
Since we do have bearer token requests, it must be triggered from Postman. We already have a pre-request script for getting the token from the bearer endpoint.