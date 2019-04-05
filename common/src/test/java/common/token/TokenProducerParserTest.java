package common.token;

import common.model.UserProfile;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TokenProducerConfig.class, TokenParserConfig.class})
@TestPropertySource("classpath:token-test.properties")
public class TokenProducerParserTest {

    @Autowired
    private TokenProducer tokenProducer;

    @Autowired
    private TokenParser tokenParser;

    private static final String firstRole = "role1";
    private static final String secondRole = "role2";

    private final UserProfile srcUserProfile = new UserProfile(1L, "testLogin", firstRole + "," + secondRole);

    @Test
    public void producedTokenNotBlankTest() {
        String token = tokenProducer.produce(srcUserProfile);

        Assert.assertThat(token, not(isEmptyOrNullString()));
    }

    @Test
    public void twoWaysTokenTransformationTest() {
        String token = tokenProducer.produce(srcUserProfile);

        Optional<UserProfile> profileOptional = tokenParser.getProfileIfNotExpired(token);

        profileOptional.ifPresentOrElse(profile -> {
            Assert.assertThat(profile.id, equalTo(srcUserProfile.id));
            Assert.assertThat(profile.login, equalTo(srcUserProfile.login));
            Assert.assertThat(profile.roles, Matchers.arrayContaining(firstRole, secondRole));
        }, Assert::fail);
    }
}
