package passwordchecker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class PasswordCheckerTest {

    PasswordChecker checker = new PasswordChecker();

    @Test
    void isInCommonPasswords() {
        assertTrue(checker.isInCommonPasswords("123456"));
        assertFalse(checker.isInCommonPasswords("3xcellentPa$$Word!"));
    }

    @Test
    void checkPassword() {
        assertEquals(5, checker.checkPassword("S0lidP4$$Word!"));
        assertTrue(checker.checkPassword("abc") < 3);
    }

    @Test
    void printStrength() {
        checker.printStrength(5);
        checker.printStrength(0);
    }

    @Test
    void estimateCrackTime() {
        String result = checker.estimateCrackTime("G00dP4$$word!");
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}