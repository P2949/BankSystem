import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class Direct_test {
    @Test
    public void Withdrawal_test() {
        Account a = new Account("tom","111", "t@mail.c", "999", BigDecimal.valueOf(9), new PasswordHasher());
        assertTrue(a.withdraw(BigDecimal.valueOf(5)));
        assertEquals(a.getBalance(), BigDecimal.valueOf(4));
        assertFalse(a.withdraw(BigDecimal.valueOf(5)));
        assertFalse(a.withdraw(BigDecimal.ZERO));
        assertFalse(a.withdraw(BigDecimal.valueOf(-5)));
        assertTrue(a.withdraw(BigDecimal.valueOf(4)));
        assertEquals(BigDecimal.ZERO, a.getBalance());
    }
    @Test
    public void Deposit_test() {
        Account a = new Account("tom","111", "t@mail.c", "999", BigDecimal.ZERO, new PasswordHasher());
        assertTrue(a.deposit(BigDecimal.valueOf(5)));
        assertEquals(a.getBalance(), BigDecimal.valueOf(5));
        assertFalse(a.deposit(BigDecimal.valueOf(-5)));
        assertTrue(a.withdraw(BigDecimal.valueOf(1)));
        assertEquals(a.getBalance(), BigDecimal.valueOf(4));
        assertFalse(a.deposit(BigDecimal.ZERO));
    }
    @Test
    public void DuplicateAccount_test() {
        BankSystem b = new BankSystem();
        assertTrue(b.addAccount("tom","111", "t@mail.c", "999", BigDecimal.ZERO));
        assertFalse(b.addAccount("tom","111", "t@mail.c", "999", BigDecimal.ZERO));
    }
    @Test
    public void Transaction_test() {
        Account a = new Account("tom","111", "t@mail.c", "999", BigDecimal.ZERO, new PasswordHasher());
        assertTrue(a.deposit(BigDecimal.valueOf(5)));
        assertEquals(a.getBalance(), BigDecimal.valueOf(5));
        assertTrue(a.withdraw(BigDecimal.valueOf(2)));
        assertEquals(a.getBalance(), BigDecimal.valueOf(3));
        a.getTransactions();
        for (Transaction t : a.getTransactions()) {
            assertTrue(t.toString().contains("tom") &&
                    (t.toString().contains("DEPOSIT") || t.toString().contains("WITHDRAWAL")) &&
                    (t.toString().contains("5") || t.toString().contains("2")));
        }
    }
    @Test
    public void Password_test() {
        BankSystem b = new BankSystem();
        assertTrue(b.addAccount("tom","111", "t@mail.c", "999", BigDecimal.ZERO));
        assertFalse(b.authenticate("tom","112"));
    }
    @Test
    public void NoAccountFound_test() {
        BankSystem b = new BankSystem();
        assertTrue(b.addAccount("tom","111", "t@mail.c", "999", BigDecimal.ZERO));
        assertNull(b.getAccount("tim"));
    }
}
