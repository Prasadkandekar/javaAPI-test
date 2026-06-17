# JavaAPI - Data Processing REST API

Spring Boot REST API for D.Y.Patil Campus Hiring (June 2026) - API Round

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

### 2. GET /health - Health Check
```bash
curl http://localhost:8080/health
```

### 3. POST /bfhl - Process Data
```bash
curl -X POST http://localhost:8080/bfhl \
  -H "Content-Type: application/json" \
  -H "X-Request-Id: REQ-1001" \
  -d '{"data": ["A", "1", "22", "$", "B", "7"]}'
```

## API Response Examples

### Example 1: Basic Mixed Input
![Example 1](outputs/out1.png)

### Example 2: Alphanumeric Strings
![Example 2](outputs/out2.png)

### Example 3: Null & Empty Handling
![Example 3](outputs/out3.png)

### Example 4: Decimal & Negative Numbers
![Example 4](outputs/out4.png)

### Example 5: Complex Input
![Example 5](outputs/out5.png)

### Example 6: Additional Test
![Example 6](outputs/out6.png)

## Features

✅ Processes mixed arrays (numbers, alphabets, special characters)  
✅ Extracts data from alphanumeric strings (e.g., "A1B2" → numbers: 1,2; alphabets: A,B)  
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

**Layers:**
- **Controller** - REST endpoints (BfhlController, WelcomeController)
- **Service** - Business logic (DataProcessingServiceImpl)
- **DTO** - Request/Response models (DataRequest, DataResponse)
- **Exception** - Global error handling (@ControllerAdvice)

## Testing

```bash
# Run all tests (36 tests)
.\mvnw.cmd test

# Automated API test script
test-api.bat
```

**Test Coverage:** 36 tests, >80% service layer coverage

## Docker Deployment

```bash
# Build image
docker build -t javaapi .

# Run container
docker run -p 8080:8080 javaapi
```

## Deploy to Render

1. Push code to GitHub
2. Go to [Render Dashboard](https://render.com)
3. New Web Service → Select your repo
4. Environment: **Docker**
5. Click **Deploy**

Your API will be live at: `https://your-app.onrender.com`

See [DEPLOYMENT.md](DEPLOYMENT.md) for detailed guide.

## Tech Stack

- **Java 21**
- **Spring Boot 3.5.15**
- **Maven**
- **JUnit 5 & Mockito**
- **Docker**
- **Lombok**

## Project Structure

```
src/
├── main/java/.../JavaAPI/
│   ├── controller/      # REST endpoints
│   ├── service/         # Business logic
│   ├── dto/             # Request/Response
│   └── exception/       # Error handling
└── test/                # 36 test cases

outputs/                 # Sample API responses
Dockerfile              # Docker configuration
render.yaml             # Render deployment config
```

## Response Fields

| Field | Description |
|-------|-------------|
| `odd_numbers` | List of odd integers |
| `even_numbers` | List of even integers |
| `alphabets` | Extracted alphabetic strings |
| `special_characters` | Special characters |
| `sum` | Sum of all numbers |
| `largest_number` | Maximum value |
| `smallest_number` | Minimum value |
| `alphabet_frequency` | Letter frequency map |
| `vowel_count` | Total vowels |
| `consonant_count` | Total consonants |
| `contains_duplicates` | Duplicate flag |
| `sorted_numbers` | Numbers in ascending order |
| `processing_time_ms` | Processing time |

## Additional Documentation

- [QUICKSTART.md](QUICKSTART.md) - Quick start guide
- [TESTING.md](TESTING.md) - Comprehensive testing guide
- [DEPLOYMENT.md](DEPLOYMENT.md) - Deployment to Render/AWS/Railway
- [ARCHITECTURE.md](ARCHITECTURE.md) - System architecture
- [outputs/README.md](outputs/README.md) - Sample responses

## Sample JSON Responses

All example responses are available in the `outputs/` folder:
- [example1-response.json](outputs/example1-response.json)
- [example2-response.json](outputs/example2-response.json)
- [example3-response.json](outputs/example3-response.json)
- [example4-response.json](outputs/example4-response.json)
- [example5-response.json](outputs/example5-response.json)

## License

Educational/Interview Project - D.Y.Patil Campus Hiring 2026
