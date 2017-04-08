package pl.aleksanderkotbury.bs.config;

import org.htmlcleaner.HtmlCleaner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HtmlCleanerConfiguration {

    @Bean
    public HtmlCleaner htmlCleaner() {
        return new HtmlCleaner();
    }
}
