package com.pureenergy.service;

import com.pureenergy.dto.MovieDTO;

import java.util.List;

public interface MovieService {

    List<MovieDTO> getAllMovies();

    MovieDTO getMovieById(Long id) throws Exception;

    MovieDTO createMovie(MovieDTO movieDTO);
}
