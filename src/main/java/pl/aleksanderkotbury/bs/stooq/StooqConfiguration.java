package pl.aleksanderkotbury.bs.stooq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class StooqConfiguration {

    private final URL stooqUrl;
    private final Long updateSecondsInterval;

    public StooqConfiguration(@Value("${stooq.url}") URL stooqUrl,
                              @Value("${stooq.updateSecondsInterval}") Long updateSecondsInterval) {
        this.stooqUrl = stooqUrl;
        this.updateSecondsInterval = updateSecondsInterval;
    }

    public URL getStooqUrl() {
        return stooqUrl;
    }

    public Long getUpdateSecondsInterval() {
        return updateSecondsInterval;
    }
}
