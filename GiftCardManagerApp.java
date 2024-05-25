import java.util.*;

class GiftCard {
    private int cardNumber;
    private int pin;
    private double balance;
    private boolean blocked;
    private List<String> transactionHistory;

    public GiftCard(int cardNumber, int pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = 0.0;
        this.blocked = false;
        this.transactionHistory = new ArrayList<>();
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public boolean verifyPIN(int pin) {
        return this.pin == pin;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void blockCard() {
        blocked = true;
    }

    public void topUp(double amount) {
        if (!blocked) {
            balance += amount;
            transactionHistory.add("Top up: +" + amount);
        }
    }

    public void purchase(double amount) {
        if (!blocked && balance >= amount) {
            balance -= amount;
            transactionHistory.add("Purchase: -" + amount);
        }
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }
}

class GiftCardManager {
    private Map<Integer, GiftCard> giftCards;
    private Set<Integer> usedCardNumbers;

    public GiftCardManager() {
        this.giftCards = new HashMap<>();
        this.usedCardNumbers = new HashSet<>();
    }

    public void createGiftCard() {
        int cardNumber;
        do {
            cardNumber = new Random().nextInt(90000) + 10000;
        } while (usedCardNumbers.contains(cardNumber));
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a 4-digit PIN for the gift card: ");
        int pin = scanner.nextInt();

        giftCards.put(cardNumber, new GiftCard(cardNumber, pin));
        usedCardNumbers.add(cardNumber);
        System.out.println("***** Gift card created successfully with card number: " + cardNumber + " *****" );
    }

    public void topUpGiftCard(int cardNumber, int pin, double amount) {
        GiftCard card = giftCards.get(cardNumber);
        if (card != null && card.verifyPIN(pin)) {
            card.topUp(amount);
            System.out.println("****** Top-up successful. Current balance: " + card.getBalance() + " ******");
        } else {
            System.out.println("Invalid card number or PIN.");
        }
    }

    public void makePurchase(int cardNumber, int pin, double amount) {
        GiftCard card = giftCards.get(cardNumber);
        if (card != null && card.verifyPIN(pin)) {
            if (card.getBalance() >= amount) {
                card.purchase(amount);
                System.out.println("***** Purchase successful. Remaining balance: " + card.getBalance() + " *****");
            } else {
                System.out.println("Insufficient balance.");
            }
        } else {
            System.out.println("Invalid card number or PIN.");
        }
    }

    public List<String> viewTransactionHistory(int cardNumber, int pin) {
        GiftCard card = giftCards.get(cardNumber);
        if (card != null && card.verifyPIN(pin)) {
            return card.getTransactionHistory();
        }
        return null;
    }

    public void blockGiftCard(int cardNumber, int pin) {
        GiftCard card = giftCards.get(cardNumber);
        if (card != null && card.verifyPIN(pin)) {
            card.blockCard();
            System.out.println("***** Gift card blocked successfully *****");
        } else {
            System.out.println("Invalid card number or PIN.");
        }
    }

}

public class GiftCardManagerApp {
    public static void main(String[] args) {
        GiftCardManager manager = new GiftCardManager();
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            System.out.println("\nSelect an operation:");
            System.out.println("1. Create Gift Card");
            System.out.println("2. Top Up Gift Card");
            System.out.println("3. Make Purchase");
            System.out.println("4. View Transaction History");
            System.out.println("5. Block Gift Card");
            System.out.println("6. Exit");
            System.out.print("\nEnter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    manager.createGiftCard();
                    break;
                case 2:
                    System.out.print("Enter Gift Card Number: ");
                    int topUpCardNumber = scanner.nextInt();
                    System.out.print("Enter PIN: ");
                    int topUpPin = scanner.nextInt();
                    System.out.print("Enter Amount to Top Up: ");
                    double amountToTopUp = scanner.nextDouble();
                    manager.topUpGiftCard(topUpCardNumber, topUpPin, amountToTopUp);
                    break;
                case 3:
                    System.out.print("Enter Gift Card Number: ");
                    int purchaseCardNumber = scanner.nextInt();
                    System.out.print("Enter PIN: ");
                    int purchasePin = scanner.nextInt();
                    System.out.print("Enter Purchase Amount: ");
                    double purchaseAmount = scanner.nextDouble();
                    manager.makePurchase(purchaseCardNumber, purchasePin, purchaseAmount);
                    break;
                case 4:
                    System.out.print("Enter Gift Card Number: ");
                    int historyCardNumber = scanner.nextInt();
                    System.out.print("Enter PIN: ");
                    int historyPin = scanner.nextInt();
                    List<String> transactions = manager.viewTransactionHistory(historyCardNumber, historyPin);
                    if (transactions != null) {
                        System.out.println("\nTransaction History:");
                        for (String transaction : transactions) {
                            System.out.println(transaction);
                        }
                    } else {
                        System.out.println("Invalid Card Number or PIN.");
                    }
                    break;
                case 5:
                    System.out.print("Enter Gift Card Number: ");
                    int blockCardNumber = scanner.nextInt();
                    System.out.print("Enter PIN: ");
                    int blockPin = scanner.nextInt();
                    manager.blockGiftCard(blockCardNumber, blockPin);
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}
