package acorn.calendar.com.calendar.service;

import acorn.calendar.com.calendar.domain.entity.CalendarContEntity;
import acorn.calendar.com.calendar.domain.repository.CalendarContRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationService {

    private static final String ANNUAL_LEAVE = "S1";
    private static final String HALF_DAY_LEAVE = "S2";

    private final CalendarContRepository calendarContRepository;

    public double calculateRemainingDays(long mSeq, String joinCompDt) {
        return calculateRemainingDays(mSeq, joinCompDt, 0.0);
    }

    public double calculateRemainingDays(long mSeq, String joinCompDt, double extraDays) {
        LocalDate joinDate = parseJoinDate(joinCompDt);
        if (joinDate == null) {
            return extraDays;
        }

        LocalDate today = LocalDate.now();
        double grantedDays = calculateGrantedDays(joinDate, today);
        double usedDays = calculateUsedDays(mSeq, joinDate, today);

        return grantedDays + extraDays - usedDays;
    }

    public double calculateGrantedDays(LocalDate joinDate, LocalDate today) {
        if (joinDate == null || today.isBefore(joinDate)) {
            return 0.0;
        }

        long fullYears = ChronoUnit.YEARS.between(joinDate, today);
        if (fullYears < 1) {
            long fullMonths = ChronoUnit.MONTHS.between(joinDate, today);
            return Math.min(fullMonths, 11);
        }

        long extraDays = Math.max(0, (fullYears - 1) / 2);
        return Math.min(15 + extraDays, 25);
    }

    private double calculateUsedDays(long mSeq, LocalDate joinDate, LocalDate today) {
        VacationPeriod period = getVacationPeriod(joinDate, today);
        Date periodStart = toDate(period.start);
        Date periodEnd = toEndDate(period.end);

        List<CalendarContEntity> vacationConts = calendarContRepository.findUsedVacationConts(
                mSeq, periodStart, periodEnd);

        return vacationConts.stream()
                .mapToDouble(entity -> calculateUsedDays(entity, period))
                .sum();
    }

    private double calculateUsedDays(CalendarContEntity entity, VacationPeriod period) {
        if (HALF_DAY_LEAVE.equals(entity.getCalDetailType())) {
            return 0.5;
        }

        if (!ANNUAL_LEAVE.equals(entity.getCalDetailType())) {
            return 0.0;
        }

        LocalDate startDate = toLocalDate(entity.getContStartDt());
        LocalDate endDate = entity.getContEndDt() == null ? startDate : toLocalDate(entity.getContEndDt());

        LocalDate usedStart = startDate.isBefore(period.start) ? period.start : startDate;
        LocalDate usedEnd = endDate.isAfter(period.end) ? period.end : endDate;

        if (usedEnd.isBefore(usedStart)) {
            return 0.0;
        }

        return ChronoUnit.DAYS.between(usedStart, usedEnd) + 1;
    }

    private VacationPeriod getVacationPeriod(LocalDate joinDate, LocalDate today) {
        long fullYears = ChronoUnit.YEARS.between(joinDate, today);
        if (fullYears < 1) {
            return new VacationPeriod(joinDate, joinDate.plusYears(1).minusDays(1));
        }

        LocalDate periodStart = joinDate.withYear(today.getYear());
        if (periodStart.isAfter(today)) {
            periodStart = periodStart.minusYears(1);
        }

        return new VacationPeriod(periodStart, periodStart.plusYears(1).minusDays(1));
    }

    private LocalDate parseJoinDate(String joinCompDt) {
        if (joinCompDt == null || joinCompDt.trim().isEmpty()) {
            return null;
        }

        return LocalDate.parse(joinCompDt.substring(0, 10));
    }

    private Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private Date toEndDate(LocalDate localDate) {
        return Date.from(localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).minusNanos(1).toInstant());
    }

    private LocalDate toLocalDate(Date date) {
        return new Date(date.getTime()).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private static class VacationPeriod {
        private final LocalDate start;
        private final LocalDate end;

        private VacationPeriod(LocalDate start, LocalDate end) {
            this.start = start;
            this.end = end;
        }
    }
}
