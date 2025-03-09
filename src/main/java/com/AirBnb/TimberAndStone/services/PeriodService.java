package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.exceptions.ConflictException;
import com.AirBnb.TimberAndStone.models.Period;
import com.AirBnb.TimberAndStone.requests.booking.BookingRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PeriodService {

    public Integer getAmountOfDays(Period period) {
        LocalDate start = period.getStartDate();
        LocalDate end = period.getEndDate();
        return period.getEndDate().getDayOfYear() - period.getStartDate().getDayOfYear();
    }

    public void isPeriodMatching(List<Period> periods, BookingRequest request) {
        Boolean matching = false;
        Boolean available = false;
        for (Period period : periods) {
            matching = isPeriodMatching(period, request.getStartDate(), request.getEndDate());
            System.out.println(matching);
            System.out.println("Period: " + period.getStartDate() + " - " + period.getEndDate());
            System.out.println("StartDate " + request.getStartDate() + " EndDate " + request.getEndDate() + "\n--------------------------------------------");
            if (matching) {
                available = true;
                break;
            }
        }
        if (!available) {
            throw new ConflictException("The period you want is not available");
        }
    }

    // https://chatgpt.com/share/67b4a4fb-a588-800b-9894-16722dd3a37d
    public boolean isPeriodMatching(Period period, LocalDate startDate, LocalDate endDate) {
        boolean overlap = (startDate.isEqual(period.getStartDate()) || startDate.isAfter(period.getStartDate())) &&
                (endDate.isEqual(period.getEndDate()) || endDate.isBefore(period.getEndDate()));
        return overlap;
    }
}
