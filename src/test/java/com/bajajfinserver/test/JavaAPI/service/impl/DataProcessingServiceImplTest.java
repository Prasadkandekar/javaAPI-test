package com.bajajfinserver.test.JavaAPI.service.impl;

import com.bajajfinserver.test.JavaAPI.dto.DataRequest;
import com.bajajfinserver.test.JavaAPI.dto.DataResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataProcessingServiceImplTest {
    
    private DataProcessingServiceImpl dataProcessingService;
    
    @BeforeEach
    void setUp() {
        dataProcessingService = new DataProcessingServiceImpl();
    }
    
    @Test
    @DisplayName("Test Example 1: Basic mixed input")
    void testExample1() {
        DataRequest request = new DataRequest(Arrays.asList("A", "1", "22", "$", "B", "7"));
        DataResponse response = dataProcessingService.processData(request, "REQ-1001");
        
        assertTrue(response.isSuccess());
        assertEquals("REQ-1001", response.getRequestId());
        assertEquals(Arrays.asList("1", "7"), response.getOddNumbers());
        assertEquals(Arrays.asList("22"), response.getEvenNumbers());
        assertEquals(Arrays.asList("A", "B"), response.getAlphabets());
        assertEquals(Arrays.asList("$"), response.getSpecialCharacters());
        assertEquals("30", response.getSum());
        assertEquals("22", response.getLargestNumber());
        assertEquals("1", response.getSmallestNumber());
        assertEquals(2, response.getAlphabetCount());
        assertEquals(3, response.getNumberCount());
        assertEquals(1, response.getSpecialCharacterCount());
        assertFalse(response.isContainsDuplicates());
        assertNotNull(response.getProcessingTimeMs());
    }
    
    @Test
    @DisplayName("Test Example 2: Alphanumeric strings")
    void testExample2() {
        DataRequest request = new DataRequest(Arrays.asList("A1B2", "100", "#", "Test123", "Z", "55"));
        DataResponse response = dataProcessingService.processData(request, "REQ-1002");
        
        assertTrue(response.isSuccess());
        assertEquals("REQ-1002", response.getRequestId());
        assertEquals(Arrays.asList("55", "1", "123"), response.getOddNumbers());
        assertEquals(Arrays.asList("100", "2"), response.getEvenNumbers());
        assertTrue(response.getAlphabets().contains("Z"));
        assertEquals(Arrays.asList("#"), response.getSpecialCharacters());
        assertEquals("281", response.getSum());
        assertEquals("123", response.getLargestNumber());
        assertEquals("1", response.getSmallestNumber());
        assertEquals(7, response.getAlphabetCount());
        assertEquals(5, response.getNumberCount());
        assertEquals(1, response.getSpecialCharacterCount());
    }
    
    @Test
    @DisplayName("Test Example 3: Handling null, empty, and whitespace")
    void testExample3() {
        DataRequest request = new DataRequest(Arrays.asList("10", "-10", "A", "A", "", null, "&", "-5"));
        DataResponse response = dataProcessingService.processData(request, "REQ-1003");
        
        assertTrue(response.isSuccess());
        assertEquals("REQ-1003", response.getRequestId());
        assertEquals(Arrays.asList("-5"), response.getOddNumbers());
        assertEquals(Arrays.asList("10", "-10"), response.getEvenNumbers());
        assertEquals(Arrays.asList("A", "A"), response.getAlphabets());
        assertEquals(Arrays.asList("&"), response.getSpecialCharacters());
        assertEquals("-5", response.getSum());
        assertEquals("10", response.getLargestNumber());
        assertEquals("-10", response.getSmallestNumber());
        assertTrue(response.isContainsDuplicates());
        assertEquals(4, response.getUniqueElementCount());
        assertEquals(8, response.getSummary().getTotalElementsReceived());
        assertEquals(6, response.getSummary().getValidElementsProcessed());
        assertEquals(2, response.getSummary().getInvalidElementsIgnored());
    }
    
    @Test
    @DisplayName("Test Example 4: Decimal and negative numbers")
    void testExample4() {
        DataRequest request = new DataRequest(Arrays.asList("-10", "25.5", "-100.75", "B", "@", "5", "A9"));
        DataResponse response = dataProcessingService.processData(request, "REQ-1004");
        
        assertTrue(response.isSuccess());
        assertEquals("REQ-1004", response.getRequestId());
        assertEquals(Arrays.asList("5", "9"), response.getOddNumbers());
        assertEquals(Arrays.asList("-10"), response.getEvenNumbers());
        assertEquals(2, response.getAlphabetCount());
        assertEquals(4, response.getNumberCount());
        assertEquals(1, response.getSpecialCharacterCount());
        assertEquals("-80.25", response.getSum());
        assertEquals("25.5", response.getLargestNumber());
        assertEquals("-100.75", response.getSmallestNumber());
        assertFalse(response.isContainsDuplicates());
    }
    
    @Test
    @DisplayName("Test Example 5: Complex alphanumeric with duplicates")
    void testExample5() {
        DataRequest request = new DataRequest(
                Arrays.asList("ABC", "123", "A1B2", "$", "%", "-50", "@", "xyz", "", null, "999", "Test99", "&")
        );
        DataResponse response = dataProcessingService.processData(request, "REQ-1005");
        
        assertTrue(response.isSuccess());
        assertEquals("REQ-1005", response.getRequestId());
        assertTrue(response.getOddNumbers().contains("123"));
        assertTrue(response.getOddNumbers().contains("999"));
        assertTrue(response.getEvenNumbers().contains("-50"));
        assertTrue(response.getAlphabets().contains("ABC"));
        assertTrue(response.getAlphabets().contains("xyz"));
        assertEquals(3, response.getSpecialCharacterCount());
        assertEquals("1072", response.getSum());
        assertEquals("999", response.getLargestNumber());
        assertEquals("-50", response.getSmallestNumber());
        assertTrue(response.getVowelCount() > 0);
        assertNotNull(response.getAlphabetFrequency());
        assertNotNull(response.getLongestAlphabeticValue());
        assertNotNull(response.getShortestAlphabeticValue());
    }
    
    @Test
    @DisplayName("Test alphabet frequency calculation")
    void testAlphabetFrequency() {
        DataRequest request = new DataRequest(Arrays.asList("AAA", "BB", "A"));
        DataResponse response = dataProcessingService.processData(request, "REQ-TEST");
        
        assertEquals(4, response.getAlphabetFrequency().get("A"));
        assertEquals(2, response.getAlphabetFrequency().get("B"));
    }
    
    @Test
    @DisplayName("Test sorted numbers in ascending order")
    void testSortedNumbers() {
        DataRequest request = new DataRequest(Arrays.asList("100", "5", "-50", "25.5"));
        DataResponse response = dataProcessingService.processData(request, "REQ-TEST");
        
        assertEquals(Arrays.asList("-50", "5", "25.5", "100"), response.getSortedNumbers());
    }
    
    @Test
    @DisplayName("Test vowel and consonant count")
    void testVowelConsonantCount() {
        DataRequest request = new DataRequest(Arrays.asList("HELLO", "WORLD"));
        DataResponse response = dataProcessingService.processData(request, "REQ-TEST");
        
        assertEquals(3, response.getVowelCount()); // E, O, O
        assertEquals(7, response.getConsonantCount()); // H, L, L, W, R, L, D
    }
    
    @Test
    @DisplayName("Test longest and shortest alphabetic strings")
    void testLongestShortestAlphabetic() {
        DataRequest request = new DataRequest(Arrays.asList("A", "HELLO", "HI", "WORLD"));
        DataResponse response = dataProcessingService.processData(request, "REQ-TEST");
        
        assertTrue(response.getLongestAlphabeticValue().equals("HELLO") || 
                   response.getLongestAlphabeticValue().equals("WORLD"));
        assertEquals("A", response.getShortestAlphabeticValue());
    }
    
    @Test
    @DisplayName("Test empty data array")
    void testEmptyDataArray() {
        DataRequest request = new DataRequest(Arrays.asList());
        DataResponse response = dataProcessingService.processData(request, "REQ-TEST");
        
        assertTrue(response.isSuccess());
        assertEquals(0, response.getNumberCount());
        assertEquals(0, response.getAlphabetCount());
        assertEquals(0, response.getSpecialCharacterCount());
    }
    
    @Test
    @DisplayName("Test all null values")
    void testAllNullValues() {
        DataRequest request = new DataRequest(Arrays.asList(null, null, null));
        DataResponse response = dataProcessingService.processData(request, "REQ-TEST");
        
        assertTrue(response.isSuccess());
        assertEquals(3, response.getSummary().getTotalElementsReceived());
        assertEquals(0, response.getSummary().getValidElementsProcessed());
        assertEquals(3, response.getSummary().getInvalidElementsIgnored());
    }
    
    @Test
    @DisplayName("Test duplicate detection")
    void testDuplicateDetection() {
        DataRequest request = new DataRequest(Arrays.asList("A", "B", "A", "C"));
        DataResponse response = dataProcessingService.processData(request, "REQ-TEST");
        
        assertTrue(response.isContainsDuplicates());
        assertEquals(3, response.getUniqueElementCount());
    }
    
    @Test
    @DisplayName("Test no duplicates")
    void testNoDuplicates() {
        DataRequest request = new DataRequest(Arrays.asList("A", "B", "C", "1", "2"));
        DataResponse response = dataProcessingService.processData(request, "REQ-TEST");
        
        assertFalse(response.isContainsDuplicates());
        assertEquals(5, response.getUniqueElementCount());
    }
    
    @Test
    @DisplayName("Test special characters only")
    void testSpecialCharactersOnly() {
        DataRequest request = new DataRequest(Arrays.asList("@", "#", "$", "%", "&"));
        DataResponse response = dataProcessingService.processData(request, "REQ-TEST");
        
        assertEquals(5, response.getSpecialCharacterCount());
        assertEquals(0, response.getNumberCount());
        assertEquals(0, response.getAlphabetCount());
    }
    
    @Test
    @DisplayName("Test numbers only")
    void testNumbersOnly() {
        DataRequest request = new DataRequest(Arrays.asList("1", "2", "3", "4", "5"));
        DataResponse response = dataProcessingService.processData(request, "REQ-TEST");
        
        assertEquals(5, response.getNumberCount());
        assertEquals(0, response.getAlphabetCount());
        assertEquals(0, response.getSpecialCharacterCount());
        assertEquals("15", response.getSum());
    }
    
    @Test
    @DisplayName("Test alphabets only")
    void testAlphabetsOnly() {
        DataRequest request = new DataRequest(Arrays.asList("A", "B", "C", "D", "E"));
        DataResponse response = dataProcessingService.processData(request, "REQ-TEST");
        
        assertEquals(0, response.getNumberCount());
        assertEquals(5, response.getAlphabetCount());
        assertEquals(0, response.getSpecialCharacterCount());
    }
    
    @Test
    @DisplayName("Test large numbers")
    void testLargeNumbers() {
        DataRequest request = new DataRequest(Arrays.asList("999999999", "1000000000", "-999999999"));
        DataResponse response = dataProcessingService.processData(request, "REQ-TEST");
        
        assertEquals("1000000001", response.getSum());
        assertEquals("1000000000", response.getLargestNumber());
        assertEquals("-999999999", response.getSmallestNumber());
    }
    
    @Test
    @DisplayName("Test processing time is calculated")
    void testProcessingTime() {
        DataRequest request = new DataRequest(Arrays.asList("A", "1", "B", "2"));
        DataResponse response = dataProcessingService.processData(request, "REQ-TEST");
        
        assertTrue(response.getProcessingTimeMs() >= 0);
    }
}
