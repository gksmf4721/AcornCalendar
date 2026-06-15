package acorn.calendar.com.calendar.service;

import acorn.calendar.com.calendar.domain.dto.CalendarDTO;
import acorn.calendar.com.calendar.domain.repository.CalendarContRepository;
import acorn.calendar.com.calendar.domain.vo.CalendarVO;
import acorn.calendar.config.data.AcornMap;
import acorn.calendar.config.model.LoginSession;
import lombok.RequiredArgsConstructor;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarContService {

    private final SqlSession sqlSession;

    private final CalendarContRepository calendarContRepository;

    private final VacationService vacationService;

    public Double insertCalendarCont(CalendarVO.Jh_Cal_Cont_Calendar cont) throws ParseException {
        cont.setContDelYn("N");

        calendarContRepository.save(calendarContRepository.returnCalendarEntity(cont));

        return calculateRemainingVacationDays();
    }

    public Double updateCalendarCont(CalendarVO.Jh_Cal_Cont_Calendar cont) throws ParseException {
        cont.setContDelYn("N");

        calendarContRepository.save(calendarContRepository.returnCalendarEntity(cont));

        return calculateRemainingVacationDays();
    }

    public Double deleteCalednarCont(CalendarVO.Jh_Cal_Cont_Calendar cont) {
        AcornMap acornMap = new AcornMap();
        acornMap.put("cont_seq", cont.getContSeq());

        sqlSession.update("mapper.com.member.deleteCont", acornMap);

        return calculateRemainingVacationDays();
    }

    private Double calculateRemainingVacationDays() {
        LoginSession loginSession = LoginSession.getLoginSession();
        return vacationService.calculateRemainingDays(
                Long.parseLong(loginSession.getM_seq()),
                loginSession.getM_join_comp_dt(),
                parseVacationDays(loginSession.getM_vact_cnt()));
    }

    private double parseVacationDays(String vacationDays) {
        if (vacationDays == null || vacationDays.trim().isEmpty()) {
            return 0.0;
        }
        return Double.parseDouble(vacationDays);
    }

    public CalendarDTO.Jh_Cal_Cont_Calendar_ListResponse selectCalendarCont(long calSeq, long mSeq, String contStartDt,
            String contEntDt) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<CalendarDTO.Jh_Cal_Cont_Calendar_Response> entities = calendarContRepository.findCalendarEntitiesBy(
                "N", calSeq, sdf.parse(contStartDt), sdf.parse(contEntDt));
        return CalendarDTO.Jh_Cal_Cont_Calendar_ListResponse.of(entities);
    }

}
