package util;

public final class InputValidator {
    private InputValidator() {}

    public static String requireNonEmpty(String s, String field) {
        if (s == null || s.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " cannot be empty");
        }
        return s.trim();
    }

    public static int requireRange(int value, int min, int max, String field) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(field + " must be between " + min + " and " + max);
        }
        return value;
    }
}
