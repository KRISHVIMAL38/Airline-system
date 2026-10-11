# Airline-system

#  Airline Management System — Microservices Platform

A production-oriented **Airline Management System** built using a **microservices architecture** and designed to simulate real-world airline booking, flight operations, pricing, payments, seat management, ancillary services, and customer communication workflows.

The platform is decomposed into independently deployable domain and event-driven services, with centralized configuration, service discovery, API gateway routing, authentication/authorization, and dedicated services for core airline business capabilities.

The primary goal of this project is to demonstrate how a complex, distributed business domain can be designed and implemented using **Spring Boot, Spring Cloud, REST APIs, event-driven communication, and cloud-native engineering principles**.

---

<img width="3200" height="2660" alt="image" src="https://github.com/user-attachments/assets/fe986505-ef73-4cd2-ae40-01344508181e" />



# 🧩 Microservices

| Service | Responsibility |
|---|---|
| **API Gateway** | Single entry point for client requests, routing, authentication filters and cross-cutting concerns |
| **Config Server** | Centralized configuration management for all microservices |
| **Service Registry** | Service registration and discovery |
| **Airline Core Service** | Manages airlines, aircrafts and airline-related master data |
| **Location Service** | Manages airports, cities and geographical information |
| **Flight Ops Service** | Flight search, scheduling and flight-instance management |
| **Seat Service** | Manages aircraft seat configuration and flight seat instances |
| **Pricing Service** | Calculates and manages flight pricing |
| **Ancillary Service** | Manages additional services such as meals and travel insurance |
| **Booking Service** | Handles flight reservations and booking lifecycle |
| **Payment Service** | Handles payment processing and payment status |
| **Notification Service** | Sends notifications related to bookings, payments and tickets |
| **User Service** | User registration, login, authentication and authorization |

---

# Service Responsibilities

## 1. API Gateway

The API Gateway acts as the **single entry point** into the microservices platform.

### Responsibilities

- Request routing
- Authentication/authorization integration
- Hiding internal service topology
- Centralized cross-cutting concerns
- API composition where required
- Request filtering
- Service discovery integration

Example:

```text
Client
   |
   v
API Gateway
   |
   +----> User Service
   +----> Flight Ops Service
   +----> Booking Service
   +----> Payment Service
   +----> Seat Service
```

This prevents clients from directly depending on internal service addresses.

---

# 2. Config Server

The Config Server provides **centralized configuration management**.

Instead of maintaining configuration independently across every service, common configuration are managed centrally.

Configuration includes:

```text
Database configuration
Service URLs
Security configuration
Application properties
Environment-specific configuration
External integration configuration
```

This makes configuration changes easier to manage across multiple environments.

---

# 3. Service Registry

The Service Registry enables **dynamic service discovery**.

Instead of hardcoding service addresses:

```text
location-service : http://localhost:9191
user-service     : http://localhost:9192
airline-core-service : http://localhost:9193
flight-ops-service   : http://localhost:9194
pricing-service      : http://localhost:9195
seat-service         : http://localhost:9196
ancillary-service    : http://localhost:9197
booking-service      : http://localhost:9198
payment-service      : http://localhost:9199
service-registry     : http://localhost:9200
notification-gateway : http://localhost:9201
seat-service         : http://localhost:9202
seat-service         : http://localhost:8888
```

Services register with the Service Registry using their configured application names:

| Service              | Application Name       |
| -------------------- | ---------------------- |
| User Service         | `user-service`         |
| Airline Core Service | `airline-core-service` |
| Location Service     | `location-service`     |
| Flight Ops Service   | `flight-ops-service`   |
| Seat Service         | `seat-service`         |
| Pricing Service      | `pricing-service`      |
| Ancillary Service    | `ancillary-service`    |
| Booking Service      | `booking-service`      |
| Payment Service      | `payment-service`      |
| Notification Service | `notification-service` |


For example, if Eureka is used, the Service Registry dashboard is typically available at `http://localhost:8761`, unless configured otherwise.

---

# 4. Airline Core Service

Responsible for managing airline and aircraft-related information.

### Responsibilities

- Airline management
- Aircraft management
- Aircraft configuration

---

# 5. Location Service

Manages geographical entities required by the airline platform.

### Responsibilities

- Airport management
- City management
- Airport-city relationships

---

# 6. Flight Ops Service

The Flight Operations service is responsible for operational flight management.

