package pl.aleksanderkotbury.bs.stooq;

import io.reactivex.schedulers.TestScheduler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.aleksanderkotbury.bs.rx.Schedulers;
import pl.aleksanderkotbury.bs.stooq.client.StooqClient;
import pl.aleksanderkotbury.bs.stooq.client.StooqData;
import pl.aleksanderkotbury.bs.stooq.repository.StooqMongoDto;
import pl.aleksanderkotbury.bs.stooq.repository.StooqRepository;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StooqDataUpdateSchedulerTest {

    @InjectMocks
    private StooqDataUpdateScheduler testObj;
    @Mock
    private StooqRepository stooqRepository;
    @Mock
    private StooqClient stooqClient;
    @Mock
    private StooqConfiguration stooqConfiguration;
    @Mock
    private Schedulers schedulers;
    @Mock
    private StooqDataToMongoDtoMapper stooqDataToMongoDtoMapper;

    @Mock
    private StooqData firstStooqData;
    @Mock
    private StooqData secondStooqData;
    @Mock
    private StooqMongoDto firstStooqMongoDto;
    @Mock
    private StooqMongoDto secondStooqMongoDto;

    @Before
    public void setUp() throws Exception {
        when(stooqConfiguration.getUpdateSecondsInterval()).thenReturn(60L);

        when(stooqDataToMongoDtoMapper.mapToMongoDto(Collections.singletonList(firstStooqData)))
                .thenReturn(Collections.singletonList(firstStooqMongoDto));
        when(stooqRepository.save(Collections.singletonList(firstStooqMongoDto)))
                .thenReturn(Collections.singletonList(firstStooqMongoDto));

        when(stooqDataToMongoDtoMapper.mapToMongoDto(Collections.singletonList(secondStooqData)))
                .thenReturn(Collections.singletonList(secondStooqMongoDto));
        when(stooqRepository.save(Collections.singletonList(secondStooqMongoDto)))
                .thenReturn(Collections.singletonList(secondStooqMongoDto));
    }

    @Test
    public void shouldSaveDataOnlyOnce_whenDataHasBeenNotChanged() {
        // given
        TestScheduler testScheduler = new TestScheduler();
        when(schedulers.computation()).thenReturn(testScheduler);

        when(stooqClient.getStooqData())
                .thenReturn(Collections.singletonList(firstStooqData))
                .thenReturn(Collections.singletonList(firstStooqData));

        // when
        testObj.onInit();
        testScheduler.advanceTimeBy(60L, TimeUnit.SECONDS);

        // then
        verify(stooqRepository).save(Collections.singletonList(firstStooqMongoDto));
        verify(stooqClient, times(2)).getStooqData();
    }

    @Test
    public void shouldUpdateDataOnMongo_whenStooqDataHasBeenChanged() {
        // given
        TestScheduler testScheduler = new TestScheduler();
        when(schedulers.computation()).thenReturn(testScheduler);

        when(stooqClient.getStooqData())
                .thenReturn(Collections.singletonList(firstStooqData))
                .thenReturn(Collections.singletonList(secondStooqData));

        // when
        testObj.onInit();
        testScheduler.advanceTimeBy(60L, TimeUnit.SECONDS);

        // then
        verify(stooqRepository).save(Collections.singletonList(firstStooqMongoDto));
        verify(stooqRepository).save(Collections.singletonList(secondStooqMongoDto));
        verify(stooqClient, times(2)).getStooqData();
    }

    @Test
    public void shouldContinueFetchingData_whenErrorOccurred() {
        // given
        TestScheduler testScheduler = new TestScheduler();
        when(schedulers.computation()).thenReturn(testScheduler);

        when(stooqClient.getStooqData())
                .thenThrow(new RuntimeException("force error!"))
                .thenReturn(Collections.singletonList(firstStooqData));

        // when
        testObj.onInit();
        testScheduler.advanceTimeBy(60L, TimeUnit.SECONDS);

        // then
        verify(stooqRepository).save(Collections.singletonList(firstStooqMongoDto));
        verify(stooqClient, times(2)).getStooqData();
    }
}