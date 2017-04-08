package pl.aleksanderkotbury.bs.stooq;

import org.springframework.stereotype.Component;
import pl.aleksanderkotbury.bs.stooq.client.StooqData;
import pl.aleksanderkotbury.bs.stooq.repository.StooqMongoDto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StooqDataToMongoDtoMapper {

    List<StooqMongoDto> mapToMongoDto(List<StooqData> stooqDataList) {
        return stooqDataList.stream().map(this::mapToMongoDto).collect(Collectors.toList());
    }

    private StooqMongoDto mapToMongoDto(StooqData stooqData) {
        return new StooqMongoDto(stooqData.getName(), stooqData.getValue(), LocalDateTime.now(ZoneOffset.UTC));
    }
}
