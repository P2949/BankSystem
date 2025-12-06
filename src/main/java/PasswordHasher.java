import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public final class PasswordHasher {
    // Choose sensible defaults
    private static final int ITERATIONS = 3;
    private static final int MEMORY_KB = 64 * 1024; // 64 MB -> expressed in KB
    private static final int PARALLELISM = 2;

    private final Argon2 argon2;

    public PasswordHasher() {
        this.argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    }
    public String hash(char[] password) {
        return argon2.hash(ITERATIONS, MEMORY_KB, PARALLELISM, password);
    }

    /**
     * Verify a raw password against a stored PHC-encoded hash.
     */
    public boolean verify(String encodedHash, char[] candidatePassword) {
        return argon2.verify(encodedHash, candidatePassword);
    }

    /**
     * Wipe sensitive arrays after use.
     */
    public void wipe(char[] arr) {
        if (arr != null) {
            java.util.Arrays.fill(arr, '\0');
        }
    }
}
