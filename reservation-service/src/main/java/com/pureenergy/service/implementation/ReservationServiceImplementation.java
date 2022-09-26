package com.pureenergy.service.implementation;

import com.pureenergy.dto.ReservationDTO;
import com.pureenergy.entity.Reservation;
import com.pureenergy.enums.Operation;
import com.pureenergy.enums.Status;
import com.pureenergy.exception.NoSuchMovieException;
import com.pureenergy.exception.NoSuchReservationException;
import com.pureenergy.repository.ReservationRepository;
import com.pureenergy.service.MovieClientService;
import com.pureenergy.service.ReservationService;
//import com.pureenergy.util.LogUtil;
import com.pureenergy.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReservationServiceImplementation implements ReservationService {

    private ReservationRepository reservationRepository;
    private MapperUtil mapperUtil;
    //private LogUtil logUtil;
    private MovieClientService movieClientService;

    public ReservationServiceImplementation(ReservationRepository reservationRepository, MapperUtil mapperUtil, MovieClientService movieClientService) {
        this.reservationRepository = reservationRepository;
        this.mapperUtil = mapperUtil;
        this.movieClientService = movieClientService;
    }

    @Override
    public List<ReservationDTO> getReservationsByMovieId(Long movieId) {
        if (movieClientService.getMovieById(movieId).getData()==null){
            throw new NoSuchMovieException(movieId);
        }
        List<Reservation> reservationList = reservationRepository.findByMovieId(movieId);
        log.info("Reservations that depends on movie id " + movieId + " are retrieved.");
        //logUtil.createLog(Operation.READ, "Reservations that depends on movie id " + movieId + " are retrieved.");
        return reservationList
                .stream()
                .map(reservation -> mapperUtil.convert(reservation, new ReservationDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        if (movieClientService.getMovieById(reservationDTO.getMovieId()).getData()==null){
            throw new NoSuchMovieException(reservationDTO.getMovieId());
        }
        log.info("Reservation is created.");
        //logUtil.createLog(Operation.CREATE, "Reservation is created.");
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
        //logUtil.createLog(Operation.DELETE, "Reservation is canceled depends on reservation id " + id + ".");
        Reservation reservation = reservationRepository.findById(id).get();
        reservation.setReservationStatus(Status.PASSIVE);
        reservationRepository.save(reservation);
        return mapperUtil.convert(reservation, new ReservationDTO());
    }
}