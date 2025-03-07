package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.models.Period;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PeriodService {

    public Integer getAmountOfDays(Period period) {
        LocalDate start = period.getStartDate();
        LocalDate end = period.getEndDate();
        return period.getEndDate().getDayOfYear() - period.getStartDate().getDayOfYear();
    }


    // https://chatgpt.com/share/67b4a4fb-a588-800b-9894-16722dd3a37d
    public boolean isPeriodMatching(Period period, LocalDate startDate, LocalDate endDate) {
        boolean overlap = (startDate.isEqual(period.getStartDate()) || startDate.isAfter(period.getStartDate())) &&
                (endDate.isEqual(period.getEndDate()) || endDate.isBefore(period.getEndDate()));
        return overlap;
    }
}
