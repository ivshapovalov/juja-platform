package juja.microservices.links.slackbot.repository.feign;

import juja.microservices.links.slackbot.model.Link;
import juja.microservices.links.slackbot.model.SaveLinkRequest;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Ivan Shapovalov
 */
@FeignClient(name = "gateway", url = "localhost:8080")
public interface LinksClient {

    @RequestMapping(method = RequestMethod.POST, value = "/v1/links",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Link saveLink(SaveLinkRequest request);

}
