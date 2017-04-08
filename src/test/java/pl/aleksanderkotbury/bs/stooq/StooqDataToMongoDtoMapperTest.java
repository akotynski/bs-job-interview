package pl.aleksanderkotbury.bs.stooq;

import org.junit.Test;
import pl.aleksanderkotbury.bs.stooq.client.StooqData;
import pl.aleksanderkotbury.bs.stooq.repository.StooqMongoDto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StooqDataToMongoDtoMapperTest {

    private StooqDataToMongoDtoMapper testObj = new StooqDataToMongoDtoMapper();

    @Test
    public void shouldMapStooqDataToMongoDto_withCurrentTimeAtUtcZone() {
        // given
        List<StooqData> stooqDataList = Arrays.asList(
                new StooqData("firstStooqName", 1.0),
                new StooqData("secondStooqName", 2.0)
        );

        // when
        List<StooqMongoDto> result = testObj.mapToMongoDto(stooqDataList);

        // then
        assertThat(result).hasSameSizeAs(stooqDataList);

        StooqMongoDto firstResult = result.get(0);
        assertThat(firstResult.getName()).isEqualTo("firstStooqName");
        assertThat(firstResult.getValue()).isEqualTo(1.0);
        assertThat(firstResult.getUpdateTime()).isBeforeOrEqualTo(LocalDateTime.now(ZoneOffset.UTC));

        StooqMongoDto secondResult = result.get(1);
        assertThat(secondResult.getName()).isEqualTo("secondStooqName");
        assertThat(secondResult.getValue()).isEqualTo(2.0);
        assertThat(secondResult.getUpdateTime()).isBeforeOrEqualTo(LocalDateTime.now(ZoneOffset.UTC));
    }
}