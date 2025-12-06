import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Account_test {
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    void setUpStreams() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    private void provideInput(@NotNull String input) {
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return testOut.toString();
    }

    @Test
    public void duplicateAccount() {
        String input = String.join("\n",
                // create an account
                "1",
                "Tom",
                "pw",
                "t@mail.com",
                "999",
                "100",

                // create the same account
                "1",
                "Tom",
                "pw",
                "t@mail.com",
                "999",
                "100",

                // logout
                "5",

                // exit system
                "3"
        ) + "\n";
        provideInput(input);
        new Cli();
        String output = getOutput();

        assertTrue(output.contains("Account creation failed"));
    }
    @Test
    public void transactionOutput() {
        String input = String.join("\n",
                // create an account
                "1",
                "Tom",
                "pw",
                "t@mail.com",
                "999",
                "100",

                // login
                "2",
                "Tom",
                "pw",

                // withdrawal 30
                "2",
                "30",

                // deposit 50
                "1",
                "50",

                //show transaction history
                "4",

                // logout
                "5",

                // exit system
                "3"
        ) + "\n";
        provideInput(input);
        new Cli();
        String output = getOutput();

        assertTrue(output.contains("Tom DEPOSIT 50"));
        assertTrue(output.contains("Tom WITHDRAWAL 30"));
    }
    @Test
    public void incorrectPassword() {
        String input = String.join("\n",
                // create an account
                "1",
                "Tom",
                "pw",
                "t@mail.com",
                "999",
                "100",

                // login
                "2",
                "Tom",
                "p",

                // exit system
                "3"
        ) + "\n";
        provideInput(input);
        new Cli();
        String output = getOutput();

        assertTrue(output.contains("account does not exist or wrong password"));
    }
}
