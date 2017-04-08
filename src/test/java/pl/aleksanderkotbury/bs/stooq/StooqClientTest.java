package pl.aleksanderkotbury.bs.stooq;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.http.entity.ContentType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.aleksanderkotbury.bs.stooq.client.StooqClient;
import pl.aleksanderkotbury.bs.stooq.client.StooqData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StooqClientTest {

    @Rule
    public WireMockRule wireMockServer = new WireMockRule(9000);
    @Autowired
    private StooqClient testObj;

    @Test
    public void shouldReturnDataFromStooqService() throws URISyntaxException, IOException {
        // given
        Path stooqPath = Paths.get(getClass().getResource("stooq.html").toURI());
        String stooqContent = new String(Files.readAllBytes(stooqPath), StandardCharsets.UTF_8);
        wireMockServer.stubFor(get("/stooq").willReturn(okForContentType(ContentType.TEXT_HTML.getMimeType(), stooqContent)));

        List<StooqData> expectedStooqs = Arrays.asList(
                new StooqData("WIG", 59287.92),
                new StooqData("WIG20", 2248.33),
                new StooqData("WIG20 Fut", 2233.0),
                new StooqData("mWIG40", 4774.01),
                new StooqData("sWIG80", 16536.65)
        );

        // when
        List<StooqData> result = testObj.getStooqData();

        // then
        assertThat(result).containsAll(expectedStooqs);
    }
}