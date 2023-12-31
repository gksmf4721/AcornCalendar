package acorn.calendar.com.calendar.service;

import acorn.calendar.com.calendar.domain.dto.CalendarDTO;
import acorn.calendar.com.calendar.domain.entity.CalendarContEntity;
import acorn.calendar.com.calendar.domain.repository.CalendarContRepository;
import acorn.calendar.com.calendar.domain.vo.CalendarVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarContService {

    private final CalendarContRepository calendarContRepository;

    public void insertCalendarCont(CalendarVO.Jh_Cal_Cont_Calendar cont) throws ParseException {
        cont.setContDelYn("N");
        calendarContRepository.save(calendarContRepository.returnCalendarEntity(cont));
    }

    public CalendarDTO.Jh_Cal_Cont_Calendar_ListResponse selectCalendarCont(CalendarVO.Jh_Cal_Cont_Calendar cont){
        List<CalendarDTO.Jh_Cal_Cont_Calendar_Response> entities = calendarContRepository.findCalendarEntitiesBy(
                "N",cont.getCalSeq(),cont.getContStartDt(),cont.getContEndDt(),cont.getContStartDt(),cont.getContEndDt());
        return CalendarDTO.Jh_Cal_Cont_Calendar_ListResponse.of(entities);
    }

}
