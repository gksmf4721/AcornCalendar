package acorn.calendar.com.sample.domain.entity;

import acorn.calendar.com.sample.domain.enums.SampleEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Entity(name = "SAMPLE")
public class SampleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    @Comment("샘플_순번")
    private long seq;

    @Column(name = "TITLE")
    @Comment("샘플_제목")
    private String title;

    @Column(name = "CONTENT")
    @Comment("샘플_내용")
    private String content;

    @Column(name = "REG_DT", updatable = false)
    @Comment("샘플_생성일자")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul", pattern = "yyyy-MM-dd HH:mm")
    private Timestamp regDt;

    @Column(name = "SAMPLE_COLUMN1")
    @Comment("샘플_컬럼_1")
    private String sampleColumn1;

    @Column(name = "SAMPLE_COLUMN2")
    @Comment("샘플_컬럼_2")
    private String sampleColumn2;

    @Column(name = "TYPE")
    @Comment("샘플_타입")
    private String type;

    @Column(name = "DEL_YN")
    @Comment("삭제_여부")
    private String delYn;
}
