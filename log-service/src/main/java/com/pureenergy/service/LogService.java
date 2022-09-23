package com.pureenergy.service;

import com.pureenergy.dto.LogDTO;

import java.time.LocalDate;
import java.util.List;

public interface LogService {

    List<LogDTO> getAllLogsByDate(LocalDate date);

    List<LogDTO> getAllLogs();
}
