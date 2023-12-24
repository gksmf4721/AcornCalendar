package acorn.calendar.com.calendar.domain.repository;

import acorn.calendar.com.calendar.domain.entity.CalendarEntity;
import acorn.calendar.com.calendar.domain.vo.CalendarVO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<CalendarEntity,Long> {

    default CalendarEntity returnCalendarEntity(CalendarVO.Jh_Cal_Calendar vo){
        return CalendarEntity.builder()
                .calSeq(vo.getCalSeq())
                .calType(vo.getCalType())
                .mSeqMake(vo.getMSeqMake())
                .mSeqParty(vo.getMSeqParty())
                .calName((vo.getCalName()))
                .calDelYn(vo.getCalDelYn())
                .build();
    }
}
