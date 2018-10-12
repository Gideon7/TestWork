package com.bsuir.rest.service;

import com.bsuir.rest.entity.JogInfoEntity;
import com.bsuir.rest.exception.NotFoundException;
import com.bsuir.rest.model.ReportForm;
import com.bsuir.rest.repository.JogInfoRepository;
import com.bsuir.rest.repository.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.IsoFields;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {

    private final int METERS_IN_KILOMETER = 1000;
    private final int SECONDS_IN_MINUTE = 60;
    private final int SECONDS_IN_HOUR = 3600;
    private final int DIFFERENCE_BETWEEN_WEEK_DAYS = 6;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JogInfoRepository jogInfoRepository;

    @Override
    @PreAuthorize("@securityUtil.isAdminOrResourceOwner(#userId)")
    public List<ReportForm> getReport(int page, int pageSize, Long userId) {

        if(userRepository.findOneById(userId) == null) {
            throw new NotFoundException();
        }

        Pageable pageable = new PageRequest(page - 1, pageSize);

        return createReportList(jogInfoRepository.findAllByUserEntityIdOrderByDateAsc(userId, pageable));
    }

    private List<ReportForm> createReportList(List<JogInfoEntity> jogInfoEntityList) {

        if(jogInfoEntityList.isEmpty()) {
            return new ArrayList<>();
        }

        List<ReportForm> reportFormList = new ArrayList<>();
        Map<ImmutablePair<Integer, Integer>, List<JogInfoEntity>> weekMap = new LinkedHashMap<>();

        for(JogInfoEntity currentJogInfoEntity: jogInfoEntityList) {
            int currentWeek = currentJogInfoEntity.getDate().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
            int currentYear = currentJogInfoEntity.getDate().getYear();

            if(weekMap.get(new ImmutablePair<>(currentYear, currentWeek)) == null) {
                List<JogInfoEntity> jogsOnWeek = new ArrayList<>();
                jogsOnWeek.add(currentJogInfoEntity);

                weekMap.put(new ImmutablePair<>(currentYear, currentWeek), jogsOnWeek);
            } else {
                weekMap.get(new ImmutablePair<>(currentYear, currentWeek)).add(currentJogInfoEntity);
            }
        }

        weekMap.forEach((yearAndWeek, jogsOnWeek) -> {
            int usersWeek = reportFormList.size();
            reportFormList.add(createReport(yearAndWeek, jogsOnWeek, usersWeek));
        });

        return reportFormList;
    }

    private ReportForm createReport(ImmutablePair<Integer, Integer> yearAndWeek, List<JogInfoEntity> jogsOnWeekList, int reportFormId) {

        ReportForm reportForm = new ReportForm();

        LocalDate firstJogOnWeek = jogsOnWeekList.get(0).getDate();
        LocalDate firstDayOfWeek = firstJogOnWeek.minusDays(firstJogOnWeek.getDayOfWeek().getValue() - 1);
        LocalDate lastDayOfWeek = firstDayOfWeek.plusDays(DIFFERENCE_BETWEEN_WEEK_DAYS);

        reportForm.setUsersWeek(reportFormId);
        reportForm.setWeekOfYear(yearAndWeek.right);
        reportForm.setFirstDayOfWeek(firstDayOfWeek.toString());
        reportForm.setLastDayOfWeek(lastDayOfWeek.toString());
        reportForm.setAverageSpeed(BigDecimal.valueOf(calculateAverageSpeed(jogsOnWeekList)).setScale(3, RoundingMode.HALF_UP));
        reportForm.setAverageTime(calculateAverageTime(jogsOnWeekList).toString());
        reportForm.setTotalDistance(BigDecimal.valueOf(calculateTotalDistance(jogsOnWeekList)).setScale(1, RoundingMode.HALF_UP));

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
