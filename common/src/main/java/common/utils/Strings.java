package common.utils;

import java.util.Objects;

public class Strings {
    private Strings() {
    }

    public static String requireNotBlank(String srcStr) {
        if (Objects.requireNonNull(srcStr).isBlank()) throw new IllegalArgumentException("The string is blank");

        return srcStr;
    }

    public static boolean isNullOrBlank(String srcStr) {
        return srcStr == null || srcStr.isBlank();
    }

    public static boolean notNullOrBlank(String srcStr) {
        return !isNullOrBlank(srcStr);
    }
}
