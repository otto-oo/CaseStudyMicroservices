package com.pureenergy.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"},ignoreUnknown = true)
public class MovieDTO {

    @JsonIgnore
    private Long id;

    private String movieName;
    private String movieDetails;
    private int movieYear;
    private String director;
    private String movieUrl;

}
