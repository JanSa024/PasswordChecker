package passwordchecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PownedCheckerTest {

    @Test
    void testGetPwnedCount_ValidInput() {
        PownedChecker checker = new PownedChecker();
        String testPassword = "Hello123!";

        try {
            int count = checker.getPwnedCount(testPassword);
            Assertions.assertTrue(count >= 0, "Count should be >= 0");
        } catch (Exception e) {
            Assertions.fail("getPwnedCount threw an exception: " + e.getMessage());
        }
    }

    @Test
    void testGetPwnedCount_EmptyInput() {
        PownedChecker checker = new PownedChecker();

        try {
            int count = checker.getPwnedCount("");
            Assertions.assertEquals(0, count);
        } catch (Exception e) {
            Assertions.fail("Exception for empty password: " + e.getMessage());
        }
    }
}
