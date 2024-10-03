# Quarkus Microservices Project - Part 2

This project contains two microservices: `service-one` and `service-two`. `Service-two` communicates with `service-one` to perform CRUD operations on `Department` and `Employee` entities. The project also integrates MongoDB, implements reactive CRUD operations, fault tolerance, exception handling, and testing.

## Prerequisites

Before you begin, ensure you have the following installed on your system:

- Java 11
- Maven 3.2+
- Docker (for MongoDB container)
- Postman (optional for API testing)
- Git

## Project Structure

The project consists of the following modules:
- **service-one**: Handles CRUD operations on `Department` and `Employee` entities.
- **service-two**: Consumes APIs from `service-one`.
- **common-dtos**: Contains shared DTOs used between the services.

## Cloning the Project

To clone the project, run the following command:

```bash
git clone <your-git-repo-url>
cd <project-directory>
