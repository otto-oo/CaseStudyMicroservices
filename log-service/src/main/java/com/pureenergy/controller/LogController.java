package com.pureenergy.controller;

import com.pureenergy.dto.LogDTO;
import com.pureenergy.entity.ResponseWrapper;
import com.pureenergy.enums.Operation;
import com.pureenergy.service.LogService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/logs")
public class LogController {

    private LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/{date}")
    public ResponseEntity<ResponseWrapper> getAllLogsByDate(@PathVariable("date")
                                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return ResponseEntity
                .ok(new ResponseWrapper("Logs are getting by date " + date, logService.getAllLogsByDate(date)));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getAllLogs(){
        return ResponseEntity
                .ok(new ResponseWrapper("All logs are retrieved", logService.getAllLogs()));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createLog(@RequestBody LogDTO logDTO){
        return ResponseEntity
                .ok(new ResponseWrapper("Log is saved", logService.createLog(logDTO)));
    }
}