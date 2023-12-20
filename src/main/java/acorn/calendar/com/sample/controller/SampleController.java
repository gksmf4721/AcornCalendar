package acorn.calendar.com.sample.controller;

import acorn.calendar.com.sample.domain.dto.SampleDTO;
import acorn.calendar.com.sample.domain.enums.SampleEnum;
import acorn.calendar.com.sample.service.SampleService;
import acorn.calendar.config.data.RestResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Sample")
@RequestMapping("/api/v1")
public class SampleController {

    private final SampleService sampleService;

    @ApiOperation(tags = "Sample", value = "샘플 목록 조회", notes = "타입 조건 조회")
    @GetMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<SampleDTO.SampleListResponse>> sample(@RequestParam("type") SampleEnum type){
        SampleDTO.SampleListResponse response = sampleService.sampleList(type);
        System.out.println("리스폰즈 : "+response.getItems().get(0).getRegDt());
        System.out.println("리스폰즈 : "+response.getItems().get(0).getTitle());
        return ResponseEntity.ok(new RestResponse<SampleDTO.SampleListResponse>(response));
    }
}

