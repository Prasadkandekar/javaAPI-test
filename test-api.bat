@echo off
REM Test script for JavaAPI endpoints
echo ======================================
echo Testing JavaAPI Endpoints
echo ======================================

echo.
echo [1/6] Testing Example 1: Basic mixed input...
curl -X POST http://localhost:8080/bfhl -H "Content-Type: application/json" -H "X-Request-Id: REQ-1001" -d "{\"data\": [\"A\", \"1\", \"22\", \"$\", \"B\", \"7\"]}"

echo.
echo.
echo [2/6] Testing Example 2: Alphanumeric strings...
curl -X POST http://localhost:8080/bfhl -H "Content-Type: application/json" -H "X-Request-Id: REQ-1002" -d "{\"data\": [\"A1B2\", \"100\", \"#\", \"Test123\", \"Z\", \"55\"]}"

echo.
echo.
echo [3/6] Testing Example 3: With nulls and empty strings...
curl -X POST http://localhost:8080/bfhl -H "Content-Type: application/json" -H "X-Request-Id: REQ-1003" -d "{\"data\": [\"10\", \"-10\", \"A\", \"A\", \"\", null, \"&\", \"-5\"]}"

echo.
echo.
echo [4/6] Testing Example 4: Decimal and negative numbers...
curl -X POST http://localhost:8080/bfhl -H "Content-Type: application/json" -H "X-Request-Id: REQ-1004" -d "{\"data\": [\"-10\", \"25.5\", \"-100.75\", \"B\", \"@\", \"5\", \"A9\"]}"

echo.
echo.
echo [5/6] Testing Example 5: Complex input...
curl -X POST http://localhost:8080/bfhl -H "Content-Type: application/json" -H "X-Request-Id: REQ-1005" -d "{\"data\": [\"ABC\", \"123\", \"A1B2\", \"$\", \"%%\", \"-50\", \"@\", \"xyz\", \"\", null, \"999\", \"Test99\", \"&\"]}"

echo.
echo.
echo [6/6] Testing GET endpoint...
curl -X GET http://localhost:8080/bfhl

echo.
echo.
echo ======================================
echo All tests completed!
echo ======================================
