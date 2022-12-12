package com.project.santaletter.report;

import com.project.santaletter.domain.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public void makeReport(Long letterId) {
        Report report = new Report(letterId);
        reportRepository.save(report);
    }
}
