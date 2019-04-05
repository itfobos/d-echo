package common.token;

import common.model.UserProfile;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TokenParserConfig.class)
@TestPropertySource("classpath:token-test.properties")
public class TokenParserTest {
    @Autowired
    private TokenParser tokenParser;

    @Test
    public void emptyTokenCorrectProcessingTest() {
        Optional<UserProfile> profileOptional = tokenParser.getProfileIfNotExpired("");
        Assert.assertTrue(profileOptional.isEmpty());

        profileOptional = tokenParser.getProfileIfNotExpired("     ");
        Assert.assertTrue(profileOptional.isEmpty());

        profileOptional = tokenParser.getProfileIfNotExpired(null);
        Assert.assertTrue(profileOptional.isEmpty());
    }

    @Test
    public void incorrectTokenProcessingTest() {
        Optional<UserProfile> profileOptional = tokenParser.getProfileIfNotExpired("some random str");
        Assert.assertTrue(profileOptional.isEmpty());
    }
}
