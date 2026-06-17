package com.bajajfinserver.test.JavaAPI.service.impl;

import com.bajajfinserver.test.JavaAPI.dto.DataRequest;
import com.bajajfinserver.test.JavaAPI.dto.DataResponse;
import com.bajajfinserver.test.JavaAPI.service.DataProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DataProcessingServiceImpl implements DataProcessingService {
    
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^-?\\d+(\\.\\d+)?$");
    private static final Pattern ALPHABET_PATTERN = Pattern.compile("^[a-zA-Z]+$");
    private static final Set<Character> VOWELS = Set.of('A', 'E', 'I', 'O', 'U', 'a', 'e', 'i', 'o', 'u');
    
    @Override
    public DataResponse processData(DataRequest request, String requestId) {
        long startTime = System.currentTimeMillis();
        
        log.info("Processing request with ID: {}", requestId);
        
        List<String> data = request.getData();
        
        // Filter out null, empty, and whitespace-only values
        List<String> validData = data.stream()
                .filter(Objects::nonNull)
                .filter(s -> !s.trim().isEmpty())
                .collect(Collectors.toList());
        
        int totalReceived = data.size();
        int validProcessed = validData.size();
        int invalidIgnored = totalReceived - validProcessed;
        
        // Categorize data
        List<String> numbers = new ArrayList<>();
        List<BigDecimal> numericValues = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        
        for (String item : validData) {
            if (NUMBER_PATTERN.matcher(item).matches()) {
                numbers.add(item);
                numericValues.add(new BigDecimal(item));
            } else if (ALPHABET_PATTERN.matcher(item).matches()) {
                alphabets.add(item);
            } else {
                // Handle alphanumeric strings
                String extractedNumbers = extractNumbers(item);
                String extractedAlphabets = extractAlphabets(item);
                String extractedSpecial = extractSpecialCharacters(item);
                
                if (!extractedNumbers.isEmpty()) {
                    for (String num : extractedNumbers.split(",")) {
                        numbers.add(num);
                        numericValues.add(new BigDecimal(num));
                    }
                }
                
                if (!extractedAlphabets.isEmpty()) {
                    alphabets.add(extractedAlphabets);
                }
                
                if (!extractedSpecial.isEmpty()) {
                    for (char c : extractedSpecial.toCharArray()) {
                        specialCharacters.add(String.valueOf(c));
                    }
                }
            }
        }
        
        // Separate odd and even numbers
        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        
        for (BigDecimal num : numericValues) {
            if (num.stripTrailingZeros().scale() <= 0) {
                // It's an integer or can be represented as one
                long longValue = num.longValue();
                if (longValue % 2 == 0) {
                    evenNumbers.add(num.stripTrailingZeros().toPlainString());
                } else {
                    oddNumbers.add(num.stripTrailingZeros().toPlainString());
                }
            }
        }
        
        // Calculate sum
        BigDecimal sum = numericValues.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Find largest and smallest numbers
        String largestNumber = numericValues.isEmpty() ? null : 
                numericValues.stream().max(BigDecimal::compareTo).get().stripTrailingZeros().toPlainString();
        String smallestNumber = numericValues.isEmpty() ? null :
                numericValues.stream().min(BigDecimal::compareTo).get().stripTrailingZeros().toPlainString();
        
        // Count alphabets, numbers, and special characters
        int alphabetCount = alphabets.stream().mapToInt(String::length).sum();
        int numberCount = numbers.size();
        int specialCharacterCount = specialCharacters.size();
        
        // Check for duplicates
        Set<String> uniqueSet = new HashSet<>(validData);
        boolean containsDuplicates = uniqueSet.size() < validData.size();
        
        // Alphabet frequency
        Map<String, Integer> alphabetFrequency = calculateAlphabetFrequency(alphabets);
        
        // Unique element count
        int uniqueElementCount = uniqueSet.size();
        
        // Sorted numbers
        List<String> sortedNumbers = numericValues.stream()
                .sorted()
                .map(n -> n.stripTrailingZeros().toPlainString())
                .collect(Collectors.toList());
        
        // Vowel and consonant count
        int vowelCount = countVowels(alphabets);
        int consonantCount = alphabetCount - vowelCount;
        
        // Longest and shortest alphabetic strings
        String longestAlphabetic = alphabets.isEmpty() ? null :
                alphabets.stream().max(Comparator.comparingInt(String::length)).orElse(null);
        String shortestAlphabetic = alphabets.isEmpty() ? null :
                alphabets.stream().min(Comparator.comparingInt(String::length)).orElse(null);
        
        long processingTime = System.currentTimeMillis() - startTime;
        
        log.info("Request {} processed in {} ms", requestId, processingTime);
        
        return DataResponse.builder()
                .isSuccess(true)
                .requestId(requestId)
                .oddNumbers(oddNumbers)
                .evenNumbers(evenNumbers)
                .alphabets(alphabets)
                .specialCharacters(specialCharacters)
                .sum(sum.stripTrailingZeros().toPlainString())
                .largestNumber(largestNumber)
                .smallestNumber(smallestNumber)
                .alphabetCount(alphabetCount)
                .numberCount(numberCount)
                .specialCharacterCount(specialCharacterCount)
                .containsDuplicates(containsDuplicates)
                .processingTimeMs(processingTime)
                .alphabetFrequency(alphabetFrequency)
                .uniqueElementCount(uniqueElementCount)
                .sortedNumbers(sortedNumbers)
                .vowelCount(vowelCount)
                .consonantCount(consonantCount)
                .longestAlphabeticValue(longestAlphabetic)
                .shortestAlphabeticValue(shortestAlphabetic)
                .summary(DataResponse.Summary.builder()
                        .totalElementsReceived(totalReceived)
                        .validElementsProcessed(validProcessed)
                        .invalidElementsIgnored(invalidIgnored)
                        .build())
                .build();
    }
    
    private String extractNumbers(String input) {
        StringBuilder numbers = new StringBuilder();
        StringBuilder currentNumber = new StringBuilder();
        boolean isNegative = false;
        
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                currentNumber.append(c);
            } else if (c == '-' && (i == 0 || !Character.isDigit(input.charAt(i - 1)))) {
                isNegative = true;
            } else {
                if (currentNumber.length() > 0) {
                    if (numbers.length() > 0) numbers.append(",");
                    if (isNegative) numbers.append("-");
                    numbers.append(currentNumber);
                    currentNumber = new StringBuilder();
                    isNegative = false;
                }
            }
        }
        
        if (currentNumber.length() > 0) {
            if (numbers.length() > 0) numbers.append(",");
            if (isNegative) numbers.append("-");
            numbers.append(currentNumber);
        }
        
        return numbers.toString();
    }
    
    private String extractAlphabets(String input) {
        return input.chars()
                .filter(Character::isLetter)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
    
    private String extractSpecialCharacters(String input) {
        return input.chars()
                .filter(c -> !Character.isLetterOrDigit(c) && c != '.' && c != '-')
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
    
    private Map<String, Integer> calculateAlphabetFrequency(List<String> alphabets) {
        Map<String, Integer> frequency = new HashMap<>();
        for (String str : alphabets) {
            for (char c : str.toCharArray()) {
                String upper = String.valueOf(Character.toUpperCase(c));
                frequency.put(upper, frequency.getOrDefault(upper, 0) + 1);
            }
        }
        return frequency;
    }
    
    private int countVowels(List<String> alphabets) {
        int count = 0;
        for (String str : alphabets) {
            for (char c : str.toCharArray()) {
                if (VOWELS.contains(c)) {
                    count++;
                }
            }
        }
        return count;
    }
}
