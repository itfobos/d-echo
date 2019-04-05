package common.model;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class UserProfileRolesTest {
    private static final String ADMIN_ROLE = "ADMIN_ROLE";
    private static final String USER_ROLE = "USER_ROLE";

    @Test
    public void emptyRolesTest() {
        UserProfile profile = new UserProfile(null, null, "");

        Assert.assertNotNull(profile.roles);
        Assert.assertArrayEquals(new String[0], profile.roles);
    }

    @Test
    public void oneRoleTest() {
        UserProfile profile = new UserProfile(null, null, ADMIN_ROLE);

        Assert.assertArrayEquals(new String[]{ADMIN_ROLE}, profile.roles);
    }

    @Test
    public void bothRolesTest() {
        UserProfile profile = new UserProfile(null, null, ADMIN_ROLE + "," + USER_ROLE);

        Assert.assertArrayEquals(new String[]{ADMIN_ROLE, USER_ROLE}, profile.roles);
    }


    @Test
    public void bothRolesWhitespacesTest() {
        UserProfile profile = new UserProfile(null, null, ADMIN_ROLE + "      ,    " + USER_ROLE);

        Assert.assertArrayEquals(new String[]{ADMIN_ROLE, USER_ROLE}, profile.roles);
    }

    @Test
    public void oneRoleWhitespacesTest() {
        UserProfile profile = new UserProfile(null, null, "    " + ADMIN_ROLE);

        Assert.assertArrayEquals(new String[]{ADMIN_ROLE}, profile.roles);
    }

    @Test
    public void getRolesAsStringTest() {
        final String srcRolesAsString = ADMIN_ROLE + "," + USER_ROLE;

        UserProfile profile = new UserProfile(null, null, srcRolesAsString);

        Assert.assertThat(profile.getRolesAsString(), Matchers.equalTo(srcRolesAsString));
    }
}
