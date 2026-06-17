package com.bajajfinserver.test.JavaAPI.service;

import com.bajajfinserver.test.JavaAPI.dto.DataRequest;
import com.bajajfinserver.test.JavaAPI.dto.DataResponse;

public interface DataProcessingService {
    DataResponse processData(DataRequest request, String requestId);
}
