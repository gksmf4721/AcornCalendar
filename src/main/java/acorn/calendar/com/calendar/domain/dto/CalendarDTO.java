package acorn.calendar.com.calendar.domain.dto;

import acorn.calendar.com.calendar.domain.entity.CalendarEntity;
import acorn.calendar.com.calendar.domain.vo.CalendarVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

public class CalendarDTO {

    @Getter
    @Builder
    public static class Jh_Cal_Calendar_Response {
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

        public static Jh_Cal_Calendar_Response ofEntity(CalendarEntity entity){
            return Jh_Cal_Calendar_Response.builder()
                    .calSeq(entity.getCalSeq())
                    .calType(entity.getCalType())
                    .mSeqMake(entity.getSeqMake())
                    .mSeqParty(entity.getSeqParty())
                    .calName(entity.getCalName())
                    .calDelYn(entity.getCalName())
                    .build();
        }
    }

    @Builder
    @Getter // 직렬화 오류 해결
    public static class Jh_Cal_Calendar_ListResponse {
        @ApiModelProperty(position = 1, value = "결과 리스트")
        List<CalendarDTO.Jh_Cal_Calendar_Response> items;

        public static Jh_Cal_Calendar_ListResponse of(List<CalendarDTO.Jh_Cal_Calendar_Response> items) {
            return Jh_Cal_Calendar_ListResponse.builder()
                    .items(items)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Jh_Cal_Cont_Calendar_Response {

        @ApiModelProperty(hidden = true)
        public long contSeq;

        @ApiModelProperty(position = 1, value = "캘린더 타입")
        public long calSeq;

        @ApiModelProperty(position = 2, value = "캘린더 타입")
        public long mSeq;

        @ApiModelProperty(position = 3, value = "캘린더 타입")
        public String contCont;

        @ApiModelProperty(position = 4, value = "캘린더 타입")
        public String title;

        @ApiModelProperty(position = 5, value = "캘린더 타입")
        public String contStartDt;

        @ApiModelProperty(position = 6, value = "캘린더 타입")
        public String contEndDt;

        @ApiModelProperty(position = 7, value = "캘린더 타입")
        public String contStartTm;

        @ApiModelProperty(position = 8, value = "캘린더 타입")
        public String contEndTm;

        @ApiModelProperty(position = 9, value = "캘린더 타입")
        public String contDelYn;

        @ApiModelProperty(position = 10, value = "캘린더 타입")
        public String calDetailType;

        @ApiModelProperty(position = 11, value = "캘린더 타입")
        public String contAlldayYn;

    }

}
