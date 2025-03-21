# User Directory API

## Overview

A REST API for managing user data by loading from an external API and performing CRUD operations. Features include:
- Loading user data from external API into H2 database
- Retrieve users with filtering and sorting
- Search users by ID or SSN

**Base URL:** `http://localhost:8080/api/users`

## API Endpoints

### 🚀 Load Users into Database

**`POST /load`**

Fetch user data from external API and store in database.

**Example Request:**
```http
POST /api/users/load

Response:

{
  "message": "Successfully loaded 50 users into the database."
}
Status Codes:

Status Codes:

200 OK: Users loaded successfully

500 Internal Server Error: Loading failed

📄 Get All Users
GET /

Retrieve all users from the database.

Example Request:

http
Copy
GET /api/users
Example Response:

json
Copy
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
Status Codes:

200 OK: Users found

204 No Content: No users available

🔍 Get Users by Role
GET /role/{role}

Filter users by their role.

Parameters:

Name	Type	Description
role	String	User role to filter
Example Request:

http
Copy
GET /api/users/role/Developer
Status Codes:

200 OK: Users found

204 No Content: No users with specified role

500 Internal Server Error: Server error

🔄 Get Sorted Users by Age
GET /sorted

Retrieve users sorted by age.

Parameters:

Name	Type	Description	Default
order	String	Sorting order (asc/desc)	asc
Example Request:

http
Copy
GET /api/users/sorted?order=desc
Example Response:

json
Copy
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
🔎 Get User by ID
GET /id/{id}

Retrieve single user by unique identifier.

Parameters:

Name	Type	Description
id	Long	User ID to search
Example Request:

http
Copy
GET /api/users/id/1
🔍 Get User by SSN
GET /ssn/{ssn}

Retrieve user by Social Security Number.

Parameters:

Name	Type	Description
ssn	String	SSN to search for
Example Request:

http
Copy
GET /api/users/ssn/123-45-6789
Error Handling
Sample error response:

json
Copy
{
  "status": 500,
  "message": "Failed to load users. Error: Connection timeout"
}
Common Status Codes:

400 Bad Request: Invalid parameters

404 Not Found: Resource not found

500 Internal Server Error: Server-side error

Note: All endpoints support CORS and are accessible from any origin
