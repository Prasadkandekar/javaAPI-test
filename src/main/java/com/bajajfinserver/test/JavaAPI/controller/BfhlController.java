package com.bajajfinserver.test.JavaAPI.controller;

import com.bajajfinserver.test.JavaAPI.dto.DataRequest;
import com.bajajfinserver.test.JavaAPI.dto.DataResponse;
import com.bajajfinserver.test.JavaAPI.service.DataProcessingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bfhl")
@RequiredArgsConstructor
@Slf4j
public class BfhlController {
    
    private final DataProcessingService dataProcessingService;
    
    @PostMapping
    public ResponseEntity<DataResponse> processData(
            @Valid @RequestBody DataRequest request,
            @RequestHeader(value = "X-Request-Id", required = false) String requestId) {
        
        // Generate request ID if not provided
        if (requestId == null || requestId.trim().isEmpty()) {
            requestId = "REQ-" + System.currentTimeMillis();
        }
        
        log.info("Received POST request with X-Request-Id: {}", requestId);
        
        DataResponse response = dataProcessingService.processData(request, requestId);
        
        return ResponseEntity.status(HttpStatus.OK)
                .header("X-Request-Id", requestId)
                .body(response);
    }
    
    @GetMapping
    public ResponseEntity<String> getOperationCode() {
        log.info("Received GET request");
        return ResponseEntity.ok("Operation successful");
    }
}
