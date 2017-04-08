package pl.aleksanderkotbury.bs.rx;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.springframework.stereotype.Component;

@Component
public class DefaultSchedulers implements pl.aleksanderkotbury.bs.rx.Schedulers {

    public Scheduler computation() {
        return Schedulers.computation();
    }
}
