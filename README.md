# Waste Management System

This is a waste management system designed to help manage and store waste-related guidelines and tips. It provides an API to manage guidelines and tips, including operations such as creating, updating, retrieving, and deleting them. The system utilizes Spring Boot for backend development, and the data is persisted in a database using Spring Data JPA.

## Features

- **Tip Management**: Create, update, retrieve, and delete waste management tips.
- **Guideline Management**: Create, update, retrieve, and delete waste management guidelines.
- **Database Interaction**: All data is stored and managed in a database using Spring Data JPA and Hibernate.
- **Transactional Integrity**: Operations on tips and guidelines are wrapped in transactions to ensure consistency and rollback on errors.

## Technologies Used

- **Spring Boot**: For building the backend service.
- **Spring Data JPA**: For interacting with the database.
- **Hibernate**: As the ORM (Object Relational Mapping) framework.
- **H2 Database**: In-memory database (or replaceable with another relational database).
- **Java**: The programming language used.
- **Maven**: Dependency management and build tool.

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17 or higher** (JDK)
- **Maven** for building the project
- **IDE** like IntelliJ IDEA or Eclipse (optional)

### Installation

1. Clone the repository:

   ```bash
   git clone https://git@github.com:SanzaGemini/waste-management-system.git
   cd waste-management-system
   ```

2. Build the project using Maven:

   ```bash
   mvn clean install
   ```

3. Run the application:

   ```bash
   mvn spring-boot:run
   ```

4. The application should now be running on `http://localhost:8080`.

### Database Configuration

By default, the application uses an in-memory H2 database. If you want to use a different database, modify the `application.properties` file in the `src/main/resources` directory:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/waste_management
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
```

Make sure to replace the `url`, `username`, and `password` with your actual database credentials.

## API Endpoints

### Tips

- **Create a new tip**: `POST /api/tips`
  - Request body: `TipDTO` (JSON format)
  
- **Get a tip by ID**: `GET /api/tips/{id}`
  - Returns the tip with the specified ID.
  
- **Get all tips**: `GET /api/tips`
  - Returns a list of all tips.
  
- **Update a tip**: `PUT /api/tips/{id}`
  - Request body: `TipDTO` (JSON format)
  
- **Delete a tip**: `DELETE /api/tips/{id}`
  - Deletes the tip with the specified ID.

### Guidelines

- **Create a new guideline**: `POST /api/guidelines`
  - Request body: `GuidelineDTO` (JSON format)
  
- **Get a guideline by ID**: `GET /api/guidelines/{id}`
  - Returns the guideline with the specified ID.
  
- **Get all guidelines**: `GET /api/guidelines`
  - Returns a list of all guidelines.
  
- **Update a guideline**: `PUT /api/guidelines/{id}`
  - Request body: `GuidelineDTO` (JSON format)
  
- **Delete a guideline**: `DELETE /api/guidelines/{id}`
  - Deletes the guideline with the specified ID.

  ### Category

- **Create a new category**: `POST /api/categories`
  - Request body: `CategoryDTO` (JSON format)
  
- **Get a category by ID**: `GET /api/categories/{id}`
  - Returns the category with the specified ID.
  
- **Get all categorys**: `GET /api/categories`
  - Returns a list of all category.
  
- **Update a category**: `PUT /api/categories/{id}`
  - Request body: `CategoryDTO` (JSON format)
  
- **Delete a category**: `DELETE /api/categories/{id}`
  - Deletes the category with the specified ID.

## Project Structure

- **`/src/main/java`**: Contains the source code for the application.
  - **`com.enviro.assessment.grad001.bhekumuzivilakazi.waste_management_system`**: Main package.
    - **`controller`**: REST API controllers.
    - **`service`**: Business logic for tips and guidelines.
    - **`model`**: Data models (`Tip`, `Guideline`, etc.).
    - **`repository`**: Spring Data JPA repositories for interacting with the database.
    - **`exceptions`**: Custom exception classes like `NotFoundException`.
    - **`reponse`**: Custom response classes to serialize the response.
- **`/src/main/resources`**: Contains configuration files.
  - **`application.properties`**: Configuration file for database and application settings.

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-name`).
3. Commit your changes (`git commit -am 'Add new feature'`).
4. Push to the branch (`git push origin feature-name`).
5. Create a new Pull Request.

## License

This project is licensed under the Enviro360 License.
