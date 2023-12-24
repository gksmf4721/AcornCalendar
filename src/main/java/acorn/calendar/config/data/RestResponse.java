package acorn.calendar.config.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<T> {

    @ApiModelProperty(position = 1, value = "결과 코드")
    private int resultCode;

    // @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    // @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(position = 2, value = "결과 요청 일시")
    public String resultDate;

    @ApiModelProperty(position = 3, value = "결과 데이터")
    private T data;

    public RestResponse(T data){
        this.resultCode = 200;
        this.resultDate = LocalDateTime.now().toString();
        this.data = data;
    }

    public RestResponse(int resultCode, T data){
        this.resultCode = resultCode;
        this.resultDate = LocalDateTime.now().toString();
        this.data = data;
    }

    @Getter
    @Setter
    public static class RestResultResponse {

        @ApiModelProperty(position = 1, value = "결과 코드")
        private int resultCode;

        @ApiModelProperty(position = 2, value = "결과 요청 일시")
        private String resultDate;

        @ApiModelProperty(position = 3, value = "결과 메시지")
        private String resultMsg;

        @Builder
        public RestResultResponse(int resultCode, String resultMsg){
            this.resultCode = resultCode == 0 ? 200 : resultCode;
            this.resultDate = LocalDateTime.now().toString();
            this.resultMsg = resultMsg == null ? "성공했습니다." : resultMsg;
        }
    }

}
