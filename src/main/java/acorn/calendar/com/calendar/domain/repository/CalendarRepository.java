package acorn.calendar.com.calendar.domain.repository;

import acorn.calendar.com.calendar.domain.dto.CalendarDTO;
import acorn.calendar.com.calendar.domain.entity.CalendarEntity;
import acorn.calendar.com.calendar.domain.vo.CalendarVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarEntity,Long> {

    List<CalendarEntity> findByCalDelYnAndSeqMakeOrSeqParty(String calDelYn, long seqMake, long seqParty);

    default CalendarEntity returnCalendarEntity(CalendarVO.Jh_Cal_Calendar vo){
        return CalendarEntity.builder()
                .calSeq(vo.getCalSeq())
                .calType(vo.getCalType())
                .seqMake(vo.getMSeqMake())
                .seqParty(vo.getMSeqParty())
                .calName((vo.getCalName()))
                .calDelYn(vo.getCalDelYn())
                .build();
    }

    default List<CalendarDTO.Jh_Cal_Calendar_Response> findCalendarEntitiesBy(String calDelYn, long mSeq) {
        List<CalendarEntity> entities = findByCalDelYnAndSeqMakeOrSeqParty(calDelYn, mSeq, mSeq);
        List<String> sort = Arrays.asList("S","C","F");
        Collections.sort(entities, Comparator.comparingInt(
                entity -> sort.indexOf(entity.getCalType())));
        return entities.stream().map(
                entity -> CalendarDTO.Jh_Cal_Calendar_Response.ofEntity(entity))
                .collect(Collectors.toList());
    }


}
