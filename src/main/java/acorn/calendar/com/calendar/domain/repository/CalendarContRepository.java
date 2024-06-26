package acorn.calendar.com.calendar.domain.repository;

import acorn.calendar.com.calendar.domain.dto.CalendarDTO;
import acorn.calendar.com.calendar.domain.entity.CalendarContEntity;
import acorn.calendar.com.calendar.domain.vo.CalendarVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public interface CalendarContRepository extends JpaRepository<CalendarContEntity, Long> {

    // List<CalendarContEntity>
    // findByContStartDtBetweenOrContEndDtBetweenAndContDelYnAndCalSeq(
    // Date contStartDt1, Date contEndDt1, Date contStartDt2, Date contEndDt2,
    // String contDelYn, long calSeq);

    CalendarContEntity findByContSeq(long contSeq);

    @Query("SELECT ce FROM JH_CAL_CONT ce WHERE " +
            "((ce.contStartDt BETWEEN :contStartDt1 AND :contEndDt1) OR (ce.contEndDt BETWEEN :contStartDt2 AND :contEndDt2)) "
            +
            "AND ce.contDelYn = :contDelYn " +
            "AND ce.calSeq = :calSeq")
    List<CalendarContEntity> findByContStartDtBetweenOrContEndDtBetweenAndContDelYnAndCalSeq(
            @Param("contStartDt1") Date contStartDt1,
            @Param("contEndDt1") Date contEndDt1,
            @Param("contStartDt2") Date contStartDt2,
            @Param("contEndDt2") Date contEndDt2,
            @Param("contDelYn") String contDelYn,
            @Param("calSeq") long calSeq);

    default CalendarContEntity returnCalendarEntity(CalendarVO.Jh_Cal_Cont_Calendar vo) throws ParseException {

        // 2024 - 01 - 05 효니 수정
        // 이 부분은 효니가 이미 시작하는 날짜랑 마지막날짜를 잘라서 주기 때문에 재욱이가 이렇게 자를 필요가 없어! 그래서 효니가 주석처리하고,
        // contStartTm, contEndTm안에 그냥 vo에서 가져온 값으로 넣엇어!

        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // Date startDt = sdf.parse(vo.getContStartDt().toString());
        // Date endDt = sdf.parse(vo.getContEndDt().toString());

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

    default List<CalendarDTO.Jh_Cal_Cont_Calendar_Response> findCalendarEntitiesBy(
            String contDelYn, long calSeq, Date contStartDt, Date contEndDt) {
        contStartDt = format(contStartDt);
        contEndDt = format(contEndDt);

        List<CalendarContEntity> entities = findByContStartDtBetweenOrContEndDtBetweenAndContDelYnAndCalSeq(
                contStartDt, contEndDt, contStartDt, contEndDt, contDelYn, calSeq);
        return entities.stream().map(
                entity -> CalendarDTO.Jh_Cal_Cont_Calendar_Response.ofEntity(entity))
                .collect(Collectors.toList());
    }

    default Date format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = sdf.format(date);

        Date parse = null;

        try {
            parse = sdf.parse(formatDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parse;
    }
}
