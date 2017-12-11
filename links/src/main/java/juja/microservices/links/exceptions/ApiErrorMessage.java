package juja.microservices.links.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class ApiErrorMessage {
    private Integer errorCode;
    private String exceptionMessage;
}