### Responsibilities

- Flight creation
- Flight scheduling
- Flight search
- Flight-instance creation
- Flight-instance lifecycle
- arrival/departure management
- Aircraft assignment
- Flight schedule management


---

# 7. Seat Service

### Responsibilities

- Aircraft seat configuration
- Seat layout
- Seat availability
- Seat reservation
- Flight seat instances
- Seat status management

Example:

This separation is particularly important because the same physical aircraft seat can have different availability across different flight instances.

---

# 8. Pricing Service

The Pricing Service manages flight pricing.

### Responsibilities

- Manage flight fare information
- Calculate applicable flight prices
- Apply configured pricing rules
- Provide pricing information to the Booking Service
- Support pricing calculations for selected flight instances
- Coordinate with the Ancillary Service when additional charges are required

A major benefit of isolating pricing is that pricing rules can evolve without modifying the booking service.

---

# 9. Ancillary Service

Manages additional products purchased alongside a flight.

### Supported Ancillaries

- Meals
- Travel insurance

The service is designed so additional ancillary products can be introduced without changing the core booking domain.

---

# 10. Booking Service

The Booking Service is the central business service responsible for managing flight reservations.

### Responsibilities

- Create, retrieve, update, and cancel bookings
- Manage passenger and booking information
- Coordinate flight and seat selection
- Retrieve flight pricing
- Handle ancillary selections
- Coordinate payment processing
- Manage booking status transitions
- Publish booking-related events through Kafka
- Initiate booking notifications through the Notification Service

---

# 11. Payment Service

Responsible for payment processing and payment lifecycle management.

### Responsibilities

- Payment initiation
- Payment processing
- Payment status tracking
- Payment confirmation
- Payment failure handling
- Payment notications though mails

Example payment lifecycle:

The service is isolated from booking so payment-related functionality can evolve independently.

---

# 12. Notification Service

The Notification Service manages customer communication.

### Notification Types

- Booking confirmation
- Payment confirmation
- Payment failure
- Ticket confirmation
- Booking cancellation
- Flight-related notifications

Example:


The service can be extended to support multiple notification providers without tightly coupling them to business services.

---

# 13. User Service

The User Service handles authentication and authorization.

### Responsibilities

- User registration
- Login
- Password management
- JWT token generation
- JWT validation
- Authentication
- Authorization
- Role management
- User profile management

Example roles:

```text
ROLE_USER
ROLE_ADMIN
```

The API Gateway and individual services can use authentication information to secure protected endpoints.


# 🔐 Security

Security is implemented as a dedicated concern rather than being embedded inside individual business workflows.

### Authentication

```text
User
 |
 | Login
 v
User Service
 |
 | JWT
 v
Client
```

Subsequent requests contain the JWT:

```http
Authorization: Bearer <JWT_TOKEN>
```

The API Gateway can validate authentication information before forwarding requests to downstream services.



# 🔗 Service Communication

Services communicate through well-defined APIs and service discovery and Feign Client.

Example:

```text
Booking Service
       |
       +---- Flight Ops Service
       |
       +---- Seat Service
       |
       +---- Pricing Service
       |
       +---- Ancillary Service
       |
       +---- Payment Service
```

# 🐳 Containerization

The platform is designed to support containerized execution using Docker.


```text
Docker Network
│
├── API Gateway
├── Config Server
├── Service Registry
│
├── User Service
├── Airline Core Service
├── Location Service
├── Flight Ops Service
├── Seat Service
├── Pricing Service
├── Ancillary Service
├── Booking Service
├── Payment Service
└── Notification Service
```


# 🛠️ Technology Stack

The platform is built around the following technologies:

| Technology | Purpose |
|---|---|
| **Java 17+** | Backend development |
| **Spring Boot** | Microservice development |
| **Spring Cloud** | Distributed system infrastructure |
| **Spring Cloud Gateway** | API Gateway |
| **Spring Cloud Config** | Centralized configuration |
| **Service Registry** | Service discovery |
| **Spring Security** | Authentication and authorization |
| **JWT** | Stateless authentication |
| **Spring Data JPA** | Persistence layer |
| **MySQL** | Relational persistence |
| **REST APIs** | Service communication |
| **Maven** | Build and dependency management |
| **Docker** | Containerization |
| **Swagger / OpenAPI** | API documentation |
| **Kafka / RabbitMQ** | Event-driven communication |



