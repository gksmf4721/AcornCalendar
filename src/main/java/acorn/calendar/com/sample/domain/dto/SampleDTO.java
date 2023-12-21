package acorn.calendar.com.sample.domain.dto;

import acorn.calendar.com.sample.domain.enums.SampleEnum;
import acorn.calendar.com.sample.domain.vo.SampleVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class SampleDTO {

    @Builder
    @Getter
    public static class SampleResponse {

        @ApiModelProperty(hidden = true)
        private long seq;

        @ApiModelProperty(position = 1, value = "제목")
        private String title;

        @ApiModelProperty(position = 2, value = "내용")
        private String content;

        @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul", pattern = "yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(position = 3, value = "생성일자")
        private String regDt;

        @ApiModelProperty(position = 4, value = "샘플 컬럼 1")
        private String sampleColumn1;

        @ApiModelProperty(position = 5, value = "샘플 컬럼 2")
        private String sampleColumn2;

        @ApiModelProperty(position = 6, value = "샘플 타입")
        private String type;

        @ApiModelProperty(position = 7, value = "삭제 여부")
        private String delYn;

        public static SampleResponse of(SampleVO sampleVO) {
            return SampleResponse.builder()
                    .seq(sampleVO.getSeq())
                    .title(sampleVO.getTitle())
                    .content(sampleVO.getContent())
                    .regDt(sampleVO.getRegDt().toString())
                    .sampleColumn1(sampleVO.getSampleColumn1())
                    .sampleColumn2(sampleVO.getSampleColumn2())
                    .type(sampleVO.getType())
                    .delYn(sampleVO.getDelYn())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class SampleListResponse {

        @ApiModelProperty(position = 1, value = "샘플 목록 조회")
        private List<SampleDTO.SampleResponse> items;

        public static SampleListResponse of(List<SampleDTO.SampleResponse> sampleResponse){
            return SampleListResponse.builder().items(sampleResponse).build();
        }

    }
}
