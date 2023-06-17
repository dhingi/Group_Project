import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// Card class
class Card {
    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String toString() {
        return rank + " of " + suit;
    }

    public String getRank() {
        return rank;
    }
}

// Deck class
class Deck {
    private List<Card> cards;

    public Deck() {
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        cards = new ArrayList<>();

        for (String rank : ranks) {
            for (String suit : suits) {
                Card card = new Card(rank, suit);
                cards.add(card);
            }
        }

        shuffle();
    }

    public void shuffle() {
        Random random = new Random();
        for (int i = cards.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }

    public Card dealCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(cards.size() - 1);
    }
}

// Hand class
class Hand {
    private List<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int getValue() {
        int value = 0;
        boolean hasAce = false;

        for (Card card : cards) {
            if (card.getRank().equals("Ace")) {
                hasAce = true;
                value += 11;
            } else if (card.getRank().equals("King") || card.getRank().equals("Queen") || card.getRank().equals("Jack")) {
                value += 10;
            } else {
                value += Integer.parseInt(card.getRank());
            }
        }

        if (hasAce && value > 21) {
            value -= 10;
        }

        return value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card.toString()).append(", ");
        }
        return sb.toString();
    }
}

// Game class
class BlackjackGame {
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;

    public BlackjackGame() {
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();
    }

    public void play() {
        System.out.println("Welcome to Blackjack!");
        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());

        System.out.println("Player's hand: " + playerHand.toString());
        System.out.println("Dealer's hand: " + dealerHand.toString());

        // Player's turn
        playerTurn();

        // Dealer's turn
        dealerTurn();

        // Determine the winner
        determineWinner();
    }

    private void playerTurn() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Player's turn:");
            System.out.println("1. Hit");
            System.out.println("2. Stand");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                playerHand.addCard(deck.dealCard());
                System.out.println("Player's hand: " + playerHand.toString());

                if (playerHand.getValue() > 21) {
                    System.out.println("Player busts! You lose.");
                    break;
                }
            } else if (choice == 2) {
                break;
            }
        }
    }

    private void dealerTurn() {
        System.out.println("Dealer's turn:");
        System.out.println("Dealer's hand: " + dealerHand.toString());

        while (dealerHand.getValue() < 17) {
            dealerHand.addCard(deck.dealCard());
            System.out.println("Dealer hits. Dealer's hand: " + dealerHand.toString());
        }

        if (dealerHand.getValue() > 21) {
            System.out.println("Dealer busts! You win.");
        }
    }

    private void determineWinner() {
        int playerValue = playerHand.getValue();
        int dealerValue = dealerHand.getValue();

        System.out.println("Player's hand value: " + playerValue);
        System.out.println("Dealer's hand value: " + dealerValue);

        if (playerValue > dealerValue) {
            System.out.println("You win!");
        } else if (playerValue < dealerValue) {
            System.out.println("You lose!");
        } else {
            System.out.println("It's a tie!");
        }
    }
}

// Main class
public class main {
    public static void main(String[] args) {
        BlackjackGame game = new BlackjackGame();
        game.play();
    }
}
