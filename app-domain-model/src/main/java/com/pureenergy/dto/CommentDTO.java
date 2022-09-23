package com.pureenergy.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"},ignoreUnknown = true)
public class CommentDTO {

    @JsonIgnore
    private Long id;

    private Long movieId;
    private String commentName;
    private String commentDetails;
    private LocalDateTime commentTime;
}
