package juja.microservices.links.slackbot.exceptions;

/**
 * @author Ivan Shapovalov
 */
public class WrongCommandFormatException extends RuntimeException {
    public WrongCommandFormatException(String message) {
        super(message);
    }
}