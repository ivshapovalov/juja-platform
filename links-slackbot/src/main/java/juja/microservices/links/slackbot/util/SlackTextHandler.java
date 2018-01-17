package juja.microservices.links.slackbot.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ben Novikov
 */
@Slf4j
@Service
public class SlackTextHandler {
    /**
     * URL detection is performed by Slack server, returning links surrounded with angle brackets, then
     * followed by a pipe character (|) and originally typed version of the URL, if it was not taken as-is.
     * This method returns bookmarkable links: starting with http, https, ftp and ftps prefixes only.
     */
    public List<String> getURLsFromText(String text) {
        List<String> result = new ArrayList<>();

        Pattern p = Pattern.compile("<(ht|f)tp(s?)://.*?([>|])");
        Matcher matcher = p.matcher(text);
        while (matcher.find()) {
            String found = matcher.group().subSequence(1, matcher.group().length() - 1).toString();
            log.info("URL link is found: [{}]", found);
            result.add(found);
        }

        return result;
    }
}
