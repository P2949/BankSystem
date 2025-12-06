import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Withdrawal_test {
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
    // Test Case 1: Covers parameter 'w' definition (negative path)
    @Test
    public void testWithdrawNegativeAmount() {
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

                // negative withdrawal
                "2",
                "-50",
                "1",

                // view balance
                "3",

                // logout
                "5",

                // exit system
                "3"
        ) + "\n";
        provideInput(input);
        new Cli();
        String output = getOutput();

        assertTrue(output.contains("Amount cannot be negative or zero, please enter a valid amount"));
        assertTrue(output.contains("Balance: 99"));
    }

    // Test Case 2: Covers parameter 'w' definition (insufficient funds)
    @Test
    public void testWithdrawInsufficientFunds() {
        String input = String.join("\n",
                // create an account
                "1",
                "Tom",
                "pw",
                "t@mail.com",
                "999",
                "25",

                // login
                "2",
                "Tom",
                "pw",

                // negative withdrawal
                "2",
                "50",

                // view balance
                "3",

                // logout
                "5",

                // exit system
                "3"
        ) + "\n";
        provideInput(input);
        new Cli();
        String output = getOutput();

        assertTrue(output.contains("Withdraw Failed"));
        assertTrue(output.contains("Balance: 25"));
    }
    // Test Case 3: Covers balance definition at Line 5
    @Test
    public void testWithdrawValidAmount() {
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

                // withdraw 50
                "2",
                "50",

                // view balance
                "3",

                // logout
                "5",

                // exit system
                "3"
        ) + "\n";
        provideInput(input);
        new Cli();
        String output = getOutput();

        assertTrue(output.contains("Withdraw Successful"));
        assertTrue(output.contains("Balance: 50"));
    }
    @Test
    public void testWithdrawAllFunds() {
        String input = String.join("\n",
                // create an account
                "1",
                "Tom",
                "pw",
                "t@mail.com",
                "999",
                "50",

                // login
                "2",
                "Tom",
                "pw",

                // withdraw 50
                "2",
                "50",

                // view balance
                "3",

                // logout
                "5",

                // exit system
                "3"
        ) + "\n";
        provideInput(input);
        new Cli();
        String output = getOutput();

        assertTrue(output.contains("Withdraw Successful"));
        assertTrue(output.contains("Balance: 0"));
    }
    @Test
    public void testWithdrawZero() {
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

                // withdraw 0
                "2",
                "0",
                "1",

                // view balance
                "3",

                // logout
                "5",

                // exit system
                "3"
        ) + "\n";
        provideInput(input);
        new Cli();
        String output = getOutput();

        assertTrue(output.contains("Amount cannot be negative or zero, please enter a valid amount"));
        assertTrue(output.contains("Withdraw Successful"));
        assertTrue(output.contains("Balance: 99"));
    }
}
