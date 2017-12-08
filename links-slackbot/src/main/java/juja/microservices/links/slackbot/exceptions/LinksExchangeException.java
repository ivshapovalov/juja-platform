package juja.microservices.links.slackbot.exceptions;

/**
 * @author Danil Kuznetsov
 */
public class LinksExchangeException extends BaseBotException {
    public LinksExchangeException(ApiError error, Exception ex) {
        super(error, ex);
    }
}