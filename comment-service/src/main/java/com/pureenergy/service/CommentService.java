package com.pureenergy.service;

import com.pureenergy.dto.CommentDTO;

import java.util.List;

public interface CommentService {

    List<CommentDTO> getCommentsByMovieId(Long movieId);

    CommentDTO createComment(CommentDTO commentDTO);
}
