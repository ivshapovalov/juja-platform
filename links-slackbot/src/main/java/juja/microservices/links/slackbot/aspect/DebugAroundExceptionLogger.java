package juja.microservices.links.slackbot.aspect;

import juja.microservices.links.slackbot.exceptions.BaseBotException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class DebugAroundExceptionLogger {

    @Around("execution(* juja.microservices.links.slackbot.exceptions..*.handle*(..))")
    public Object logExceptionHandleMethods(ProceedingJoinPoint call) throws Throwable {
        Object[] args = call.getArgs();
        String error = Arrays.deepToString(args);
        if (args.length == 1) {
            Object argument = args[0];
            if (argument instanceof BaseBotException) {
                error = ((BaseBotException) argument).detailMessage();
            }
        }
        String returnMessage = call.toShortString().replace("execution", "exception");
        log.warn("{} called with args '{}'!", returnMessage, error);
        return call.proceed();
    }
}
