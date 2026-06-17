# Sample API Outputs

This folder contains example responses from the JavaAPI endpoints.

## Files

### 1. welcome-response.json
Response from `GET /`
- Welcome message
- API version
- Available endpoints

### 2. health-response.json
Response from `GET /health`
- Service status
- Timestamp
- Health check message

### 3. example1-response.json
Response from `POST /bfhl` with input: `["A", "1", "22", "$", "B", "7"]`
- Basic mixed input
- Demonstrates number categorization (odd/even)
- Alphabet and special character extraction

### 4. example2-response.json
Response from `POST /bfhl` with input: `["A1B2", "100", "#", "Test123", "Z", "55"]`
- Alphanumeric string processing
- Extracts numbers and alphabets from mixed strings
- Shows frequency analysis

### 5. example3-response.json
Response from `POST /bfhl` with input: `["10", "-10", "A", "A", "", null, "&", "-5"]`
- Handles null and empty values
- Duplicate detection (A appears twice)
- Summary object with ignored elements count

### 6. example4-response.json
Response from `POST /bfhl` with input: `["-10", "25.5", "-100.75", "B", "@", "5", "A9"]`
- Negative numbers
- Decimal numbers
- Correct sum calculation with decimals

## Key Features Demonstrated

✅ Number categorization (odd/even)
✅ Alphabet extraction and frequency
✅ Special character detection
✅ Alphanumeric string processing
✅ Negative and decimal number handling
✅ Null/empty value filtering
✅ Duplicate detection
✅ Vowel/consonant counting
✅ Processing time tracking
✅ Summary statistics

## How to Generate These Outputs

Run the application and use curl or Postman:

```bash
# Example 1
curl -X POST http://localhost:8080/bfhl \
  -H "Content-Type: application/json" \
  -d '{"data": ["A", "1", "22", "$", "B", "7"]}'

# Example 2
curl -X POST http://localhost:8080/bfhl \
  -H "Content-Type: application/json" \
  -d '{"data": ["A1B2", "100", "#", "Test123", "Z", "55"]}'
```

Or use the automated test script:
```bash
test-api.bat
```
