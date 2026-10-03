# Restful Booker – Java API Test Framework

This is a small interview-oriented API automation framework using:

- Java 21
- Maven
- TestNG
- REST Assured
- DTOs / POJOs
- RequestSpecification
- API Client pattern
- OpenAPI contract example
- Jenkins Pipeline

## Project structure

```text
src/
└── test/
    ├── java/
    │   └── api/
    │       ├── base/
    │       │   └── BaseApiTest.java
    │       ├── specs/
    │       │   └── RequestSpecFactory.java
    │       ├── clients/
    │       │   └── BookingApiClient.java
    │       ├── dto/
    │       │   ├── BookingRequestDto.java
    │       │   ├── BookingResponseDto.java
    │       │   └── BookingDatesDto.java
    │       ├── data/
    │       │   └── BookingTestData.java
    │       └── tests/
    │           └── BookingApiTest.java
    └── resources/
        └── openapi.yaml

Jenkinsfile
pom.xml
```

The original example structure used `UserApiClient` / `User*Dto`. Because the selected practice API is **Restful Booker**, the domain is actually `Booking`, so the example uses booking-specific names instead of teaching a misleading `User` abstraction.

## API under test

Default base URL:

```text
https://restful-booker.herokuapp.com
```

The included tests use:

```text
GET  /booking
GET  /booking/{id}
POST /booking
```

The public API returns booking data from these endpoints; the create-booking response includes a booking ID and the created booking object.

## Run locally

From the project root:

```bash
mvn clean test
```

You can override the base URL:

```bash
mvn clean test -DbaseUrl=https://restful-booker.herokuapp.com
```

Surefire reports are written to:

```text
target/surefire-reports/
```

## Framework responsibilities

```text
BookingApiTest
    ↓
BookingApiClient
    ↓
RequestSpecFactory
    ↓
REST Assured
    ↓
Restful Booker API
```

- `BaseApiTest`: common test setup
- `RequestSpecFactory`: creates common REST Assured request configuration
- `BookingApiClient`: encapsulates endpoint calls
- `DTOs`: request/response data models
- `BookingTestData`: reusable test data
- `BookingApiTest`: assertions and test scenarios
- `openapi.yaml`: simplified API contract reference
- `Jenkinsfile`: CI pipeline

## Jenkins

Create a Jenkins Pipeline job with:

```text
Definition:
Pipeline script from SCM

SCM:
Git

Branch:
*/main

Script Path:
Jenkinsfile
```

The Jenkinsfile expects a Maven installation named:

```text
Maven-3.9.16
```

The pipeline runs:

```text
Checkout
   ↓
mvn clean test
   ↓
TestNG
   ↓
Surefire XML reports
```

## Notes

Restful Booker is a public practice API. Availability, data, and response behavior can change or reset, so a failed live test does not automatically mean the test code is wrong. The tests intentionally avoid depending on one permanent booking ID by taking an existing ID from `GET /booking`.
