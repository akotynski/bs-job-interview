package pl.aleksanderkotbury.bs.stooq.client;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.springframework.stereotype.Component;
import pl.aleksanderkotbury.bs.stooq.StooqConfiguration;

import java.io.IOException;
import java.net.URL;

@Component
public class StooqHtmlProvider {

    private final HtmlCleaner htmlCleaner;
    private final StooqConfiguration stooqConfiguration;

    public StooqHtmlProvider(HtmlCleaner htmlCleaner, StooqConfiguration stooqConfiguration) {
        this.htmlCleaner = htmlCleaner;
        this.stooqConfiguration = stooqConfiguration;
    }

    TagNode getCleanStooq() {
        URL stooqUrl = stooqConfiguration.getStooqUrl();
        return getCleanHtml(stooqUrl);
    }

    private TagNode getCleanHtml(URL url) {
        try {
            return htmlCleaner.clean(url);
        } catch (IOException e) {
            throw new StooqException(e);
        }
    }
}
