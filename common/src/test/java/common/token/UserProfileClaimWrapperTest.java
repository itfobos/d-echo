package common.token;

import common.model.UserProfile;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class UserProfileClaimWrapperTest {

    @Test
    public void twoWaysTransformationTest() {
        final String firstRole = "role1";
        final String secondRole = "role2";

        UserProfile srcProfile = new UserProfile(1L, "someLogin", firstRole + "," + secondRole);

        Map<String, Object> claims = new UserProfileClaimWrapper(srcProfile).getClaims();

        UserProfileClaimWrapper restoredProfile = UserProfileClaimWrapper.fromClaims(new DefaultClaims(claims));

        assertEquals(restoredProfile.id, srcProfile.id);
        assertEquals(restoredProfile.login, srcProfile.login);
        Assert.assertArrayEquals(restoredProfile.roles, srcProfile.roles);
    }
}
