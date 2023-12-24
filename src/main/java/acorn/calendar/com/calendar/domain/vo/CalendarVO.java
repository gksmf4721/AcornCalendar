package acorn.calendar.com.calendar.domain.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public class Jh_Cal_Cont_Calendar {

    }
}
