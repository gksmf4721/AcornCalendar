package acorn.calendar.com.calendar.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Getter
@AllArgsConstructor
@Entity(name="JH_CAL_CONT")
public class CalendarContEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONT_SEQ")
    long contSeq;

    @Column(name = "CAL_SEQ")
    long calSeq;

    @Column(name = "M_SEQ")
    long mSeq;

    @Nullable
    @Column(name = "CONT_CONT")
    String contCont;

    @Column(name = "CONT_TITLE")
    String contTitle;

    @Column(name = "CONT_START_DT")
    Date contStartDt;

    @Nullable
    @Column(name = "CONT_END_DT")
    Date contEndDt;

    @Nullable
    @Column(name = "CONT_START_TM")
    String contStartTm;

    @Nullable
    @Column(name = "CONT_END_TM")
    String contEndTm;

    @Column(name = "CONT_DEL_YN")
    String contDelYn;

    @Column(name = "CAL_DETAIL_TYPE")
    String calDetailType;

    @Column(name = "CONT_ALLDAY_YN")
    String contAlldayYn;

    public CalendarContEntity() {}
}
