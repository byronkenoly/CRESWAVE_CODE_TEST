## How to run application

### Prerequisites
1. Java Development Kit (JDK): Ensure you have JDK 21 installed
2. Maven: Ensure maven is installed to manage the project's dependencies
3. MySQL DB: Ensure MySQL v8 is installed in your machine
4. Git: Ensure you have git installed to be able to clone the repository

### Step by step instructions
1. Clone the repository
`git clone https://github.com/byronkenoly/CRESWAVE_CODE_TEST.git`

2. Navigate to the project directory `cd CRESWAVE_CODE_TEST`
3. Access MySQL Shell in terminal and run the following commands:

    ``` sql
    CREATE DATABASE blog;
    CREATE USER 'your_username'@'localhost' IDENTIFIED BY 'your_password';
    GRANT ALL ON blog.* TO 'your_username'@'localhost';
    ```
   replace `your_username` and `your_password` with your desired username and password

4. Change username and password in the `application.yml` file found in the path `src/main/resources/application.yml`
5. Install dependencies `mvn clean install`.
This will install dependencies specified in the `pom.xml` file
6. Run the Spring Boot App `mvn spring-boot:run`

## API Endpoints Documentation

## Register User

### POST /api/auth/register

This endpoint allows users to register by providing their name, email, and password.

#### Request Body

- `name` (string, required): The name of the user.
- `email` (string, required): The email address of the user.
- `password` (string, required): The password for the user account.


#### Response

The response for this request is a JSON object with the following schema:

``` json
{
  "type": "object",
  "properties": {
    "userId": {
      "type": "string"
    },
    "message": {
      "type": "string"
    }
  }
}

 ```

## Authenticate User

### POST /api/auth/authenticate

This endpoint allows users to authenticate and obtain a token for accessing protected resources.

**Request Body**

- `email` (string, required): The email of the user.
- `password` (string, required): The password of the user.


**Response**

- Status: 200 OK
- Content-Type: application/json
- `token` (string): The authentication token.

## Create Post

### POST /api/posts

This endpoint allows you to create a new post by sending an HTTP POST request to {{host}}:{{port}}/api/posts. The request should include a JSON payload in the raw request body type with the keys "title" and "content".

#### Request Body

- `title`: (string) The title of the post.
- `content`: (string) The content of the post.


#### Response

- Status: 200
- Content-Type: text/plain
- Body: "post created"

## View Posts

### GET /api/posts

This endpoint retrieves a list of posts.

#### Request

This is a GET request to fetch the list of posts.

#### Response

The response will be a JSON object with the following schema:

``` json
{
  "type": "object",
  "properties": {
    "totalPages": {
      "type": "number"
    },
    "totalElements": {
      "type": "number"
    },
    "pageable": {
      "type": "object",
      "properties": {
        "pageNumber": {
          "type": "number"
        },
        "pageSize": {
          "type": "number"
        },
        "sort": {
          "type": "object",
          "properties": {
            "sorted": {
              "type": "boolean"
            },
            "empty": {
              "type": "boolean"
            },
            "unsorted": {
              "type": "boolean"
            }
          }
        },
        "offset": {
          "type": "number"
        },
        "paged": {
          "type": "boolean"
        },
        "unpaged": {
          "type": "boolean"
        }
      }
    },
    "first": {
      "type": "boolean"
    },
    "last": {
      "type": "boolean"
    },
    "size": {
      "type": "number"
    },
    "content": {
      "type": "array",
      "items": {
        "type": "object",
        "properties": {
          "title": {
            "type": "string"
          },
          "content": {
            "type": "string"
          },
          "comments": {
            "type": "array"
          },
          "postId": {
            "type": "number"
          },
          "postBy": {
            "type": "string"
          }
        }
      }
    },
    "number": {
      "type": "number"
    },
    "sort": {
      "type": "object",
      "properties": {
        "sorted": {
          "type": "boolean"
        },
        "empty": {
          "type": "boolean"
        },
        "unsorted": {
          "type": "boolean"
        }
      }
    },
    "numberOfElements": {
      "type": "number"
    },
    "empty": {
      "type": "boolean"
    }
  }
}

 ```

## View Post

### GET /api/posts{id}

This endpoint makes an HTTP GET request to retrieve the details of a  post with ID with is passed in the params. The request does not include a request body.

## Edit Post

### PATCH /api/posts/{id}

This endpoint is used to update a specific post with the given ID.

### Request

- Method: `PATCH`
- URL: `{{host}}:{{port}}/api/posts/{id}`
- Body:
    - `title` (string, required): The updated title of the post.

### Response

- Status: 200
- Content-Type: text/plain


The response body is a text/plain content type with the message "post updated".

### JSON Schema

``` json
{
  "type": "object",
  "properties": {
    "status": {
      "type": "number",
      "description": "The status code of the response"
    },
    "message": {
      "type": "string",
      "description": "A message indicating the result of the update operation"
    }
  }
}

 ```

## Delete Post

### DELETE /api/posts/{id}

This endpoint is used to delete a specific post by providing its ID.

#### Request

- Method: DELETE
- Endpoint: `{{host}}:{{port}}/api/posts/1`


#### Response

- Status: 200
- Content-Type: text/plain
- Body: "post deleted"

## Create Comment

### POST /api/comments

The API endpoint allows the creation of comments via an HTTP POST request to {{host}}:{{port}}/api/comments.

### Request Body

- The request body should be in raw format and include the following parameters:
    - `postId` (number): The ID of the post to which the comment belongs.
    - `content` (string): The content of the comment.

### Response

The response to the request has a status code of 200 and a content type of text/plain. The response body indicates that the comment was successfully created.

### JSON Schema for Response

``` json
{
  "type": "object",
  "properties": {
    "status": {
      "type": "integer",
      "description": "The status code of the response"
    },
    "message": {
      "type": "string",
      "description": "A message indicating the result of the request"
    }
  }
}

 ```

## Update Comment

### PATCH /api/comments/{id}

This endpoint is used to update a specific comment.

#### Request

- Method: PATCH
- Endpoint: `{{host}}:{{port}}/api/comments/1`
- Body (raw, JSON):

    ``` json
    {
      "content": ""
    }
     ```


#### Response

The response for this request is a JSON schema with the following properties:

- `status` (number): The HTTP status code of the response.
- `message` (string): A message indicating the result of the update operation.


Example:

``` json
{
  "status": 200,
  "message": "comment updated"
}

 ```

## Delete Comment

### DELETE /api/comments/{id}

This endpoint is used to delete a specific comment.

#### Request Body

This request does not require a request body.

#### Response

- Status: 200
- Content-Type: text/plain
- Body: "comment deleted"