package com.pureenergy.service;

import com.pureenergy.dto.MovieResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "movie-service",url = "http://localhost:9091")
public interface MovieClientService {

    @GetMapping("/api/v1/movies/{id}")
    MovieResponseDTO getMovieById(@PathVariable("id") Long id);

}
