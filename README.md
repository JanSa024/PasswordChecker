# PasswordChecker + Generator

This PasswordChecker can check your Passwords on different criteria for safety, can tell you how long it would take to crack it and uses an hashed API query to check if your Password ever got leaked. It also can generate new Passwords which comply with the criteria it checks.
 
## Technologies
- Java 17+ (tested with Java 22)
- JavaFX SDK (for graphical user interface)
- JUnit 5 (unit testing)
- SHA-1 hashing for secure API usage
- HaveIBeenPwned API (via k-Anonymity)

## Features

- Analyze password strength based on criterions like: 
    - length
    - Uppercase characters
    - Lowercase characters
    - Digits
    - Symbols
- Password gets rated from 0 to 5 in terms of difficulty
- Estimated time to crack given password (Table is used by Hive Systems, 12 x RTX 5090, Password has: bcrypt(10))
- Checks 100.000 most common used passwords for password
- Checks per hashed API query for data breaches of that password 
- Built-in password generator that ensures every optimal criterion
- JavaFX GUI for better user-friendly interaction
- All logic components tested
- Program can be terminated by extra close button in bottom right

## Usage Examples

- Use it to check if your password is still safe nowadays or if it has ever been leaked
- In either case you can create your own new Password which improves your account safety

## How to Run

## Start the .EXE

Doubleclick the exe and the program should start

## Run with Maven 

```bash
mvn clean javafx:run
```

### Console Mode

Run `Main.java` and follow the on-screen instructions:  
Enter a password or type `quit` to exit the program.

## Project Structure 

PasswordChecker  
 ├── src/  
 │   ├── main/  
 │   │   ├── java/  
 │   │   │   ├── PasswordChecker.java  
 │   │   │   ├── PasswordGenerator.java  
 │   │   │   ├── PownedChecker.java  
 │   │   │   ├── Gui.java  
 │   │   │   └── Main.java  
 │   └── resources/  
 │       └── most-used-passwords.txt  
 ├── test/  
 │   └── java/  
 │       ├── PasswordCheckerTest.java  
 │       ├── PasswordGeneratorTest.java  
 │       └── PownedCheckerTest.java  
 ├── .gitignore  
 ├── pom.xml  
 └── README.md




## Testing 

Unit tests for core functionality are available in the `test/java` directory.  
They can be run using IntelliJ or any JUnit 5-compatible test runner.

## Security Note

This program never transmits passwords in plain text.  
Every API query request is performed via HaveIBeenPwned's k-anonymity API:
- Passwords are hashed locally by SHA-1
- Only first 5 Characters of Hash are sent to the API endpoint
- Every other comparison works entirely offline
All API requests are made over HTTPS (TLS 1.2) to ensure secure communication.

## Project Status

This project is fully functional and tested.  
Additional features and improvements are planned for future updates.

## Sources

- **Password Cracking Reference Table**  
  https://www.hivesystems.com/blog/are-your-passwords-in-the-green

- **100,000 Most Common Passwords**  
  https://gitlab.com/kalilinux/packages/seclists/-/blob/c762d09c287bfde4ba326d450b21796980e95781/Passwords/Common-Credentials/100k-most-used-passwords-NCSC.txt
