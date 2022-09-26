package com.pureenergy.service;

import com.pureenergy.dto.LogDTO;
import com.pureenergy.dto.LogResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("log-service")
public interface LogClientService {

    @PostMapping
    LogResponseDTO createLog(@RequestBody LogDTO logDTO);
}
