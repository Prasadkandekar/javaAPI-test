package com.bajajfinserver.test.JavaAPI.integration;

import com.bajajfinserver.test.JavaAPI.dto.DataRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BfhlIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    @DisplayName("Integration Test - Example 1 from assignment")
    void testExample1Integration() throws Exception {
        DataRequest request = new DataRequest(Arrays.asList("A", "1", "22", "$", "B", "7"));
        
        mockMvc.perform(post("/bfhl")
                .header("X-Request-Id", "REQ-1001")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Request-Id", "REQ-1001"))
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.request_id").value("REQ-1001"))
                .andExpect(jsonPath("$.sum").value("30"))
                .andExpect(jsonPath("$.largest_number").value("22"))
                .andExpect(jsonPath("$.smallest_number").value("1"))
                .andExpect(jsonPath("$.alphabet_count").value(2))
                .andExpect(jsonPath("$.number_count").value(3))
                .andExpect(jsonPath("$.special_character_count").value(1))
                .andExpect(jsonPath("$.contains_duplicates").value(false));
    }
    
    @Test
    @DisplayName("Integration Test - Example 2 from assignment")
    void testExample2Integration() throws Exception {
        DataRequest request = new DataRequest(Arrays.asList("A1B2", "100", "#", "Test123", "Z", "55"));
        
        mockMvc.perform(post("/bfhl")
                .header("X-Request-Id", "REQ-1002")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Request-Id", "REQ-1002"))
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.request_id").value("REQ-1002"))
                .andExpect(jsonPath("$.sum").value("281"))
                .andExpect(jsonPath("$.largest_number").value("123"))
                .andExpect(jsonPath("$.smallest_number").value("1"))
                .andExpect(jsonPath("$.contains_duplicates").value(false));
    }
    
    @Test
    @DisplayName("Integration Test - Example 3 from assignment")
    void testExample3Integration() throws Exception {
        DataRequest request = new DataRequest(Arrays.asList("10", "-10", "A", "A", "", null, "&", "-5"));
        
        mockMvc.perform(post("/bfhl")
                .header("X-Request-Id", "REQ-1003")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.request_id").value("REQ-1003"))
                .andExpect(jsonPath("$.sum").value("-5"))
                .andExpect(jsonPath("$.largest_number").value("10"))
                .andExpect(jsonPath("$.smallest_number").value("-10"))
                .andExpect(jsonPath("$.contains_duplicates").value(true))
                .andExpect(jsonPath("$.unique_element_count").value(4))
                .andExpect(jsonPath("$.summary.total_elements_received").value(8))
                .andExpect(jsonPath("$.summary.valid_elements_processed").value(6))
                .andExpect(jsonPath("$.summary.invalid_elements_ignored").value(2));
    }
    
    @Test
    @DisplayName("Integration Test - Example 4 from assignment")
    void testExample4Integration() throws Exception {
        DataRequest request = new DataRequest(Arrays.asList("-10", "25.5", "-100.75", "B", "@", "5", "A9"));
        
        mockMvc.perform(post("/bfhl")
                .header("X-Request-Id", "REQ-1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.request_id").value("REQ-1004"))
                .andExpect(jsonPath("$.sum").value("-80.25"))
                .andExpect(jsonPath("$.largest_number").value("25.5"))
                .andExpect(jsonPath("$.smallest_number").value("-100.75"))
                .andExpect(jsonPath("$.alphabet_count").value(2))
                .andExpect(jsonPath("$.number_count").value(4))
                .andExpect(jsonPath("$.special_character_count").value(1))
                .andExpect(jsonPath("$.contains_duplicates").value(false));
    }
    
    @Test
    @DisplayName("Integration Test - Example 5 from assignment")
    void testExample5Integration() throws Exception {
        DataRequest request = new DataRequest(
                Arrays.asList("ABC", "123", "A1B2", "$", "%", "-50", "@", "xyz", "", null, "999", "Test99", "&")
        );
        
        mockMvc.perform(post("/bfhl")
                .header("X-Request-Id", "REQ-1005")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.request_id").value("REQ-1005"))
                .andExpect(jsonPath("$.sum").value("1072"))
                .andExpect(jsonPath("$.largest_number").value("999"))
                .andExpect(jsonPath("$.smallest_number").value("-50"))
                .andExpect(jsonPath("$.alphabet_count").value(8))
                .andExpect(jsonPath("$.special_character_count").value(3))
                .andExpect(jsonPath("$.vowel_count").exists())
                .andExpect(jsonPath("$.consonant_count").exists())
                .andExpect(jsonPath("$.alphabet_frequency").exists())
                .andExpect(jsonPath("$.longest_alphabetic_value").exists())
                .andExpect(jsonPath("$.shortest_alphabetic_value").exists());
    }
}
