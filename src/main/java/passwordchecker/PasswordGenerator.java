package passwordchecker;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.*;

public class PasswordGenerator {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()_+-=[]{};':\"\\|,.<>/?";
    private static final String ALL = UPPERCASE + LOWERCASE + DIGITS + SYMBOLS;

    private static final SecureRandom random = new SecureRandom();
    private static final Set<String> commonPasswords = loadCommonPasswords();

    // Generates a password with the given length (between 8 and 32 characters),
    // ensuring it is not one of the most common passwords
    public static String generate(int length) {
        if (length < 8 || length > 32) {
            throw new IllegalArgumentException("Password must be between 8 and 32 characters long.");
        }

        String password;
        do {
            password = generateRawPassword(length);
        } while (isCommonPassword(password));

        return password;
    }

    // Generates a password that satisfies all complexity rules
    private static String generateRawPassword(int length) {
        List<Character> chars = new ArrayList<>();
        chars.add(randomChar(UPPERCASE));
        chars.add(randomChar(LOWERCASE));
        chars.add(randomChar(DIGITS));
        chars.add(randomChar(SYMBOLS));

        for (int i = 4; i < length; i++) {
            chars.add(randomChar(ALL));
        }

        // Shuffle to avoid predictable structure
        Collections.shuffle(chars, random);

        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            sb.append(c);
        }
        return sb.toString();
    }

    // Returns a random String
    private static char randomChar(String chars) {
        return chars.charAt(random.nextInt(chars.length()));
    }

    // Checks if password is in List
    private static boolean isCommonPassword(String password) {
        return commonPasswords.contains(password);
    }

    // Loads a list of the most common passwords from the resource file
    private static Set<String> loadCommonPasswords() {
        Set<String> set = new HashSet<>();
        InputStream in = PasswordGenerator.class.getResourceAsStream("/most-used-passwords.txt");

        if (in == null) {
            System.err.println("Failed to load password list: file not found in resources.");
            return set;
        }

        try (Scanner scanner = new Scanner(in)) {
            while (scanner.hasNextLine()) {
                set.add(scanner.nextLine().trim());
            }
        }
        return set;
    }

}
