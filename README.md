# User Directory API


## Overview

A REST API for managing user data by loading from an external API and performing CRUD operations. Features include:
- Loading user data from external API into H2 database
- Retrieving all users
- Retrieve users with filtering(ROLE) and sorting(AGE)
- Search users by ID or SSN

**Base URL:** `https://user-directory-service.onrender.com/`

## API Endpoints

### 🚀 Load Users into Database

**`POST /load`**

Fetch user data from external API and store in database.

**Example Request:**
```http
POST /api/users/load
```

**Response:**

```
{
  "message": "Successfully loaded ** users into the database."
}
```

### Status Codes

200 OK: Users loaded successfully

500 Internal Server Error: Loading failed

### 📄 Get All Users
**`GET /`**

Retrieve all users from the database.

**Example Request**:
```http
GET /api/users
```
**Example Response** 📋
```json
[
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "ssn": "123-45-6789",
    "age": 30,
    "role": "Developer"
  },
  ...
]
```

### Status Codes:

200 OK: Users found

204 No Content: No users available


### 🔍 Get Users by Role
**`GET /role/{role}`**

Filter users by their role.

**Parameters:**

| Name | Type   | Description          |
|------|--------|----------------------|
| role | String | User role to filter  |

**Example Request**:

```http
GET /api/users/role/admin
```
**Example Response** 📋
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "role": "ADMIN",
    "age": 30,
    "ssn": "123-45-6789"
  },
  ...
]

```

### Status Codes:

200 OK: Users found

204 No Content: No users with specified role

500 Internal Server Error: Server error


### 🔄 Get Sorted Users by Age
**`GET /sorted`**

Retrieve users sorted by age.

**Parameters:**

| Name    | Type   | Default | Description               |
|---------|--------|---------|---------------------------|
| order   | String | asc     | Sorting order (asc/desc)  |

**Example Request**:

```http
GET /api/users/sorted?order=desc
```

Example Response:
```json
[
  {
    "id": 5,
    "age": 45,
    ...
  },
  {
    "id": 2,
    "age": 32,
    ...
  }
]
```

### 🔎 Get User by ID
**`GET /id/{id}`**

Retrieve single user by unique identifier.

**Parameters:**

| Name | Type | Description       |
|------|------|-------------------|
| id   | Long | User ID to search |


**Example Request:**

```http
GET /api/users/id/1
```

**Example Response:**
```json
  {
    "id": 5,
    "age": 45,
    ...
  }
```


### 🔍 Get User by SSN
**`GET /ssn/{ssn}`**

Retrieve user by Social Security Number.

**Parameters:**

| Name | Type   | Description       |
|------|--------|-------------------|
| ssn  | String | SSN to search for |


**Example Request:**

```http
GET /api/users/ssn/123-45-6789
```
**Example Response:**
```json
  {
    "id": 5,
    "age": 45,
    "ssn": "123-45-6789"
    ...
  }
```

***Sample error response:***
```
{
  "status": 404,
  "message": "Not found"
}
```

### Notes
- **Cross-Origin Resource Sharing (CORS):** The API allows requests from any origin.
- **Logging:** Each endpoint logs its request and response flow, which is useful for debugging and tracing issues.
- **Swagger Documentation:** The API includes Swagger annotations for automatic API documentation generation.
- **Database:**  
  - **H2 Database:** Initially used for persistence during development.  
  - **PostgreSQL (Cloud):** Currently configured to use PostgreSQL, deployed in the cloud, for production-level persistence.

### Swagger Endpoints

The API provides Swagger documentation, which can be accessed using the following endpoints:

| Endpoint           | Method | Description                                                      |
|--------------------|--------|------------------------------------------------------------------|
| `/swagger-ui.html` | GET    | Interactive Swagger UI for exploring the API endpoints.          |
| `/v3/api-docs`     | GET    | JSON representation of the OpenAPI specification for the API.    |


