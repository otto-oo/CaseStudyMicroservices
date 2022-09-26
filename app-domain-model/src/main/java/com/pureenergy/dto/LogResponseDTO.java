package com.pureenergy.dto;

import lombok.Data;

@Data
public class LogResponseDTO {

    public boolean success;
    public String message;
    public int code;
    public LogDTO data;
}
