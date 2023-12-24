package acorn.calendar.com.calendar.service;

import acorn.calendar.com.calendar.domain.dto.CalendarDTO;
import acorn.calendar.com.calendar.domain.entity.CalendarEntity;
import acorn.calendar.com.calendar.domain.repository.CalendarRepository;
import acorn.calendar.com.calendar.domain.vo.CalendarVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    public void insertCalendar(CalendarVO.Jh_Cal_Calendar calendar){
        calendar.setCalDelYn("N");
        calendarRepository.save(calendarRepository.returnCalendarEntity(calendar));
    }

    public CalendarDTO.Jh_Cal_Calendar_ListResponse selectCalendar(long mSeq){
        return CalendarDTO.Jh_Cal_Calendar_ListResponse
                .of(calendarRepository.findCalendarEntitiesBy("N",mSeq));
    }

}
