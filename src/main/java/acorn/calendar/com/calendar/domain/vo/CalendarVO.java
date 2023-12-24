package acorn.calendar.com.calendar.domain.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDateTime;

public class CalendarVO {

    @Data
    @NoArgsConstructor
    public static class Jh_Cal_Calendar {
        public long calSeq;
        public String calType;
        public long mSeqMake;
        public long mSeqParty;
        public String calName;
        public String calDelYn;

        public Jh_Cal_Calendar(long calSeq, String calType, long mSeqMake, long mSeqParty, String calName, String calDelYn){
            this.calSeq = calSeq;
            this.calType = calType;
            this.mSeqMake = mSeqMake;
            this.mSeqParty = mSeqParty;
            this.calName = calName;
            this.calDelYn = calDelYn;
        }
    }

    @Data
    @NoArgsConstructor
    public static class Jh_Cal_Cont_Calendar {
        public long contSeq;
        public long calSeq;
        public long mSeq;
        public String contCont;
        public String contTitle;
        public String contStartDt;
        public String contEndDt;
        public String contStartTm;
        public String contEndTm;
        public String contDelYn;
        public String calDetailType;
        public String contAlldayYn;

        public Jh_Cal_Cont_Calendar(long contSeq, long calSeq, long mSeq, String contCont, String contTitle, String contStartDt, String contEndDt, String contStartTm, String contEndTm, String contDelYn, String calDetailType, String contAlldayYn){
            this.contSeq = contSeq;
            this.calSeq = calSeq;
            this.mSeq = mSeq;
            this.contCont = contCont;
            this.contTitle = contTitle;
            this.contStartDt = contStartDt;
            this.contEndDt = contEndDt;
            this.contStartTm = contStartTm;
            this.contEndTm = contEndTm;
            this.contDelYn = contDelYn;
            this.calDetailType = calDetailType;
            this.contAlldayYn = contAlldayYn;
        }
    }
}
