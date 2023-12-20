package acorn.calendar.config.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import net.bytebuddy.asm.Advice;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<T> {

    @ApiModelProperty(position = 1, value = "결과 코드")
    private int resultCode;

    // LocalDateFormat 객체 생성 시 오류. 임시적 Timestamp 대체.
    // @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    // @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(position = 2, value = "결과 요청 일시")
    public Timestamp resultDate;

    @ApiModelProperty(position = 3, value = "결과 데이터")
    private T data;

    public RestResponse(T data){
        this.resultCode = 200;
        // this.resultDate = LocalDateTime.now();
        this.resultDate = new Timestamp(System.currentTimeMillis());
        this.data = data;
    }

    public RestResponse(int resultCode, T data){
        this.resultCode = resultCode;
        // this.resultDate = LocalDateTime.now();
        this.resultDate = new Timestamp(System.currentTimeMillis());
        this.data = data;
    }

    public static class RestResultResponse {

        @ApiModelProperty(position = 1, value = "결과 코드")
        private int resultCode;

        // @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        // @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul", pattern = "yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(position = 2, value = "결과 요청 일시")
        private Timestamp resultDate;

        public RestResultResponse(int resultCode){
            this.resultCode = resultCode == 0 ? 200 : resultCode;
            // this.resultDate = LocalDateTime.now();
            this.resultDate = new Timestamp(System.currentTimeMillis());
        }

    }
}
