package passwordchecker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorTest {

    @Test
    void testGeneratePassword_Length() {
        String password = PasswordGenerator.generate(12);
        assertEquals(12, password.length());
    }

    @Test
    void testGeneratePassword_NotNullOrEmpty() {
        String password = PasswordGenerator.generate(10);
        assertNotNull(password);
        assertFalse(password.isEmpty());
    }

    @Test
    void testGeneratePassword_ContainsExpectedCharacters() {
        String password = PasswordGenerator.generate(16);

        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSymbol = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");
        assertTrue(hasUpper || hasLower || hasDigit || hasSymbol, "Password should contain at least one type of character");
    }

    @Test
    void testGeneratePassword_TooShortLengthThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> PasswordGenerator.generate(1));
    }
}
