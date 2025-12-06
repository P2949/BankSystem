import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class BankSystem {
    private final List<Account> accounts = new LinkedList<>();

    public BankSystem() {}

    public boolean addAccount(String name, String password, String email, String phone, BigDecimal balance) {
        for(Account a : accounts) {
            if(a.getName().equals(name) || a.getEmail().equals(email) || a.getPhone().equals(phone) ) {
                System.out.println("Account already exists");
                return false;
            }
        }
        accounts.add(new Account(name, password, email, phone, balance, new PasswordHasher()));
        return true;
    }
    public boolean authenticate(String name, String password) {
        for(Account a : accounts) {
            if(a.getName().equals(name) && a.verifyPassword(password.toCharArray())) {
                System.out.println("Login Successful");
                return true;
            }
        }
        System.out.println("Invalid Credentials");
        return false;
    }
    public Account getAccount(String name) {
        for(Account a : accounts) {
            if(a.getName().equals(name)) {
                return a;
            }
        }
        System.out.println("Account does not exist");
        return null;
    }
}
