package pl.aleksanderkotbury.bs.stooq.rx;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;
import pl.aleksanderkotbury.bs.rx.Schedulers;

public class TestSchedulers implements Schedulers {

    private TestScheduler testScheduler = new TestScheduler();

    @Override
    public Scheduler computation() {
        return testScheduler;
    }
}
