# PetStore API Automation Tests

## Covered endpoints

- `POST /pet` — create pet
- `GET /pet/{petId}` — get pet by id
- `DELETE /pet/{petId}` — delete pet by id

## Test coverage

Implemented checks include:

- positive and negative test cases
- HTTP status code validation
- key response fields validation
- JSON Schema validation
- data-driven tests with TestNG DataProvider

## Tech stack

- Java 17
- Spring
- Gradle
- REST Assured
- TestNG
- AssertJ
- Allure Report
- Jackson
- Lombok
- Log4j2

## How to run tests

```bash
./gradlew clean :pet-api-tests:test
```

## How to use Allure report

## Generate report:
```bash
./gradlew :pet-api-tests:allureReport
```
## Open report:
```bash
./gradlew :pet-api-tests:allureServe
```

## Additional Approaches Used:
- Multi-module project structure
- Separate API client layer
- Service layer for reusable business actions
- DTO models
- Test Data Factory pattern to reduce code duplication
- TestNG DataProviders for parameterized negative scenarios
- JSON Schema validation for contract verification
- Soft Assertions for response validation
- Request and response logging
- Allure annotations (@Step, @Severity, @Description)
- Generic API client implementation
- Reusable response mapping through ApiResult wrapper


## Known issues / observations:
- POST /pet returns 200 OK instead of 201 Created.
- POST /pet accepts payloads without required fields and still returns 200 OK.
- DELETE /pet/{petId} returns 404 with empty response body for non-existing pet.

## Notes !!!!
- Some negative tests are intentionally failing 
- because they expose actual issues in the public PetStore API implementation 
- and are included to demonstrate defect detection capabilities.