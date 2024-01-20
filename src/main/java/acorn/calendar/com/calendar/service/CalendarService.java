package acorn.calendar.com.calendar.service;

import acorn.calendar.com.calendar.domain.dto.CalendarDTO;
import acorn.calendar.com.calendar.domain.entity.CalendarEntity;
import acorn.calendar.com.calendar.domain.repository.CalendarRepository;
import acorn.calendar.com.calendar.domain.vo.CalendarVO;
import acorn.calendar.config.data.AcornMap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public AcornMap selectMyCalendar(Long mSeq){
        CalendarEntity entity = calendarRepository.findByCalDelYnAndSeqMake("N", mSeq);
        AcornMap acornMap = new AcornMap();
        acornMap.put("cal_seq",entity.getCalSeq());
        acornMap.put("cal_type",entity.getCalType());
        acornMap.put("m_seq_make",entity.getSeqMake());
        acornMap.put("m_seq_party",entity.getSeqParty());
        acornMap.put("cal_name",entity.getCalName());
        return acornMap;
    }

    public void MyCalendarCheck(Long mSeq){
        CalendarEntity entity = calendarRepository.findByCalDelYnAndSeqMake("N", mSeq);
        if(entity == null){
            CalendarVO.Jh_Cal_Calendar calendar = new CalendarVO.Jh_Cal_Calendar(mSeq,"N");
            insertCalendar(calendar);
        }
    }


    public void insertCalendar(CalendarVO.Jh_Cal_Calendar calendar){
        calendar.setCalDelYn("N");
        calendarRepository.save(calendarRepository.returnCalendarEntity(calendar));
    }

    public CalendarDTO.Jh_Cal_Calendar_ListResponse selectCalendar(long mSeq){
        return CalendarDTO.Jh_Cal_Calendar_ListResponse
                .of(calendarRepository.findCalendarEntitiesBy("N",mSeq));
    }

}
