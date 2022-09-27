package com.pureenergy.service.implementation;

import com.pureenergy.dto.LogDTO;
import com.pureenergy.dto.MovieDTO;
import com.pureenergy.enums.Operation;
import com.pureenergy.repository.CommentRepository;
import com.pureenergy.service.CommentService;
import com.pureenergy.service.LogClientService;
import com.pureenergy.service.MovieClientService;
import com.pureenergy.dto.CommentDTO;
import com.pureenergy.entity.Comment;
import com.pureenergy.util.MapperUtil;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
public class CommentServiceImplementation implements CommentService {

    private CommentRepository commentRepository;
    private MapperUtil mapperUtil;
    private LogClientService logClientService;
    private MovieClientService movieClientService;

    private static Logger logger = LoggerFactory.getLogger(CommentService .class);

    public CommentServiceImplementation(CommentRepository commentRepository, MapperUtil mapperUtil, LogClientService logClientService, MovieClientService movieClientService) {
        this.commentRepository = commentRepository;
        this.mapperUtil = mapperUtil;
        this.logClientService = logClientService;
        this.movieClientService = movieClientService;
    }

    @Override
    @CircuitBreaker(name="movie-service",fallbackMethod = "movieServiceFallBack")
    public List<CommentDTO> getCommentsByMovieId(Long movieId) {
        if (movieClientService.getMovieById(movieId).getData()==null){
            return null;
        }
        List<Comment> commentList = commentRepository.findByMovieId(movieId);
        log.info("Comments are getting by movie id " + movieId);
        logClientService.createLog(new LogDTO(LocalDate.now(), Operation.READ, "Comments are getting by movie id " + movieId));
        return commentList
                .stream()
                .map(comment -> mapperUtil.convert(comment, new CommentDTO()))
                .collect(Collectors.toList());
    }

    public List<CommentDTO> movieServiceFallBack(Long movieId, Exception e){
        logger.error("exception{}",e.getMessage());
        return new ArrayList<>();
    }

    @Override
    public CommentDTO createComment(CommentDTO commentDTO) {
        if (movieClientService.getMovieById(commentDTO.getMovieId()).getData()==null){
            return null;
        }
        log.info("Comment is created.");
        logClientService.createLog(new LogDTO(LocalDate.now(), Operation.CREATE, "Comment is created."));
        Comment comment = mapperUtil.convert(commentDTO, new Comment());
        commentRepository.save(comment);
        return commentDTO;
    }


}
