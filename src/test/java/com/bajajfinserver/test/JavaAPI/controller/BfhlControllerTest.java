package com.bajajfinserver.test.JavaAPI.controller;

import com.bajajfinserver.test.JavaAPI.dto.DataRequest;
import com.bajajfinserver.test.JavaAPI.dto.DataResponse;
import com.bajajfinserver.test.JavaAPI.service.DataProcessingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BfhlController.class)
class BfhlControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private DataProcessingService dataProcessingService;
    
    private DataResponse mockResponse;
    
    @BeforeEach
    void setUp() {
        mockResponse = DataResponse.builder()
                .isSuccess(true)
                .requestId("REQ-1001")
                .oddNumbers(Arrays.asList("1", "7"))
                .evenNumbers(Arrays.asList("22"))
                .alphabets(Arrays.asList("A", "B"))
                .specialCharacters(Arrays.asList("$"))
                .sum("30")
                .largestNumber("22")
                .smallestNumber("1")
                .alphabetCount(2)
                .numberCount(3)
                .specialCharacterCount(1)
                .containsDuplicates(false)
                .processingTimeMs(15L)
                .alphabetFrequency(Collections.singletonMap("A", 1))
                .uniqueElementCount(6)
                .sortedNumbers(Arrays.asList("1", "7", "22"))
                .vowelCount(1)
                .consonantCount(1)
                .longestAlphabeticValue("A")
                .shortestAlphabeticValue("A")
                .summary(DataResponse.Summary.builder()
                        .totalElementsReceived(6)
                        .validElementsProcessed(6)
                        .invalidElementsIgnored(0)
                        .build())
                .build();
    }
    
    @Test
    @DisplayName("POST /bfhl - Successful processing with X-Request-Id header")
    void testPostWithRequestIdHeader() throws Exception {
        DataRequest request = new DataRequest(Arrays.asList("A", "1", "22", "$", "B", "7"));
        
        when(dataProcessingService.processData(any(DataRequest.class), eq("REQ-1001")))
                .thenReturn(mockResponse);
        
        mockMvc.perform(post("/bfhl")
                .header("X-Request-Id", "REQ-1001")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Request-Id", "REQ-1001"))
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.request_id").value("REQ-1001"))
                .andExpect(jsonPath("$.odd_numbers[0]").value("1"))
                .andExpect(jsonPath("$.even_numbers[0]").value("22"))
                .andExpect(jsonPath("$.alphabets[0]").value("A"))
                .andExpect(jsonPath("$.sum").value("30"));
        
        verify(dataProcessingService, times(1)).processData(any(DataRequest.class), eq("REQ-1001"));
    }
    
    @Test
    @DisplayName("POST /bfhl - Without X-Request-Id header (auto-generated)")
    void testPostWithoutRequestIdHeader() throws Exception {
        DataRequest request = new DataRequest(Arrays.asList("A", "1", "22", "$", "B", "7"));
        
        ArgumentCaptor<String> requestIdCaptor = ArgumentCaptor.forClass(String.class);
        when(dataProcessingService.processData(any(DataRequest.class), anyString()))
                .thenReturn(mockResponse);
        
        mockMvc.perform(post("/bfhl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true));
        
        verify(dataProcessingService, times(1)).processData(any(DataRequest.class), requestIdCaptor.capture());
        String capturedRequestId = requestIdCaptor.getValue();
        assert capturedRequestId.startsWith("REQ-");
    }
    
    @Test
    @DisplayName("POST /bfhl - Invalid request (null data)")
    void testPostWithNullData() throws Exception {
        String invalidJson = "{}";
        
        mockMvc.perform(post("/bfhl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success").value(false))
                .andExpect(jsonPath("$.message").value("Validation failed"));
        
        verify(dataProcessingService, never()).processData(any(), anyString());
    }
    
    @Test
    @DisplayName("POST /bfhl - Invalid JSON format")
    void testPostWithInvalidJson() throws Exception {
        String invalidJson = "{invalid json}";
        
        mockMvc.perform(post("/bfhl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());
        
        verify(dataProcessingService, never()).processData(any(), anyString());
    }
    
    @Test
    @DisplayName("POST /bfhl - Empty data array")
    void testPostWithEmptyDataArray() throws Exception {
        DataRequest request = new DataRequest(Collections.emptyList());
        
        DataResponse emptyResponse = DataResponse.builder()
                .isSuccess(true)
                .requestId("REQ-TEST")
                .oddNumbers(Collections.emptyList())
                .evenNumbers(Collections.emptyList())
                .alphabets(Collections.emptyList())
                .specialCharacters(Collections.emptyList())
                .sum("0")
                .numberCount(0)
                .alphabetCount(0)
                .specialCharacterCount(0)
                .containsDuplicates(false)
                .processingTimeMs(5L)
                .build();
        
        when(dataProcessingService.processData(any(DataRequest.class), anyString()))
                .thenReturn(emptyResponse);
        
        mockMvc.perform(post("/bfhl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.number_count").value(0));
        
        verify(dataProcessingService, times(1)).processData(any(DataRequest.class), anyString());
    }
    
    @Test
    @DisplayName("GET /bfhl - Operation code endpoint")
    void testGetOperationCode() throws Exception {
        mockMvc.perform(get("/bfhl"))
                .andExpect(status().isOk())
                .andExpect(content().string("Operation successful"));
    }
    
    @Test
    @DisplayName("POST /bfhl - Request ID passed to service correctly")
    void testRequestIdPassedCorrectly() throws Exception {
        DataRequest request = new DataRequest(Arrays.asList("A", "1"));
        String expectedRequestId = "REQ-CUSTOM-123";
        
        ArgumentCaptor<String> requestIdCaptor = ArgumentCaptor.forClass(String.class);
        when(dataProcessingService.processData(any(DataRequest.class), anyString()))
                .thenReturn(mockResponse);
        
        mockMvc.perform(post("/bfhl")
                .header("X-Request-Id", expectedRequestId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        
        verify(dataProcessingService).processData(any(DataRequest.class), requestIdCaptor.capture());
        assertEquals(expectedRequestId, requestIdCaptor.getValue());
    }
    
    @Test
    @DisplayName("POST /bfhl - Content type validation")
    void testContentTypeValidation() throws Exception {
        mockMvc.perform(post("/bfhl")
                .content("plain text data"))
                .andExpect(status().isUnsupportedMediaType());
        
        verify(dataProcessingService, never()).processData(any(), anyString());
    }
}
