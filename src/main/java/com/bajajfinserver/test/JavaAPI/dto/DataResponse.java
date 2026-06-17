package com.bajajfinserver.test.JavaAPI.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataResponse {
    @JsonProperty("is_success")
    private boolean isSuccess;
    
    @JsonProperty("request_id")
    private String requestId;
    
    @JsonProperty("odd_numbers")
    private List<String> oddNumbers;
    
    @JsonProperty("even_numbers")
    private List<String> evenNumbers;
    
    @JsonProperty("alphabets")
    private List<String> alphabets;
    
    @JsonProperty("special_characters")
    private List<String> specialCharacters;
    
    @JsonProperty("sum")
    private String sum;
    
    @JsonProperty("largest_number")
    private String largestNumber;
    
    @JsonProperty("smallest_number")
    private String smallestNumber;
    
    @JsonProperty("alphabet_count")
    private int alphabetCount;
    
    @JsonProperty("number_count")
    private int numberCount;
    
    @JsonProperty("special_character_count")
    private int specialCharacterCount;
    
    @JsonProperty("contains_duplicates")
    private boolean containsDuplicates;
    
    @JsonProperty("processing_time_ms")
    private long processingTimeMs;
    
    @JsonProperty("alphabet_frequency")
    private Map<String, Integer> alphabetFrequency;
    
    @JsonProperty("unique_element_count")
    private int uniqueElementCount;
    
    @JsonProperty("sorted_numbers")
    private List<String> sortedNumbers;
    
    @JsonProperty("vowel_count")
    private int vowelCount;
    
    @JsonProperty("consonant_count")
    private int consonantCount;
    
    @JsonProperty("longest_alphabetic_value")
    private String longestAlphabeticValue;
    
    @JsonProperty("shortest_alphabetic_value")
    private String shortestAlphabeticValue;
    
    @JsonProperty("summary")
    private Summary summary;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Summary {
        @JsonProperty("total_elements_received")
        private int totalElementsReceived;
        
        @JsonProperty("valid_elements_processed")
        private int validElementsProcessed;
        
        @JsonProperty("invalid_elements_ignored")
        private int invalidElementsIgnored;
    }
}
