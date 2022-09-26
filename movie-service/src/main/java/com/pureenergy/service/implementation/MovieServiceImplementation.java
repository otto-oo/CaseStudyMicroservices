package com.pureenergy.service.implementation;

import com.pureenergy.dto.LogDTO;
import com.pureenergy.exception.NoSuchMovieException;
import com.pureenergy.repository.MovieRepository;
import com.pureenergy.service.LogClientService;
import com.pureenergy.service.MovieService;
import com.pureenergy.dto.MovieDTO;
import com.pureenergy.entity.Movie;
import com.pureenergy.enums.Operation;
import com.pureenergy.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieServiceImplementation implements MovieService {

    private MovieRepository movieRepository;
    private MapperUtil mapperUtil;
    private LogClientService logClientService;


    public MovieServiceImplementation(MovieRepository movieRepository, MapperUtil mapperUtil, LogClientService logClientService) {
        this.movieRepository = movieRepository;
        this.mapperUtil = mapperUtil;
        this.logClientService = logClientService;
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        List<Movie> movieList = movieRepository.findAll();
        log.info("All movies are retrieved.");
        logClientService.createLog(new LogDTO(LocalDate.now(), Operation.READ, "All movies are retrieved."));
        return movieList
                .stream()
                .map(movie -> mapperUtil.convert(movie, new MovieDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public MovieDTO getMovieById(Long id) throws Exception {
        if (!movieRepository.findById(id).isPresent()){
            throw new NoSuchMovieException(id);
        }
        Movie movie = movieRepository.findById(id).get();
        log.info("Movie id " + id + " is retrieved.");
        logClientService.createLog(new LogDTO(LocalDate.now(), Operation.READ, "Movie id " + id + " is retrieved."));
        return mapperUtil.convert(movie, new MovieDTO());
    }

    @Override
    public MovieDTO createMovie(MovieDTO movieDTO) {
        Movie movie = mapperUtil.convert(movieDTO, new Movie());
        log.info("Movie is created");
        logClientService.createLog(new LogDTO(LocalDate.now(), Operation.CREATE, "Movie is created"));
        movieRepository.save(movie);
        return movieDTO;
    }
}