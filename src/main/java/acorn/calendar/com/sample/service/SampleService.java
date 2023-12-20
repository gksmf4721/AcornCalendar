package acorn.calendar.com.sample.service;

import acorn.calendar.com.sample.domain.Repository.SampleRepository;
import acorn.calendar.com.sample.domain.dto.SampleDTO;
import acorn.calendar.com.sample.domain.entity.SampleEntity;
import acorn.calendar.com.sample.domain.enums.SampleEnum;
import acorn.calendar.com.sample.domain.vo.SampleVO;
import javassist.tools.rmi.Sample;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleRepository sampleRepository;

    public SampleDTO.SampleListResponse sampleList(SampleEnum type){
        List<SampleVO> entity = sampleRepository.findSampleVOByDelYnAndType("N",type.getValue());
        return SampleDTO.SampleListResponse.of(entity.stream().map(SampleDTO.SampleResponse::of).collect(Collectors.toList()));
    }
}
