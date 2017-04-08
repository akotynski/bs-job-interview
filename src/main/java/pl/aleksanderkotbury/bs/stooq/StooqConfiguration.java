package pl.aleksanderkotbury.bs.stooq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class StooqConfiguration {

    private final URL stooqUrl;

    public StooqConfiguration(@Value("${stooq.url}") URL stooqUrl) {
        this.stooqUrl = stooqUrl;
    }

    public URL getStooqUrl() {
        return stooqUrl;
    }
}
