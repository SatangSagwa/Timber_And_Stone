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
}
