package juja.microservices.links.slackbot.util;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;

/**
 * @author Ben Novikov
 */
public class SlackTextHandlerTest {
    private SlackTextHandler slackTextHandler;
    private List<String> expected;
    private String text;

    @Before
    public void setUp() {
        slackTextHandler = new SlackTextHandler();
        text =  "Ссылка <> <http://google.com?parameter=value&also=another|google.com> " +
                "Ссылка <1> http://google.com " +
                "Ссылка <2 <yandex.ru/page?parameter=value&also=another> " +
                "Ссылка 3> https://google.info " +
                "Ссылка 4 <<https://mail.ru> " +
                "Ссылка 5 <<> google.ya " +
                "Ссылка 6 <http://yahoo.us|yahoo.us> " +
                "Ссылка 7 <mail@microsoft.com " +
                "Ссылка 8 <mailto:mail.mail@post.by|mail@post.by> " +
                "Ссылка 9 ftp://ya.google.au> " +
                "Ссылка 9 <ftp://ya.google.mail.ya.au> " +
                "Ссылка 10 <ftp://name:pass@warehouse.com> ";
        expected = new ArrayList<>(Arrays.asList(
                "http://google.com?parameter=value&also=another",
                "https://mail.ru",
                "http://yahoo.us",
                "ftp://ya.google.mail.ya.au",
                "ftp://name:pass@warehouse.com"));
    }

    @Test
    public void getURLsFromText_withTextGiven_shouldReturnFiveLinks() {
        List<String> actual = slackTextHandler.getURLsFromText(text);

        assertThat(actual, IsIterableContainingInAnyOrder.containsInAnyOrder(expected.toArray()));
    }
}
