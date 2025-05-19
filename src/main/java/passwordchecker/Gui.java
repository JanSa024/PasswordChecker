package passwordchecker;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.PrintWriter;

public class Gui extends Application {

    private final PasswordChecker checker = new PasswordChecker();
    private PownedChecker powned;


    // Setting up PasswordChecker
    @Override
    public void start(Stage primaryStage) {

        // GUI Elements
        Label label = new Label("Enter password:");
        TextField passwordTC = new TextField();
        Button checkButton = new Button("Check password");
        Button createButton = new Button("Create random password");
        passwordTC.setOnAction(event -> checkButton.fire());
        TextArea resultArea = new TextArea("Enter a Password to start");
        resultArea.setEditable(false);
        Button closeButton = new Button("Close Application");

        // Checking
        checkButton.setOnAction(event -> {
            String password = passwordTC.getText().trim();

            StringBuilder result = new StringBuilder();

            if (password.isEmpty()) {
                resultArea.setText("Please enter a valid Password!");
                return;
            }

            result.append("Your password: ").append(password).append("\n");

            try {
                if (checker.isInCommonPasswords(password)) {
                    result.append("Password is one of the most common used 100.000 passwords.\n");
                }
                if (powned == null) {
                    powned = new PownedChecker();
                }
                int count = powned.getPwnedCount(password);
                if (count > 0) {
                    result.append("⚠Password got ").append(count).append(" times leaked\n");
                } else {
                    result.append("No leaks found.\n");
                }

                int score = checker.checkPassword(password);
                result.append("Password difficulty: ").append(score).append(" / 5\n");

                String crackTime = checker.estimateCrackTime(password);
                result.append("Estimated crack time: ").append(crackTime);

            } catch (Exception e) {
                result.append("An error occurred while checking the password: ").append(e.getMessage());
            }


            resultArea.setText(result.toString());
        });

        // Creating own password "secure" by definition of PasswordGenerator
        createButton.setOnAction(event -> {
            String pw = PasswordGenerator.generate(12);
            passwordTC.setText(pw);
            resultArea.setText("Password successfully created!");
        });

        // Stop-Button to close application
        closeButton.setOnAction(event -> Platform.exit());

        // Layout
        HBox multButtons = new HBox(10, checkButton, createButton);
        VBox content = new VBox(10, label, passwordTC, multButtons, resultArea);
        content.setPadding(new Insets(20));

        HBox bottomRow = new HBox(closeButton);
        bottomRow.setPadding(new Insets(10));
        bottomRow.setAlignment(Pos.BOTTOM_RIGHT);

        BorderPane root = new BorderPane();
        root.setCenter(content);
        root.setBottom(bottomRow);

        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Password Checker");
        primaryStage.show();
    }
}
