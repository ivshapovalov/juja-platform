package juja.microservices.links.slackbot.util;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ben Novikov
 */
@Slf4j
public class SlackTextHandler {

    public List<String> getURLsFromText(String text) {
        List<String> result = new ArrayList<>();

        log.debug("Searching for URL links...");
        Pattern p = Pattern.compile("<(ht|f)tp(s?)://.*?([>|])");
        Matcher matcher = p.matcher(text);
        while (matcher.find()) {
            String found = matcher.group().subSequence(1, matcher.group().length() - 1).toString();
            log.debug("...found: [{}]", found);
            result.add(found);
        }
        log.debug("URL links found: [{}]", result.size());

        return result;
    }
}
