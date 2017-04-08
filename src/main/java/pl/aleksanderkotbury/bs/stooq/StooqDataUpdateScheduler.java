package pl.aleksanderkotbury.bs.stooq;

import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.aleksanderkotbury.bs.rx.Schedulers;
import pl.aleksanderkotbury.bs.stooq.client.StooqClient;
import pl.aleksanderkotbury.bs.stooq.repository.StooqRepository;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
public class StooqDataUpdateScheduler {

    private final StooqRepository stooqRepository;
    private final StooqClient stooqClient;
    private final StooqConfiguration stooqConfiguration;
    private final Schedulers schedulers;
    private final StooqDataToMongoDtoMapper stooqDataToMongoDtoMapper;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public StooqDataUpdateScheduler(StooqRepository stooqRepository, StooqClient stooqClient,
                                    StooqConfiguration stooqConfiguration, Schedulers schedulers,
                                    StooqDataToMongoDtoMapper stooqDataToMongoDtoMapper) {
        this.stooqRepository = stooqRepository;
        this.stooqClient = stooqClient;
        this.stooqConfiguration = stooqConfiguration;
        this.schedulers = schedulers;
        this.stooqDataToMongoDtoMapper = stooqDataToMongoDtoMapper;
    }

    @PostConstruct
    public void onInit() {
        Long updateSecondsInterval = stooqConfiguration.getUpdateSecondsInterval();
        Flowable.interval(0L, updateSecondsInterval, TimeUnit.SECONDS, schedulers.computation())
                .map(l -> stooqClient.getStooqData())
                .doOnNext(stooqDataList -> logger.debug("Received data from stooq {}", stooqDataList))
                .distinctUntilChanged()
                .map(stooqDataToMongoDtoMapper::mapToMongoDto)
                .map(stooqRepository::save)
                .subscribe(
                        savedEntities -> logger.info("Stooq entities has been updated {}", savedEntities),
                        throwable -> logger.error("Something went wrong during entities update", throwable)
                );
    }
}
