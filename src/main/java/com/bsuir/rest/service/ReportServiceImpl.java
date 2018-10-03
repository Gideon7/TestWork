package com.bsuir.rest.service;

import com.bsuir.rest.entity.JogInfoEntity;
import com.bsuir.rest.exception.NotFoundException;
import com.bsuir.rest.model.ReportForm;
import com.bsuir.rest.repository.JogInfoRepository;
import com.bsuir.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final int METERS_IN_KILOMETER = 1000;
    private final int SECONDS_IN_MINUTE = 60;
    private final int SECONDS_IN_HOUR = 3600;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JogInfoRepository jogInfoRepository;

    @Override
    @PreAuthorize("@securityUtil.isAdminOrResourceOwner(#userId)")
    public List<ReportForm> getReport(Long userId) {

        if(userRepository.findOneById(userId) == null) {
            throw new NotFoundException();
        }

        return createReportList(jogInfoRepository.findAllByUserEntityIdOrderByDateAsc(userId));
    }

    private List<ReportForm> createReportList(List<JogInfoEntity> jogInfoEntityList) {

        if(jogInfoEntityList.isEmpty()) {
            return new ArrayList<>();
        }

        int weekCounter = 1;
        JogInfoEntity firstJogOnWeek = jogInfoEntityList.get(0);
        List<JogInfoEntity> jogsOnWeekList = new ArrayList<>();
        List<ReportForm> reportFormList = new ArrayList<>();

        for(JogInfoEntity currentJogInfoEntity: jogInfoEntityList) {
            if(Period.between(firstJogOnWeek.getDate(), currentJogInfoEntity.getDate()).getDays() < 7
                    && jogInfoEntityList.indexOf(currentJogInfoEntity) < jogInfoEntityList.size() - 1) {
                jogsOnWeekList.add(currentJogInfoEntity);
            } else {
                reportFormList.add(createReport(weekCounter, jogsOnWeekList));

                jogsOnWeekList.clear();
                jogsOnWeekList.add(currentJogInfoEntity);
                firstJogOnWeek = currentJogInfoEntity;
                weekCounter++;
            }
        }
        return reportFormList;
    }

    private ReportForm createReport(int Week, List<JogInfoEntity> jogsOnWeekList) {

        DecimalFormat df = new DecimalFormat("#.###");
        ReportForm reportForm = new ReportForm();

        reportForm.setWeek("Week " + Week + ":" +
                            " (" + jogsOnWeekList.get(0).getDate().toString() +
                            "/" + jogsOnWeekList.get(jogsOnWeekList.size() - 1).getDate().toString() + ")");

        reportForm.setAverageSpeed("Av. Speed(m/s): " + df.format(calculateAverageSpeed(jogsOnWeekList)));
        reportForm.setAverageTime("Av. Time: " +  calculateAverageTime(jogsOnWeekList).toString());
        reportForm.setTotalDistance("Total Distance(km): " + df.format(calculateTotalDistance(jogsOnWeekList)));

        return reportForm;
    }

    private double calculateAverageSpeed(List<JogInfoEntity> jogsOnWeekList) {

        double totalDistanceInMeters = calculateTotalDistance(jogsOnWeekList) * METERS_IN_KILOMETER;
        int totalTimeInSeconds = calculateTotalTimeInSeconds(calculateTotalTime(jogsOnWeekList));

        return totalDistanceInMeters / totalTimeInSeconds;
    }

    private LocalTime calculateAverageTime(List<JogInfoEntity> jogsOnWeekList) {

        int totalTimeInSecond = calculateTotalTimeInSeconds(calculateTotalTime(jogsOnWeekList));
        double averageTimeInSecond = (double)totalTimeInSecond / jogsOnWeekList.size();

        int hours = (int)averageTimeInSecond / SECONDS_IN_HOUR;
        averageTimeInSecond -= hours * SECONDS_IN_HOUR;
        int minuts = (int)averageTimeInSecond / SECONDS_IN_MINUTE;
        averageTimeInSecond -= minuts * SECONDS_IN_MINUTE;
        int seconds = (int)averageTimeInSecond;

        return LocalTime.of(hours, minuts, seconds);
    }

    private double calculateTotalDistance(List<JogInfoEntity> jogsOnWeekList) {

        double totalDistance = 0;

        for(JogInfoEntity jogInfoEntity: jogsOnWeekList) {
            totalDistance += jogInfoEntity.getDistance();
        }

        return totalDistance;
    }

    private LocalTime calculateTotalTime(List<JogInfoEntity> jogsOnWeekList) {

        LocalTime totalTime = LocalTime.parse("00:00:00");

        for(JogInfoEntity jogInfoEntity: jogsOnWeekList) {
            totalTime = totalTime.plusHours(jogInfoEntity.getTime().getHour())
                    .plusMinutes(jogInfoEntity.getTime().getMinute())
                    .plusSeconds(jogInfoEntity.getTime().getSecond());
        }

        return totalTime;
    }

    private int calculateTotalTimeInSeconds(LocalTime totalTime) {
        return totalTime.getHour() * SECONDS_IN_HOUR +
                    totalTime.getMinute() * SECONDS_IN_MINUTE +
                    totalTime.getSecond();
    }
}
