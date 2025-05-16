package passwordchecker;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class PasswordChecker {

    private static final String QUIT_COMMAND = "!quit";

    // Checking
    public void run() {
        Scanner scanner = new Scanner(System.in);
        PownedChecker powned = new PownedChecker();

        while (true) {
            System.out.print("Enter a password, type '!generate' or type '!quit' to stop: ");
            String password = scanner.nextLine().trim();
            if (password.equalsIgnoreCase("!generate")) {
                int length = 8 + new Random().nextInt(4);
                String generated = PasswordGenerator.generate(length);
                System.out.println("Generated Password (" + length + " chars): " + generated);
                continue;
            }
            if (password.equalsIgnoreCase(QUIT_COMMAND)) {
                System.out.println("Programm beendet.");
                break;
            }

            System.out.println("\n----------------------------------------\n");
            System.out.println("Your password:" + password);

            if (password.isEmpty()) {
                System.out.println("Enter valid Password!");
                continue;
            }

            if (isInCommonPasswords(password)) {
                System.out.println("This password is one of the most common used passwords. Please take another one for your own safety!");
            }

            int count = powned.getPwnedCount(password);
            if (count > 0) {
                System.out.println("This password already got " + count + " times leaked!");
            } else {
                System.out.println("Not found in  HaveIBeenPwnd Database.👍");
            }

            int score = checkPassword(password);
            printStrength(score);

            String crackTime = estimateCrackTime(password);
            System.out.println("Estimated crack time: " + crackTime);
        }
    }

    private static final String SYMBOLS = "!@#$%^&*()_+-=[]{};':\"\\|,.<>/?";

    // Checks password against list of common passwords
    public boolean isInCommonPasswords(String password) {
        InputStream inputStream = getClass().getResourceAsStream("/most-used-passwords.txt");
        if (inputStream == null) {
            System.out.println("Error: Password list not found.");
            return false;
        }
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            if (scanner.nextLine().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Simple scoring system for password strength
    public int checkPassword(String password) {
        int score = 0;
        if (password.length() >= 8) score++;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*[a-z].*")) score++;
        if (password.matches(".*\\d.*")) score++;
        for (char c : password.toCharArray()) {
            if (SYMBOLS.indexOf(c) != -1) {
                score++;
                break;
            }
        }

        return score;
    }

    // Print assessment based on score
    public void printStrength(int score) {
        System.out.print("Password difficulty: " + score + " / 5 → ");
        switch (score) {
            case 5 -> System.out.println("Very Strong");
            case 4 -> System.out.println("Strong");
            case 3 -> System.out.println("Medium");
            case 2 -> System.out.println("Weak");
            default -> System.out.println("Very Weak");
        }
    }

    // Crack time estimation using predefined table
    public String estimateCrackTime(String password) {
        Map<Integer, String[]> table = new HashMap<>();

        table.put(4, new String[]{"Instantly", "Instantly", "Instantly", "Instantly", "Instantly"});
        table.put(5, new String[]{"Instantly", "Instantly", "57 minutes", "2 hours", "4 hours"});
        table.put(6, new String[]{"Instantly", "46 minutes", "2 days", "6 days", "2 weeks"});
        table.put(7, new String[]{"Instantly", "20 hours", "4 months", "1 year", "2 years"});
        table.put(8, new String[]{"Instantly", "3 weeks", "15 years", "62 years", "164 years"});
        table.put(9, new String[]{"2 hours", "2 years", "791 years", "3k years", "11k years"});
        table.put(10, new String[]{"1 day", "40 years", "41k years", "238k years", "803k years"});
        table.put(11, new String[]{"1 weeks", "1k years", "2m years", "14m years", "56m years"});
        table.put(12, new String[]{"3 months", "27k years", "111m years", "917m years", "3bn years"});
        table.put(13, new String[]{"3 years", "705k years", "5bn years", "56bn years", "275bn years"});
        table.put(14, new String[]{"28 years", "18m years", "300bn years", "3tn years", "19tn years"});
        table.put(15, new String[]{"284 years", "477m years", "15tn years", "218tn years", "1qd years"});
        table.put(16, new String[]{"2k years", "12bn years", "812tn years", "13qd years", "94qd years"});
        table.put(17, new String[]{"28k years", "322bn years", "42qd years", "840qd years", "6qn years"});
        table.put(18, new String[]{"284k years", "8tn years", "2qn years", "52qn years", "463qn years"});

        int length = password.length();
        int score = checkPassword(password) - 1;

        if (score < 0) score = 0;
        if (score > 4) score = 4;
        if (length < 4) length = 4;
        if (length > 18) length = 18;

        String[] times = table.get(length);
        if (times != null) {
            return times[score];
        } else {
            return "Unknown";
        }
    }
}
