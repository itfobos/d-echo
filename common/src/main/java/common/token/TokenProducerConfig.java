package common.token;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:token.properties")
public class TokenProducerConfig {

    @Bean
    public TokenProducer tokenProducer() {
        return new TokenProducer();
    }
}
