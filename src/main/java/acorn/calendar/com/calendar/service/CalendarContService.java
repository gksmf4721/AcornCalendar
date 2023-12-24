package acorn.calendar.com.calendar.service;

import acorn.calendar.com.calendar.domain.entity.CalendarContEntity;
import acorn.calendar.com.calendar.domain.repository.CalendarContRepository;
import acorn.calendar.com.calendar.domain.vo.CalendarVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalendarContService {

    private final CalendarContRepository calendarContRepository;

    public void insertCalendarCont(CalendarVO.Jh_Cal_Cont_Calendar cont) {
        cont.setContDelYn("N");
        calendarContRepository.save(calendarContRepository.returnCalendarEntity(cont));
    }

}
