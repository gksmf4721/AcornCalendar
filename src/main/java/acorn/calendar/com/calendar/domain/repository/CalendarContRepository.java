package acorn.calendar.com.calendar.domain.repository;

import acorn.calendar.com.calendar.domain.entity.CalendarContEntity;
import acorn.calendar.com.calendar.domain.entity.CalendarEntity;
import acorn.calendar.com.calendar.domain.vo.CalendarVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarContRepository extends JpaRepository<CalendarContEntity, Long> {
    default CalendarContEntity returnCalendarEntity(CalendarVO.Jh_Cal_Cont_Calendar vo){
        return CalendarContEntity.builder()
                .contSeq(vo.getContSeq())
                .calSeq(vo.getCalSeq())
                .mSeq(vo.getMSeq())
                .contCont(vo.getContCont())
                .contTitle(vo.getContTitle())
                .contStartDt(vo.getContStartDt())
                .contEndDt(vo.getContEndDt())
                .contStartTm(vo.getContStartTm())
                .contEndTm(vo.getContEndTm())
                .contDelYn(vo.getContDelYn())
                .calDetailType(vo.getCalDetailType())
                .contAlldayYn(vo.getContAlldayYn())
                .build();
    }
}
