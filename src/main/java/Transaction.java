import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record Transaction(String account, TransactionType type, BigDecimal amount, LocalDateTime timestamp) {
    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL
    }

    public Transaction {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be non-negative.");
        }
        if (account == null || account.isBlank()) {
            throw new IllegalArgumentException("Transaction id cannot be empty.");
        }
        if (type == null) {
            throw new IllegalArgumentException("Transaction type cannot be null.");
        }
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
    @Override
    public @NotNull String toString() {
        return String.format("%s %s %s %s", account, type, amount, timestamp);
    }
}
