package com.pureenergy.service.implementation;

import com.pureenergy.dto.LogDTO;
import com.pureenergy.enums.Operation;
import com.pureenergy.repository.CommentRepository;
import com.pureenergy.service.CommentService;
import com.pureenergy.service.LogClientService;
import com.pureenergy.service.MovieClientService;
import com.pureenergy.dto.CommentDTO;
import com.pureenergy.entity.Comment;
import com.pureenergy.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentServiceImplementation implements CommentService {

    private CommentRepository commentRepository;
    private MapperUtil mapperUtil;
    private LogClientService logClientService;
    private MovieClientService movieClientService;

    public CommentServiceImplementation(CommentRepository commentRepository, MapperUtil mapperUtil, LogClientService logClientService, MovieClientService movieClientService) {
        this.commentRepository = commentRepository;
        this.mapperUtil = mapperUtil;
        this.logClientService = logClientService;
        this.movieClientService = movieClientService;
    }

    @Override
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
