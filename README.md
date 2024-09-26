# Microservices Project

This project implements a microservices architecture using Quarkus, featuring **Service One** and **Service Two**. **Service One** simulates a product management service, while **Service Two** acts as a consumer that interacts with **Service One** to perform various operations on products. This project demonstrates the integration of fault tolerance features, such as circuit breakers and retry mechanisms, to enhance the resilience and reliability of microservices.

## Table of Contents

- [Getting Started](#getting-started)
- [Running the Project](#running-the-project)
- [Service One](#service-one)
- [Service Two](#service-two)
- [Consuming the Services](#consuming-the-services)
- [Fault Tolerance](#fault-tolerance)
- [Testing with Postman](#testing-with-postman)


## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed on your local machine:

- **Java 11**: The Java Development Kit (JDK) version 11 or later.
- **Maven 3.2 or later**: A build automation tool for Java projects.
- **Quarkus CLI (optional)**: For easier development and management of your Quarkus applications.

### Clone the Repository

To get a local copy of the project, clone the repository using the following command:

```bash
git clone <repository-url>
cd <repository-name>

### Build and Run the Services

To build and run both services in development mode, navigate to the root directory of the project and execute the following command:

```bash
./mvnw compile quarkus:dev


### Testing with Postman
You can import the json collection provided in the git Repo 