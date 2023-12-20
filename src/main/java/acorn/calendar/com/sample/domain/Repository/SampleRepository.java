package acorn.calendar.com.sample.domain.Repository;

import acorn.calendar.com.sample.domain.dto.SampleDTO;
import acorn.calendar.com.sample.domain.entity.SampleEntity;
import acorn.calendar.com.sample.domain.enums.SampleEnum;
import acorn.calendar.com.sample.domain.vo.SampleVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface SampleRepository extends JpaRepository<SampleEntity,Long> {
    List<SampleEntity> findByDelYnAndType(String delYn, String type);

    default List<SampleVO> findSampleVOByDelYnAndType(String delYn, String type) {
        List<SampleEntity> entities = findByDelYnAndType(delYn, type);
        return entities.stream()
                .map(entity -> new SampleVO(
                        entity.getSeq(),
                        entity.getTitle(),
                        entity.getContent(),
                        entity.getRegDt(),
                        entity.getSampleColumn1(),
                        entity.getSampleColumn2(),
                        entity.getType(),
                        entity.getDelYn())
                ).collect(Collectors.toList());
    }

}
