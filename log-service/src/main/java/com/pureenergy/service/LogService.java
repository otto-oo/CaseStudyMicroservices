package com.pureenergy.service;

import com.pureenergy.dto.LogDTO;
import com.pureenergy.enums.Operation;

import java.time.LocalDate;
import java.util.List;

public interface LogService {

    List<LogDTO> getAllLogsByDate(LocalDate date);

    List<LogDTO> getAllLogs();

    LogDTO createLog(LogDTO logDTO);
}
