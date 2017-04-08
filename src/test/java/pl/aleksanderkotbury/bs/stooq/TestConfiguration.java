package pl.aleksanderkotbury.bs.stooq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.aleksanderkotbury.bs.ApplicationEntryPoint;
import pl.aleksanderkotbury.bs.rx.Schedulers;
import pl.aleksanderkotbury.bs.stooq.rx.TestSchedulers;

@Configuration
@Import(ApplicationEntryPoint.class)
public class TestConfiguration {

    @Bean
    public Schedulers schedulers() {
        return new TestSchedulers();
    }
}
