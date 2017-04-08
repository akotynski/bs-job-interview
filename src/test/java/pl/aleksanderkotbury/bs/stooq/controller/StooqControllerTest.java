package pl.aleksanderkotbury.bs.stooq.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.reactivex.schedulers.TestScheduler;
import org.apache.http.entity.ContentType;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.aleksanderkotbury.bs.rx.Schedulers;
import pl.aleksanderkotbury.bs.stooq.TestConfiguration;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestConfiguration.class)
public class StooqControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Schedulers schedulers;

    @Rule
    public WireMockRule wireMockServer = new WireMockRule(9000);

    @Test
    public void shouldReturnStooqValues() throws Exception {
        // given
        Path stooqPath = Paths.get(getClass().getResource("../stooq.html").toURI());
        String stooqContent = new String(Files.readAllBytes(stooqPath), StandardCharsets.UTF_8);
        wireMockServer.stubFor(WireMock.get("/stooq").willReturn(okForContentType(ContentType.TEXT_HTML.getMimeType(), stooqContent)));
        TestScheduler testScheduler = (TestScheduler) schedulers.computation();
        testScheduler.advanceTimeTo(60, TimeUnit.SECONDS);

        // when
        ResultActions mvcResult = mockMvc.perform(get("/stooqs"));

        // then
        mvcResult.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("[0].name", equalTo("WIG")))
                .andExpect(jsonPath("[0].value", equalTo(59287.92)))
                .andExpect(jsonPath("[0].updateTime", beforeNowDateTime()))
                .andExpect(jsonPath("[1].name", equalTo("WIG20")))
                .andExpect(jsonPath("[1].value", equalTo(2248.33)))
                .andExpect(jsonPath("[1].updateTime", beforeNowDateTime()))
                .andExpect(jsonPath("[2].name", equalTo("WIG20 Fut")))
                .andExpect(jsonPath("[2].value", equalTo(2233.0)))
                .andExpect(jsonPath("[2].updateTime", beforeNowDateTime()))
                .andExpect(jsonPath("[3].name", equalTo("mWIG40")))
                .andExpect(jsonPath("[3].value", equalTo(4774.01)))
                .andExpect(jsonPath("[3].updateTime", beforeNowDateTime()))
                .andExpect(jsonPath("[4].name", equalTo("sWIG80")))
                .andExpect(jsonPath("[4].value", equalTo(16536.65)))
                .andExpect(jsonPath("[4].updateTime", beforeNowDateTime()));
    }

    private Matcher<String> beforeNowDateTime() {
        return new TypeSafeMatcher<String>() {
            @Override
            protected boolean matchesSafely(String dateTime) {
                LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
                LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
                return localDateTime.isBefore(now);
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }
}