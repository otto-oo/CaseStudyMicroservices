package com.pureenergy.dto;

import lombok.Data;

@Data
public class MovieResponseDTO {
    public boolean success;
    public String message;
    public int code;
    public MovieDTO data;
}
