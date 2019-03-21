package echoservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class EchoController {
    private final String echoPrefix;

    public EchoController(@Value("${echo.prefix}") String prefix) {
        echoPrefix = new Random().nextInt(Integer.MAX_VALUE) + " " + prefix + ' ';
    }

    @GetMapping(path = "/echo/{source}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String echo(@PathVariable("source") String source) {
        return echoPrefix + source;
    }
}
