package com.pureenergy.controller;

import com.pureenergy.dto.CommentDTO;
import com.pureenergy.entity.ResponseWrapper;
import com.pureenergy.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<ResponseWrapper> getCommentsByMovieId(@PathVariable("movieId") Long movieId){
        return ResponseEntity
                .ok(new ResponseWrapper("Comments are getting by movie id " + movieId, commentService.getCommentsByMovieId(movieId)));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createComment(@RequestBody CommentDTO commentDTO){
        return ResponseEntity
                .ok(new ResponseWrapper("Comment is created.", commentService.createComment(commentDTO)));
    }
}
