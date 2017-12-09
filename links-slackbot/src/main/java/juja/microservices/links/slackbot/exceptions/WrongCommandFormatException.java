package juja.microservices.links.slackbot.exceptions;

/**
 * @author Nikolay Horushko
 */
public class WrongCommandFormatException extends RuntimeException  {
    public WrongCommandFormatException(String message) {
        super(message);
    }
}