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

json
Copy
{
  "message": "Successfully loaded 50 users into the database."
}
Status Codes:
