package passwordchecker;
public class Main {

    public static void main(String[] args) {
        try {
            PasswordChecker checker = new PasswordChecker();
            checker.run();
        } catch (Exception e) {
            System.err.println("Error at starting the program: " + e.getMessage());
        }
    }
}

