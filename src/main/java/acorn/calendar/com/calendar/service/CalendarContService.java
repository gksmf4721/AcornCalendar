package acorn.calendar.com.calendar.service;

import acorn.calendar.com.calendar.domain.dto.CalendarDTO;
import acorn.calendar.com.calendar.domain.entity.CalendarContEntity;
import acorn.calendar.com.calendar.domain.repository.CalendarContRepository;
import acorn.calendar.com.calendar.domain.vo.CalendarVO;
import acorn.calendar.config.data.AcornMap;
import acorn.calendar.config.model.LoginSession;
import lombok.RequiredArgsConstructor;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarContService {

    private final SqlSession sqlSession;

    private final CalendarContRepository calendarContRepository;

    public Double insertCalendarCont(CalendarVO.Jh_Cal_Cont_Calendar cont) throws ParseException {
        cont.setContDelYn("N");

        Double minusVactCnt = 0.0;

        if (cont.getCalDetailType().equals("S1")) {
            minusVactCnt = (calculateDaysDifference(cont.getContStartDt(), cont.getContEndDt())) * 1.0;
            AcornMap acornMap = new AcornMap();
            acornMap.put("m_seq", LoginSession.getLoginSession().getM_seq());
            acornMap.put("m_vact_cnt", minusVactCnt);
            sqlSession.update("mapper.com.member.updateVactCnt", acornMap);
        } else if (cont.getCalDetailType().equals("S2")) {
            minusVactCnt = 0.5;
            AcornMap acornMap = new AcornMap();
            acornMap.put("m_seq", LoginSession.getLoginSession().getM_seq());
            acornMap.put("m_vact_cnt", minusVactCnt);
            sqlSession.update("mapper.com.member.updateVactCnt", acornMap);
        }

        calendarContRepository.save(calendarContRepository.returnCalendarEntity(cont));

        Double vact = Double.parseDouble(LoginSession.getLoginSession().getM_vact_cnt()) - minusVactCnt;

        LoginSession.getLoginSession().setM_vact_cnt(String.valueOf(vact));

        return vact;
    }

    private long calculateDaysDifference(Date startDate, Date endDate) {
        long millisecondsPerDay = 24 * 60 * 60 * 1000;
        long startMillis = startDate.getTime();
        long endMillis = endDate.getTime();

        return ((endMillis - startMillis) / millisecondsPerDay) + 1;
    }

    public Double updateCalendarCont(CalendarVO.Jh_Cal_Cont_Calendar cont) throws ParseException {
        cont.setContDelYn("N");

        Double oriVactCnt = Double.parseDouble(LoginSession.getLoginSession().getM_vact_cnt());

        CalendarContEntity entity = calendarContRepository.findByContSeq(cont.getContSeq());

        Double vactCnt = 0.0;

        if (entity.getCalDetailType().equals("S1")) {
            if (cont.getCalDetailType().equals("S2")) {
                vactCnt = calculateDaysDifference(entity.getContStartDt(), entity.getContEndDt()) - 0.5;

            } else if (cont.getCalDetailType().equals("S1")) {
                long entityDate = calculateDaysDifference(entity.getContStartDt(), entity.getContEndDt());
                long contDate = calculateDaysDifference(cont.getContStartDt(), cont.getContEndDt());

                if (entityDate > contDate) {
                    vactCnt = (entityDate - contDate) * 1.0;

                } else if (contDate > entityDate) {
                    vactCnt = (contDate - entityDate) * -1.0;

                }
            } else {
                vactCnt = calculateDaysDifference(entity.getContStartDt(), entity.getContEndDt()) * 1.0;

            }
        } else if (entity.getCalDetailType().equals("S2")) {
            if (cont.getCalDetailType().equals("S1")) {
                vactCnt = 0.5 + calculateDaysDifference(cont.getContStartDt(), cont.getContEndDt()) * -1.0;

            } else if (!cont.getCalDetailType().equals("S2")) {
                vactCnt = 0.5;

            }
        } else {
            if (cont.getCalDetailType().equals("S1")) {
                vactCnt = calculateDaysDifference(cont.contStartDt, cont.contEndDt) * -1.0;

            } else if (cont.getCalDetailType().equals("S2")) {
                vactCnt = -0.5;

            }
        }

        AcornMap acornMap = new AcornMap();
        acornMap.put("m_seq", LoginSession.getLoginSession().getM_seq());
        acornMap.put("m_vact_cnt", vactCnt);

        calendarContRepository.save(calendarContRepository.returnCalendarEntity(cont));
        sqlSession.update("mapper.com.member.updateVactCnt2", acornMap);

        Double vact = Double.parseDouble(LoginSession.getLoginSession().getM_vact_cnt()) + vactCnt;

        LoginSession.getLoginSession().setM_vact_cnt(vact.toString());

        return vact;
    }

    public Double deleteCalednarCont(CalendarVO.Jh_Cal_Cont_Calendar cont) {
        CalendarContEntity entity = calendarContRepository.findByContSeq(cont.getContSeq());

        Double plusVactCnt = 0.0;
        if (cont.getCalDetailType().equals("S1")) {
            plusVactCnt = calculateDaysDifference(entity.getContStartDt(), entity.getContEndDt()) * 1.0;

        } else if (cont.getCalDetailType().equals("S2")) {
            plusVactCnt = 0.5;
        }

        AcornMap acornMap = new AcornMap();
        acornMap.put("m_seq", LoginSession.getLoginSession().getM_seq());
        acornMap.put("m_vact_cnt", plusVactCnt);
        acornMap.put("cont_seq", cont.getContSeq());

        sqlSession.update("mapper.com.member.updateVactCnt2", acornMap);
        sqlSession.update("mapper.com.member.deleteCont", acornMap);

        Double vact = Double.parseDouble(LoginSession.getLoginSession().getM_vact_cnt()) + plusVactCnt;

        LoginSession.getLoginSession().setM_vact_cnt(String.valueOf(vact));

        return vact;
    }

    public CalendarDTO.Jh_Cal_Cont_Calendar_ListResponse selectCalendarCont(long calSeq, long mSeq, String contStartDt,
            String contEntDt) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<CalendarDTO.Jh_Cal_Cont_Calendar_Response> entities = calendarContRepository.findCalendarEntitiesBy(
                "N", calSeq, sdf.parse(contStartDt), sdf.parse(contEntDt));
        return CalendarDTO.Jh_Cal_Cont_Calendar_ListResponse.of(entities);
    }

}
