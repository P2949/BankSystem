import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Account {
    private final String name;
    private final String password;
    private final String email;
    private final String phone;
    private NonNegative balance;
    private final List<Transaction> transactions = new LinkedList<>();

    public Account(String name, @NotNull String password, String email, String phone, BigDecimal balance, @NotNull PasswordHasher hasher) {
        NonNegative d = new NonNegative(balance);
        this.name = name;
        char[] raw = password.toCharArray();
        String hash = hasher.hash(raw);
        hasher.wipe(raw);
        this.password = hash;
        this.email = email;
        this.phone = phone;
        this.balance = d;
    }

    public String getName() {
        return name;
    }
    public boolean verifyPassword(char[] candidate) {
        return new PasswordHasher().verify(this.password, candidate);
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public BigDecimal getBalance() {
        return balance.value;
    }

    public boolean deposit(@NotNull BigDecimal d) {
        if (d.compareTo(BigDecimal.ZERO) <= 0 ) {
            System.out.println("Deposit cannot be negative or zero");
            return false;
        }
        BigDecimal t = balance.value;
        t = t.add(d);
        balance = new NonNegative(t);
        transactions.add(new Transaction(this.name, Transaction.TransactionType.DEPOSIT, d, LocalDateTime.now()));
        return true;
    }

    public boolean withdraw(@NotNull BigDecimal w) {
        if (w.compareTo(BigDecimal.ZERO) <= 0)  {
            System.out.println("Withdraw cannot be negative or zero");
            return false;
        } else if (balance.value.compareTo(w) < 0) {
            System.out.println("Insufficient funds");
            return false;
        }

        BigDecimal t = balance.value;
        t = t.subtract(w);
        balance = new NonNegative(t);
        transactions.add(new Transaction(this.name, Transaction.TransactionType.WITHDRAWAL, w, LocalDateTime.now()));
        return true;
    }
    public List<Transaction> getTransactions() {
        return new LinkedList<>(transactions);
    }

    private record NonNegative(BigDecimal value) {
        private static final BigDecimal DEFAULT_VALUE = BigDecimal.ZERO;

        private NonNegative {
            if (value.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Negative balance not allowed");
                value = DEFAULT_VALUE; // choose something appropriate
            }
        }
    }
}
