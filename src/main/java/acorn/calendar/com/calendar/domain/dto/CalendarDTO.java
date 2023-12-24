package acorn.calendar.com.calendar.domain.dto;

import acorn.calendar.com.calendar.domain.vo.CalendarVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

public class CalendarDTO {

    @Getter
    @Builder
    public static class Jh_Cal_Calendar_Response{
        @ApiModelProperty(hidden = true)
        long calSeq;

        @ApiModelProperty(position = 1, value = "캘린더 타입")
        String calType;

        @ApiModelProperty(position = 2, value = "")
        long mSeqMake;

        @ApiModelProperty(position = 3, value = "")
        long mSeqParty;

        @ApiModelProperty(position = 4, value = "")
        String calName;

        @ApiModelProperty(position = 5, value = "")
        String calDelYn;

        public static Jh_Cal_Calendar_Response of(CalendarVO.Jh_Cal_Calendar calendarVO){
            return Jh_Cal_Calendar_Response.builder()
                    .calSeq(calendarVO.getCalSeq())
                    .calType(calendarVO.getCalType())
                    .mSeqMake(calendarVO.getMSeqMake())
                    .mSeqParty(calendarVO.getMSeqParty())
                    .calName(calendarVO.getCalName())
                    .calDelYn(calendarVO.getCalName())
                    .build();
        }
    }

}
