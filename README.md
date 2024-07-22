# GitHub Repo List

## Table of Contents
1. [Introduction](#introduction)
2. [Installation](#installation)
3. [Usage](#usage)
4. [Endpoint](#endpoint)
5. [Error handling](#error-handling)
6. [Features](#features)
7. [Dependencies](#dependencies)
8. [Configuration](#configuration)
9. [Example](#example)
10. [Troubleshooting](#troubleshooting)
11. [Contributor](#contributor)
12. [License](#license)

## Introduction
GitHub Repo List is a Java-based application built using Spring WebFlux to fetch and display information about GitHub repositories with branches. This application also showcases the use of reactive programming principles provided by Spring WebFlux, enabling efficient handling of asynchronous, non-blocking data streams.

## Installation
### Prerequisites
- Java Development Kit (JDK) 21 or higher
- Apache Maven 3.9.8 or higher

### Steps
1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/Github-repo-list.git
    cd Github-repo-list
    ```

   2. Build the project using Maven:
    
   ```sh
   mvn clean install
   ```

3. Run the application:

    ```sh
    mvn spring-boot:run
    ```

## Usage
Once the application is running, you can access it through address http://localhost:8080. It provides endpoints to fetch information about GitHub repositories.

## Endpoint
- `/github/repos/non-fork/`: Fetches repositories for the specified GitHub username.
  - Param: `username`, type: `String`
  - Required header: `Accept: application/json`
  - Returns list of given `GithubRepositoryResponse` objects:
    ```json 
    [
      {
        "repositoryName": "String",
        "ownerLogin": "String",
        "branches": [
          {
            "branchName": "String",
            "lastCommitSha": "String"
          },
          // More GithubBranchDto objects...
        ]
      },
      // More GithubRepositoryResponse objects...
    ]
    ```

## Error Handling
The `GlobalControllerAdvice` class handles various exceptions that may occur during the execution of the application. Below are the errors that can be returned and their corresponding HTTP status codes:

1. **GithubNotFoundException**
    - **Description**: This exception is thrown when the requested GitHub repository or user is not found.
    - **HTTP Status**: Not Found 404.
    - **Log Message**: "GithubNotFoundException occurred: [exception message]"

2. **GithubForbiddenException**
    - **Description**: This exception is thrown when access to the requested GitHub resource is forbidden, possibly due to reach requests rate limit.
    - **HTTP Status**: Forbidden 403.
    - **Log Message**: "GithubForbiddenException occurred: [exception message]"

3. **IllegalArgumentException**
    - **Description**: This exception is thrown when an illegal or inappropriate argument is passed to a method, like empty username.
    - **HTTP Status**: 400 Bad Request
    - **Log Message**: "IllegalArgumentException occurred: [exception message]"

4. **ExternalApiException**
    - **Description**: This exception is thrown when an unknown error occurs while interacting with an external API.
    - **HTTP Status**: Bad Gateway 502.
    - **Log Message**: "Unknown external API exception occurred: [exception message]"

For each exception, an `ErrorResponse` object is returned in the response body containing the HTTP status and the exception message. The errors are also logged for further analysis and debugging.

## Features
- Fetch information about GitHub repositories.
- Efficient handling of asynchronous data streams.
- High performance by handling many requests at once without slowing down, thanks to its non-blocking, asynchronous nature of Webflux

## Dependencies
- Java 21 or higher
- Spring Boot 3.3.1 or higher
- Spring WebFlux 3.1.12 or higher
- Maven 3.9.8 or higher

## Configuration
To configure the application, you can use the application.properties file located in `src/main/resources`.

### Adding GITHUB_TOKEN
Although adding a `GITHUB_TOKEN` is optional, it is recommended for accessing GitHub API with higher rate limits.

1. Open the application.properties file.
2. Add the following line with your GitHub token:
    ```properties
    github.access-token=YOUR_GITHUB_TOKEN
    ```

    Alternatively, you can set the token as an environment variable:
    ### Windows
    ```sh
    SETX GITHUB_TOKEN "YOUR_GITHUB_TOKEN"
    ```
   
    ### Linux
    ```bash
    export GITHUB_TOKEN=YOUR_GITHUB_TOKEN
    ```

## Example
Here is example to get you started:

### Fetch non Repositories
   ```sh
    curl.exe -H "Accept: application/json" http://localhost:8080/github/repos/non-fork/?username=octocat
   ```

## Troubleshooting
If you encounter any issues during the setup or usage of the application, refer to the following steps:

- Ensure that JDK and Maven are installed correctly.
- Verify that you have a stable internet connection to fetch dependencies.

## Contributor
- [Dominik Stachowiak](https://www.linkedin.com/in/dominik-stachowiak/)

## License
This project is licensed under the MIT License