import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Deposit_test {

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
    //Covers DU-pairs for valid deposit
    @Test
    void testDepositValidAmount() {
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

                // deposit 50
                "1",
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

        assertTrue(output.contains("Deposit Successful"));
        assertTrue(output.contains("Balance: 150"));

    }
    //Covers DU-pair for balance after deposit
    @Test
    void testDepositWithdraw() {

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

                // deposit 50
                "1",
                "50",

                // withdraw 30
                "2",
                "30",

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

        assertTrue(output.contains("Deposit Successful"));
        assertTrue(output.contains("Withdraw Successful"));
        assertTrue(output.contains("Balance: 120"));  // 100 + 50 - 30
    }
    // Covers DU-pairs involving negative stating balance
    @Test
    void testStartingNegativeAmount() {

        String input = String.join("\n",
                // create an account
                "1",
                "Tom",
                "pw",
                "t@mail.com",
                "999",
                "-100",
                "0",

                // login
                "2",
                "Tom",
                "pw",

                // logout
                "5",

                // exit system
                "3"
        ) + "\n";

        provideInput(input);
        new Cli();
        String output = getOutput();

        assertTrue(output.contains("Balance cannot be negative, please enter a valid balance"));
        assertTrue(output.contains("Account created successfully"));
    }
    @Test
    void testDepositZero() {
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

                // deposit 0
                "1",
                "0",
                "1",

                // withdraw 30
                "2",
                "30",

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
        assertTrue(output.contains("Deposit Successful"));
        assertTrue(output.contains("Withdraw Successful"));
        assertTrue(output.contains("Balance: 71"));
    }
    //Covers DU-pairs involving negative deposit
    @Test
    void testDepositNegative() {
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

                // deposit -10
                "1",
                "-10",
                "1",

                // withdraw 30
                "2",
                "30",

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
        assertTrue(output.contains("Balance: 71"));
    }

}

