package acorn.calendar.com.calendar.service;

import acorn.calendar.com.calendar.domain.entity.CalendarEntity;
import acorn.calendar.com.calendar.domain.repository.CalendarRepository;
import acorn.calendar.com.calendar.domain.vo.CalendarVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    public void insertCalendar(CalendarVO.Jh_Cal_Calendar calendar){
        calendar.setCalDelYn("N");
        calendarRepository.save(calendarRepository.returnCalendarEntity(calendar));
    }
}
