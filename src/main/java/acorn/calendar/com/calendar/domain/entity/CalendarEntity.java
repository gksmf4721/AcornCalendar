package acorn.calendar.com.calendar.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.annotation.Nullable;
import javax.persistence.*;

@Builder
@AllArgsConstructor
@Entity(name="JH_CAL")
public class CalendarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAL_SEQ")
    @Comment("캘린더_시퀀스")
    long calSeq;

    @Column(name = "CAL_TYPE")
    String calType;

    @Column(name = "M_SEQ_MAKE")
    long mSeqMake;

    @Column(name = "M_SEQ_PARTY")
    long mSeqParty;

    @Column(name = "CAL_NAME")
    String calName;

    @Column(name = "CAL_DEL_YN")
    String calDelYn;

    public CalendarEntity() {}
}
