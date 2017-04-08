package pl.aleksanderkotbury.bs.stooq.client;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.aleksanderkotbury.bs.stooq.StooqConfiguration;

import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StooqHtmlProviderTest {

    @InjectMocks
    private StooqHtmlProvider testObj;
    @Mock
    private HtmlCleaner htmlCleaner;
    @Mock
    private StooqConfiguration stooqConfiguration;
    @Mock
    private TagNode stooqHtmlTags;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldReturnCleanHtmlTagsFromHtmlCleaner() throws IOException {
        // given
        URL stooqUrl = new URL("https://stooq.pl");
        when(stooqConfiguration.getStooqUrl()).thenReturn(stooqUrl);
        when(htmlCleaner.clean(stooqUrl)).thenReturn(stooqHtmlTags);

        // when
        TagNode result = testObj.getCleanStooq();

        // then
        assertThat(result).isSameAs(stooqHtmlTags);
    }

    @Test
    public void shouldThrowStooqException_whenHtmlObjectThrowsIOException() throws IOException {
        // given
        URL stooqUrl = new URL("https://stooq.pl");
        when(stooqConfiguration.getStooqUrl()).thenReturn(stooqUrl);
        IOException ioException = new IOException();
        when(htmlCleaner.clean(stooqUrl)).thenThrow(ioException);

        expectedException.expect(StooqException.class);
        expectedException.expectCause(is(ioException));

        // when
        testObj.getCleanStooq();
    }
}