import java.util.HashMap;
import java.util.Map;
public class Main {
    public static void main(String[] args) {



        interface Currency {
            double getExchangeRate();
        }


        class USDCurrency implements Currency {
            private static final double EXCHANGE_RATE = 1.0;

            @Override
            public double getExchangeRate() {
                return EXCHANGE_RATE;
            }
        }

        class EURCurrency implements Currency {
            private static final double EXCHANGE_RATE = 0.85;

            @Override
            public double getExchangeRate() {
                return EXCHANGE_RATE;
            }
        }


        interface User {
            void displayInfo();
        }


        class RegularUser implements User {
            private int activityLevel;

            public RegularUser(int activityLevel) {
                this.activityLevel = activityLevel;
            }

            @Override
            public void displayInfo() {
                System.out.println("Regular User, Activity Level: " + activityLevel);
            }
        }

        class PremiumUser implements User {
            private boolean premiumStatus;

            public PremiumUser(boolean premiumStatus) {
                this.premiumStatus = premiumStatus;
            }

            @Override
            public void displayInfo() {
                System.out.println("Premium User, Premium Status: " + premiumStatus);
            }
        }


        interface BankAccount {
            void deposit(double amount);
            void withdraw(double amount);
            void displayInfo();
            void convertBalance(Currency newCurrency);
        }


        abstract class BaseBankAccount implements BankAccount {
            protected User user;
            protected Currency currency;
            protected double balance;

            public BaseBankAccount(User user, Currency currency, double balance) {
                this.user = user;
                this.currency = currency;
                this.balance = balance;
            }

            @Override
            public void displayInfo() {
                user.displayInfo();
                System.out.println("Balance: " + balance + " " + currency.getClass().getSimpleName());
            }
        }


        class RegularBankAccount extends BaseBankAccount {

            public RegularBankAccount(User user, Currency currency, double balance) {
                super(user, currency, balance);
            }

            @Override
            public void deposit(double amount) {
                balance += amount;
            }

            @Override
            public void withdraw(double amount) {
                balance -= amount;
            }

            @Override
            public void convertBalance(Currency newCurrency) {
                double newBalance = balance * (currency.getExchangeRate() / newCurrency.getExchangeRate());
                System.out.println("Converted Balance: " + newBalance + " " + newCurrency.getClass().getSimpleName());
            }
        }


        class PremiumBankAccount extends BaseBankAccount {

            public PremiumBankAccount(User user, Currency currency, double balance) {
                super(user, currency, balance);
            }

            @Override
            public void deposit(double amount) {
                balance += amount;
            }

            @Override
            public void withdraw(double amount) {
                balance -= amount;
            }

            @Override
            public void convertBalance(Currency newCurrency) {
                double newBalance = balance * (currency.getExchangeRate() / newCurrency.getExchangeRate());
                System.out.println("Converted Balance: " + newBalance + " " + newCurrency.getClass().getSimpleName());
            }
        }


        class BankSingleton {
            private static BankSingleton instance;
            private Map<String, Currency> currencies;

            private BankSingleton() {
                currencies = new HashMap<>();
                currencies.put("USD", new USDCurrency());
                currencies.put("EUR", new EURCurrency());

            }

            public static BankSingleton getInstance() {
                if (instance == null) {
                    instance = new BankSingleton();
                }
                return instance;
            }

            public Currency getCurrency(String code) {
                return currencies.get(code);
            }
        }

        class Main2 {
            public static void main(String[] args) {
                BankSingleton bank = BankSingleton.getInstance();


                User regularUser = new RegularUser(5);
                User premiumUser = new PremiumUser(true);
                Currency usd = bank.getCurrency("USD");
                Currency eur = bank.getCurrency("EUR");


                BankAccount account1 = new RegularBankAccount(regularUser, usd, 1000.0);
                BankAccount account2 = new PremiumBankAccount(premiumUser, eur, 500.0);


                account1.displayInfo();
                account2.displayInfo();


                account1.deposit(500.0);
                account1.withdraw(200.0);
                account2.deposit(300.0);
                account2.withdraw(100.0);


                account1.convertBalance(eur);
                account2.convertBalance(usd);
            }
        }


    }
    }
