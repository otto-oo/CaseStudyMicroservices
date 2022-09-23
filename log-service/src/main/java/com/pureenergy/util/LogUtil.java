package com.pureenergy.util;

import com.pureenergy.repository.LogRepository;
import com.pureenergy.entity.Log;
import com.pureenergy.enums.Operation;
import org.springframework.stereotype.Component;

@Component
public class LogUtil {

    private LogRepository logRepository;

    public LogUtil(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void createLog(Operation operation, String message){
        Log log = new Log(operation, message);
        logRepository.save(log);
    }
}
