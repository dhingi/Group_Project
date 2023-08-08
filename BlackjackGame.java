import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

enum Suit {
    HEART, SPADE, CLUB, DIAMOND
}

class Card {
    private Suit suit;
    private int faceValue;

    public Card(Suit suit, int faceValue) {
        this.suit = suit;
        this.faceValue = faceValue;
    }

    public int getFaceValue() {
        return faceValue;
    }

    public String toString() {
        return faceValue + " of " + suit;
    }
}

class Deck {
    private List<Card> cards = new ArrayList<>();

    public Deck() {
        for (int value = 1; value <= 13; value++) {
            for (Suit suit : Suit.values()) {
                cards.add(new Card(suit, value));
            }
        }
    }

    public void shuffle() {
        Random random = new Random();
        for (int i = 0; i < cards.size(); i++) {
            int j = random.nextInt(cards.size());
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            System.out.println("Reshuffling the deck...");
            cards.clear();
            Deck freshDeck = new Deck();
            freshDeck.shuffle();
            cards.addAll(freshDeck.cards);
        }
        return cards.remove(cards.size() - 1);
    }
}

class Hand {
    private List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    public int getScore() {
        int score = 0;
        int numAces = 0;

        for (Card card : cards) {
            int value = card.getFaceValue();
            if (value > 10) {
                value = 10;
            } else if (value == 1) {
                numAces++;
                value = 11;
            }
            score += value;
        }

        while (score > 21 && numAces > 0) {
            score -= 10;
            numAces--;
        }

        return score;
    }

    public String toString() {
        StringBuilder handString = new StringBuilder();
        for (Card card : cards) {
            handString.append(card).append(", ");
        }
        return handString.toString();
    }
}

class Player {
    private String name;
    private double balance;
    private Hand hand = new Hand();

    public Player(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public void addToHand(Card card) {
        hand.addCard(card);
    }

    public int getScore() {
        return hand.getScore();
    }

    public void clearHand() {
        hand = new Hand();
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String toString() {
        return name + " (Balance: $" + balance + ")";
    }
}

public class BlackjackGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Blackjack!");

        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();

        System.out.print("Enter starting balance: $");
        double startingBalance = scanner.nextDouble();

        Player player = new Player(playerName, startingBalance);
        Player dealer = new Player("Dealer", Double.POSITIVE_INFINITY);

        Deck deck = new Deck();
        deck.shuffle();

        while (player.getBalance() > 0) {
            System.out.println(player);
            System.out.print("Enter bet amount: $");
            double bet = scanner.nextDouble();
            if (bet > player.getBalance()) {
                System.out.println("Insufficient balance.");
                continue;
            }

            player.clearHand();
            dealer.clearHand();

            player.addToHand(deck.drawCard());
            dealer.addToHand(deck.drawCard());
            player.addToHand(deck.drawCard());
            dealer.addToHand(deck.drawCard());

            System.out.println("Your hand: " + player.getScore());
            System.out.println("Dealer's face-up card: " + dealer.getScore());

            boolean playerTurn = true;
            while (playerTurn) {
                System.out.print("Do you want to hit or stand? ");
                String choice = scanner.next().toLowerCase();
                if ("hit".equals(choice)) {
                    player.addToHand(deck.drawCard());
                    int playerScore = player.getScore();
                    System.out.println("Your hand: " + playerScore);
                    if (playerScore > 21) {
                        System.out.println("Bust! You lose $" + bet);
                        player.setBalance(player.getBalance() - bet);
                        playerTurn = false;
                    }
                } else if ("stand".equals(choice)) {
                    playerTurn = false;
                } else {
                    System.out.println("Invalid choice. Please enter hit or stand.");
                }
            }

            int dealerScore = dealer.getScore();
            while (dealerScore < 17) {
                dealer.addToHand(deck.drawCard());
                dealerScore = dealer.getScore();
            }
            System.out.println("Dealer's hand: " + dealerScore);

            if (dealerScore > 21) {
                System.out.println("Dealer busts! You win $" + bet);
                player.setBalance(player.getBalance() + bet);
            } else if (player.getScore() > dealerScore) {
                System.out.println("You win $" + bet);
                player.setBalance(player.getBalance() + bet);
            } else if (player.getScore() < dealerScore) {
                System.out.println("You lose $" + bet);
                player.setBalance(player.getBalance() - bet);
            } else {
                System.out.println("It's a tie.");
            }

            System.out.println("Your balance: $" + player.getBalance());

            System.out.print("Do you want to play again? (yes/no) ");
            String playAgain = scanner.next().toLowerCase();
            if (!"yes".equals(playAgain)) {
                System.out.println("Thank you for playing!");
                break;
            }
        }

        System.out.println("Game over.");
        scanner.close();
    }
}
