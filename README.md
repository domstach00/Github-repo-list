# GitHub Repo List

## Table of Contents
1. [Introduction](#introduction)
2. [Installation](#installation)
3. [Usage](#usage)
4. [Endpoint](#endpoint)
5. [Caching](#caching)
6. [Error handling](#error-handling)
7. [Features](#features)
8. [Dependencies](#dependencies)
9. [Configuration](#configuration)
10. [Example](#example)
11. [Troubleshooting](#troubleshooting)
12. [Contributor](#contributor)
13. [License](#license)

## Introduction
GitHub Repo List is a Java-based application built using Spring WebFlux to fetch and display information about GitHub repositories with branches. This application also showcases the use of reactive programming principles provided by Spring WebFlux, enabling efficient handling of asynchronous, non-blocking data streams.

## Installation
### Prerequisites
- Java Development Kit (JDK) 21 or higher
- Apache Maven 3.9.8 or higher
- Docker

### Steps
1. Clone the repository:
    ```sh
    git clone https://github.com/domstach00/Github-repo-list.git
    cd Github-repo-list
    ```

2. Build the project using Maven:
    
   ```sh
   mvn clean install
   ```
   
3. In separate terminal window create Redis docker image:
   ```sh
   cd docker
   docker-compose up --build
   ```

4. Run the application:
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

## Caching
This application uses caching to improve performance by reducing number of Github API requests. Caching is handled by Spring's Cache Abstraction and backend by Redis. 
- Cache configuration is defined in `CacheConfig` class.
- Cache server is running on Docker image, docker compose file is located in: `docker/docker-compose.yml`
- Time to live for cached records is defined in `application.properties`, by default it is set for 600 seconds.
- Out of the box Redis is working on SingleServer mode. It can be changed to Master-Slave, Cluster or Sentinel mode, but it requires changes in `CacheConfig` class and `docker/docker-compose.yml` file to create additional servers

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
- Reduced number of external API requests by adding Caching

## Dependencies
- Java 21 or higher
- Spring Boot 3.3.1 or higher
- Spring WebFlux 3.1.12 or higher
- Maven 3.9.8 or higher
- Docker

## Configuration
To configure the application, you can use the `application.properties` file located in `src/main/resources`, after making changes in this file you should rebuld the project using maven like in [installation process](#installation).

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

### Caching time to live
1. Open the application.properties file.
2. In following line you can define Time to live for records in Cache (in seconds):
    ```sh
   cache.expiration.time=TTL_IN_SECONDS
   ```


## Example
Here is example to get you started:

### Start application

1. Go to GithubRepoList location
   ```sh
   cd Github-repo-list
    ```
2. Start Docker
    ```sh
    docker start github-repo-list-redis
    ```
2. Start application
    ```sh
   mvn spring-boot:run
    ```

### Fetch non-fork Repositories for given username
   ```sh
    curl.exe -H "Accept: application/json" http://localhost:8080/github/repos/non-fork/?username=octocat
   ```

## Troubleshooting
If you encounter any issues during the setup or usage of the application, refer to the following steps:

- Ensure that JDK and Maven are installed correctly.
- Verify that you have a stable internet connection to fetch dependencies.
- Verify that Redis server is working properly

## Contributor
- [Dominik Stachowiak](https://www.linkedin.com/in/dominik-stachowiak/)

## License
This project is licensed under the MIT License
