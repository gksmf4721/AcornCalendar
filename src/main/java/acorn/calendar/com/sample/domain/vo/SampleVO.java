package acorn.calendar.com.sample.domain.vo;

import acorn.calendar.com.sample.domain.enums.SampleEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SampleVO {

    private long seq;
    private String title;
    private String content;
    private LocalDateTime regDt;
    private String sampleColumn1;
    private String sampleColumn2;
    private String type;
    private String delYn;

    @Builder
    public SampleVO (long seq, String title, String content, LocalDateTime regDt, String sampleColumn1, String sampleColumn2, String type, String delYn){
        this.seq = seq;
        this.title = title;
        this.content = content;
        this.regDt = regDt;
        this.sampleColumn1 = sampleColumn1;
        this.sampleColumn2 = sampleColumn2;
        this.type = type;
        this.delYn = delYn;
    }
}
