# JavaAPI - Data Processing REST API

Spring Boot REST API for D.Y.Patil Campus Hiring (June 2026) - API Round

## Assignment Overview

![Assignment Page 1](docs/assignment-1.png)
![Assignment Page 2](docs/assignment-2.png)
![Assignment Page 3](docs/assignment-3.png)
![Assignment Page 4](docs/assignment-4.png)

## Quick Start

```bash
# Build & Run
.\mvnw.cmd spring-boot:run

# Test
curl http://localhost:8080/
```

## API Endpoints

### 1. GET / - Welcome
```bash
curl http://localhost:8080/
```
See: [outputs/welcome-response.json](outputs/welcome-response.json)

### 2. GET /health - Health Check
```bash
curl http://localhost:8080/health
```
See: [outputs/health-response.json](outputs/health-response.json)

### 3. POST /bfhl - Process Data
```bash
curl -X POST http://localhost:8080/bfhl \
  -H "Content-Type: application/json" \
  -H "X-Request-Id: REQ-1001" \
  -d '{"data": ["A", "1", "22", "$", "B", "7"]}'
```

## Example Outputs

### Example 1: Basic Input
**Input:** `["A", "1", "22", "$", "B", "7"]`  
**Output:** [example1-response.json](outputs/example1-response.json)

![Example 1 Output](docs/example-1-output.png)

### Example 2: Alphanumeric Strings
**Input:** `["A1B2", "100", "#", "Test123", "Z", "55"]`  
**Output:** [example2-response.json](outputs/example2-response.json)

![Example 2 Output](docs/example-2-output.png)

### Example 3: Null/Empty Handling
**Input:** `["10", "-10", "A", "A", "", null, "&", "-5"]`  
**Output:** [example3-response.json](outputs/example3-response.json)

![Example 3 Output](docs/example-3-output.png)

### Example 4: Decimal & Negative Numbers
**Input:** `["-10", "25.5", "-100.75", "B", "@", "5", "A9"]`  
**Output:** [example4-response.json](outputs/example4-response.json)

![Example 4 Output](docs/example-4-output.png)

### Example 5: Complex Input
**Input:** `["ABC", "123", "A1B2", "$", "%", "-50", "@", "xyz", "", null, "999", "Test99", "&"]`  
**Output:** [example5-response.json](outputs/example5-response.json)

![Example 5 Output](docs/example-5-output.png)

## Features

✅ Processes mixed arrays (numbers, alphabets, special chars)  
✅ Extracts data from alphanumeric strings  
✅ Handles negative & decimal numbers  
✅ Filters null/empty values  
✅ Alphabet frequency analysis  
✅ Duplicate detection  
✅ Vowel/consonant counting  
✅ 36 test cases with >80% coverage  

## Architecture

```
Controller → Service → DTO
```

- **Layered architecture** (Controller, Service, DTO, Exception)
- **Global exception handling** (@ControllerAdvice)
- **Bean Validation** for input validation
- **Structured logging** (SLF4J)

## Testing

```bash
# Run all tests
.\mvnw.cmd test

# Automated API test
test-api.bat
```

**Test Coverage:** 36 tests, >80% service layer coverage

## Docker Deployment

```bash
# Build
docker build -t javaapi .

# Run
docker run -p 8080:8080 javaapi
```

## Deploy to Render

1. Push code to GitHub
2. Go to [Render Dashboard](https://render.com)
3. Create Web Service → Select Docker
4. Deploy!

See [DEPLOYMENT.md](DEPLOYMENT.md) for details.

## Tech Stack

- Java 21
- Spring Boot 3.5.15
- Maven
- JUnit 5 & Mockito
- Docker

## Project Structure

```
src/
├── main/java/.../JavaAPI/
│   ├── controller/      # REST endpoints
│   ├── service/         # Business logic
│   ├── dto/             # Request/Response
│   └── exception/       # Error handling
└── test/                # 36 test cases

outputs/                 # Sample responses
docs/                    # Assignment images
```

## Documentation

- [QUICKSTART.md](QUICKSTART.md) - Quick start guide
- [TESTING.md](TESTING.md) - Testing guide
- [DEPLOYMENT.md](DEPLOYMENT.md) - Deployment guide
- [ARCHITECTURE.md](ARCHITECTURE.md) - Architecture details

## License

Educational/Interview Project - D.Y.Patil Campus Hiring 2026
