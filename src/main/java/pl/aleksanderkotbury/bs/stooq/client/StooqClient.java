package pl.aleksanderkotbury.bs.stooq.client;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.springframework.stereotype.Component;
import pl.aleksanderkotbury.bs.stooq.StooqConfiguration;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class StooqClient {

    public static final String ID_ATTRIBUTE = "id";

    private final StooqHtmlProvider stooqHtmlProvider;

    public StooqClient(StooqHtmlProvider stooqHtmlProvider) {
        this.stooqHtmlProvider = stooqHtmlProvider;
    }

    public List<StooqData> getStooqData() {
        TagNode cleanStooq = stooqHtmlProvider.getCleanStooq();

        return Stream.of(StooqTicker.values())
                .map(ticker -> findStooqData(ticker, cleanStooq))
                .collect(Collectors.toList());
    }

    private StooqData findStooqData(StooqTicker stooqTicker, TagNode cleanStooq) {
        CharSequence text = cleanStooq.findElementByAttValue(ID_ATTRIBUTE, stooqTicker.getDomId(), true, true).getText();
        double wigValue = Double.parseDouble(text.toString());
        return new StooqData(stooqTicker.getName(), wigValue);
    }
}
