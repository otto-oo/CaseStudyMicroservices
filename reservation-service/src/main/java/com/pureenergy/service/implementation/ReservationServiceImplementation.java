package com.pureenergy.service.implementation;

import com.pureenergy.dto.CommentDTO;
import com.pureenergy.dto.LogDTO;
import com.pureenergy.dto.ReservationDTO;
import com.pureenergy.entity.Reservation;
import com.pureenergy.enums.Operation;
import com.pureenergy.enums.Status;
import com.pureenergy.exception.NoSuchMovieException;
import com.pureenergy.exception.NoSuchReservationException;
import com.pureenergy.repository.ReservationRepository;
import com.pureenergy.service.LogClientService;
import com.pureenergy.service.MovieClientService;
import com.pureenergy.service.ReservationService;
import com.pureenergy.util.MapperUtil;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReservationServiceImplementation implements ReservationService {

    private ReservationRepository reservationRepository;
    private MapperUtil mapperUtil;
    private LogClientService logClientService;
    private MovieClientService movieClientService;

    private static Logger logger = LoggerFactory.getLogger(ReservationService .class);

    public ReservationServiceImplementation(ReservationRepository reservationRepository, MapperUtil mapperUtil, LogClientService logClientService, MovieClientService movieClientService) {
        this.reservationRepository = reservationRepository;
        this.mapperUtil = mapperUtil;
        this.logClientService = logClientService;
        this.movieClientService = movieClientService;
    }

    @Override
    @CircuitBreaker(name="movie-service",fallbackMethod = "movieServiceFallBack")
    @Retry(name = "movie-service",fallbackMethod = "movieServiceRetryFallBack")
    public List<ReservationDTO> getReservationsByMovieId(Long movieId) {
        if (movieClientService.getMovieById(movieId).getData()==null){
            throw new NoSuchMovieException(movieId);
        }
        List<Reservation> reservationList = reservationRepository.findByMovieId(movieId);
        log.info("Reservations that depends on movie id " + movieId + " are retrieved.");
        logClientService.createLog(new LogDTO(LocalDate.now(), Operation.READ, "Reservations that depends on movie id " + movieId + " are retrieved."));
        return reservationList
                .stream()
                .map(reservation -> mapperUtil.convert(reservation, new ReservationDTO()))
                .collect(Collectors.toList());
    }

    @Override
    @CircuitBreaker(name="movie-service",fallbackMethod = "movieServiceFallBack")
    @Retry(name = "movie-service",fallbackMethod = "movieServiceRetryFallBack")
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        if (movieClientService.getMovieById(reservationDTO.getMovieId()).getData()==null){
            throw new NoSuchMovieException(reservationDTO.getMovieId());
        }
        log.info("Reservation is created.");
        logClientService.createLog(new LogDTO(LocalDate.now(), Operation.CREATE, "Reservation is created."));
        Reservation reservation = mapperUtil.convert(reservationDTO, new Reservation());
        reservationRepository.save(reservation);
        return reservationDTO;
    }

    @Override
    public ReservationDTO deleteReservation(Long id) throws Exception {
        if (!reservationRepository.findById(id).isPresent()){
            throw new NoSuchReservationException(id);
        }
        log.info("Reservation is canceled depends on reservation id " + id + ".");
        logClientService.createLog(new LogDTO(LocalDate.now(), Operation.DELETE, "Reservation is canceled depends on reservation id " + id + "."));
        Reservation reservation = reservationRepository.findById(id).get();
        reservation.setReservationStatus(Status.PASSIVE);
        reservationRepository.save(reservation);
        return mapperUtil.convert(reservation, new ReservationDTO());
    }

    public List<CommentDTO> movieServiceFallBack(Long movieId, Exception e){
        logger.error("exception{}",e.getMessage());
        return new ArrayList<>();
    }

    public List<CommentDTO> movieServiceRetryFallBack(Long movieId,Exception e) {
        logger.error("Retried 3 times. Movie-service is not healthy {}", e.getMessage());
        return new ArrayList<>();
    }
}