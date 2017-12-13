package juja.microservices.links.slackbot.util;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Ivan Shapovalov
 * @author Ben Novikov
 */
public class SlackTextHandlerTest {
    private SlackTextHandler slackTextHandler;

    @Before
    public void setUp() {
        slackTextHandler = new SlackTextHandler();
    }

    @Test
    public void getURLsFromText_withTextGiven_shouldReturnFiveLinks() {
        //Given
        String text =
                "Case <1> http://google.com " +
                        "Case <2 <yandex.ru/page?parameter=value&also=another> " +
                        "Case 3> https://google.info " +
                        "Case 4 <<https://mail.ru> " +
                        "Case 5 <<> google.ya " +
                        "Case 6 <http://yahoo.us|yahoo.us> " +
                        "Case 7 <mail@microsoft.com " +
                        "Case 8 <mailto:mail.mail@post.by|mail@post.by> " +
                        "Case 9 ftp://ya.google.au> " +
                        "Case 9 <ftp://ya.google.mail.ya.au> " +
                        "Case 10 <ftp://name:pass@warehouse.com> " +
                        "Case 11 <http:|ajdfhasdhfbks> " +
                        "Case <> <http://google.com?parameter=value&also=another|google.com> ";
        List<String> expected = new ArrayList<>(Arrays.asList(
                "https://mail.ru",
                "http://yahoo.us",
                "ftp://ya.google.mail.ya.au",
                "ftp://name:pass@warehouse.com",
                "http://google.com?parameter=value&also=another"));

        //When
        List<String> actual = slackTextHandler.getURLsFromText(text);

        //Then
        assertThat(actual, IsIterableContainingInAnyOrder.containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void getURLsFromText_withEmptyText_shouldReturnEmptyList() {
        //Given
        String emptyText = "";

        //When
        List<String> actual = slackTextHandler.getURLsFromText(emptyText);

        //Then
        assertTrue(actual.isEmpty());
    }
}
