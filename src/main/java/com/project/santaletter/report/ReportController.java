package com.project.santaletter.report;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/report/{id}")
    public ResponseEntity postReport(@PathVariable Long id) {
        reportService.makeReport(id);

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
