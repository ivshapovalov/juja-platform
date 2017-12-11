package juja.microservices.links.slackbot.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static juja.microservices.links.slackbot.util.SlackTextHandler.getURLsFromText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Ben Novikov
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SlackTextHandlerTest {
    @Test()
    public void getURLsFromText_getSet_returnsExpectedSet() {
        String text =
                "Привет, вот ссылка http://mail.ru/page?parameter=value&also=another " +
                "Привет, вот ссылка: mail.ru/page?parameter=value&also=another " +
                "Привет, вот ссылка https://mail.ru Привет, вот ссылка <https://mail.ru> " +
                "Привет, вот ссылка mail.ru Привет, вот ссылка <http://mail.ru|mail.ru> " +
                "Привет, вот ссылка mail@mail.ru Привет, вот ссылка <mailto:mail.mail@mail.ru|mail@mail.ru> " +
                "Привет, вот ссылка ftp://ivan.mail.ru Привет, вот ссылка <ftp://name:pass@warehouse.com>";
        Set<String> expectedSet = new HashSet<>(Arrays.asList(
                "http://mail.ru/page?parameter=value&also=another",
                "https://mail.ru",
                "http://mail.ru",
                "ftp://ivan.mail.ru",
                "ftp://name:pass@warehouse.com"));

        Set<String> actualSet = getURLsFromText(text);

        assertNotNull(actualSet);
        assertEquals("Actual Set should be equal to expected", expectedSet, actualSet);
    }
}
