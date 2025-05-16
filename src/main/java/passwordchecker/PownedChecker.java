package passwordchecker;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PownedChecker {

    static {
        try {
            SSLContext tls12 = SSLContext.getInstance("TLSv1.2");
            tls12.init(null, null, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(tls12.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(
                    HttpsURLConnection.getDefaultHostnameVerifier()
            );
        } catch (Exception e) {
            System.err.println("Failed to initialize TLSv1.2: " + e.getMessage());
        }
    }

    // Returns the number of times the given password was found in known data breaches
    // Uses k-Anonymity by sending only the first 5 characters of the SHA-1 hash to the API
    public int getPwnedCount(String password) {
        if (password == null || password.isEmpty()) {
            return 0;
        }
        try {
            String sha1 = sha1(password);
            String prefix = sha1.substring(0, 5);
            String suffix = sha1.substring(5);

            URL url = new URL("https://api.pwnedpasswords.com/range/" + prefix);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setRequestProperty("User-Agent", "PasswordCheckerJavaApp");

            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    String[] parts = inputLine.split(":");
                    if (parts[0].equalsIgnoreCase(suffix)) {
                        return Integer.parseInt(parts[1].trim());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error during API request: " + e.getMessage());
        }

        return 0;
    }

    // Converts a String to an SHA-1 Hash
    private String sha1(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] result = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException("SHA-1 hashing failed", e);
        }
    }
}
