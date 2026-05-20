package fishinggamejava.fishinggamejava;

import java.util.Random;
import java.util.Scanner;

public class Investment {
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    public void openInvestmentMenu(Player player) {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n\u001B[36m=======================================\u001B[0m");
            System.out.println(" 💼 INVESTMENT CENTER 💼");
            System.out.println("\u001B[36m=======================================\u001B[0m");
            System.out.println(" Available Capital: \u001B[32m" + player.getMoney() + " €\u001B[0m");
            System.out.println(" 1. Venture Capital Deal (Blackjack)");
            System.out.println(" 2. Risk Multiplier Desk (Upgrader)");
            System.out.println(" 3. Exit Investment Desk");
            System.out.print(" Select an option: ");

            int choice = scanner.hasNextInt() ? scanner.nextInt() : 3;
            scanner.nextLine();

            switch (choice) {
                case 1 -> runVentureCapitalDeal(player);
                case 2 -> runRiskMultiplierDesk(player);
                case 3 -> inMenu = false;
                default -> System.out.println(" Invalid financial operation.");
            }
        }
    }

    private void runVentureCapitalDeal(Player player) {
        System.out.print("\nEnter investment capital amount: ");
        int investment = scanner.hasNextInt() ? scanner.nextInt() : 0;
        scanner.nextLine();

        if (investment <= 0 || investment > player.getMoney()) {
            System.out.println("\u001B[31m[Error] Insufficient funds!\u001B[0m");
            return;
        }

        int playerFundValue = random.nextInt(10) + 2 + random.nextInt(10) + 2;
        int competitorFundValue = random.nextInt(10) + 2 + random.nextInt(10) + 2;

        System.out.println("\n\u001B[34m--- STARTING VALUATION ---\u001B[0m");
        System.out.println(" Your asset value: \u001B[33m" + playerFundValue + "\u001B[0m");
        System.out.println(" Competitor visibility: \u001B[35m" + (competitorFundValue - random.nextInt(4)) + "\u001B[0m");

        boolean investing = true;
        while (investing && playerFundValue <= 21) {
            System.out.print("\nAction -> [(I)nvest / (S)tay]: ");
            String move = scanner.nextLine().trim().toUpperCase();

            if (move.equals("I")) {
                int assetGrowth = random.nextInt(10) + 2;
                playerFundValue += assetGrowth;
                System.out.println("Growth: +" + assetGrowth + " | New Total: \u001B[33m" + playerFundValue + "\u001B[0m");
            } else {
                investing = false;
            }
        }

        if (playerFundValue > 21) {
            System.out.println("\n\u001B[31m!!! MARKET CRASH !!!\u001B[0m Value: " + playerFundValue + ". You lost " + investment + " €.");
            player.subMoney(investment);
            return;
        }

        while (competitorFundValue < 17) competitorFundValue += random.nextInt(10) + 2;

        System.out.println("\n\u001B[34m--- FINAL AUDIT ---\u001B[0m");
        System.out.println(" Your Fund: " + playerFundValue + " | Competitor: " + competitorFundValue);

        if (competitorFundValue > 21 || playerFundValue > competitorFundValue) {
            System.out.println("\u001B[32mSUCCESS! ROIs achieved: +" + investment + " €.\u001B[0m");
            player.addMoney(investment);
        } else if (playerFundValue < competitorFundValue) {
            System.out.println("\u001B[31mFAILURE! Competitor dominated: -" + investment + " €.\u001B[0m");
            player.subMoney(investment);
        } else {
            System.out.println("EQUILIBRIUM: Investment returned.");
        }
    }

    private void runRiskMultiplierDesk(Player player) {
        System.out.print("\nEnter starting investment value: ");
        int baseBet = scanner.hasNextInt() ? scanner.nextInt() : 0;
        scanner.nextLine();

        if (baseBet <= 0 || baseBet > player.getMoney()) {
            System.out.println("\u001B[31m[Error] Invalid funds!\u001B[0m");
            return;
        }

        player.subMoney(baseBet);
        int currentPot = baseBet;
        double[] multipliers = {1.5, 3.0, 5.0, 10.0, 20.0};
        String[] descriptions = {
                "Color Match (Red/Black)",
                "Suit Match (Hearts/Diamonds/Clubs/Spades)",
                "Index Prediction (Higher/Lower than 7)",
                "Card Type (Face Card / Number Card)",
                "Perfect Ace Hunt (Predict if next card is an Ace)"
        };

        System.out.println("\n\u001B[35m=== UPGRADER ACTIVE - STARTING BET: " + baseBet + " € ===\u001B[0m");

        for (int i = 0; i < multipliers.length; i++) {
            double mult = multipliers[i];
            int nextPotential = (int)(baseBet * mult);

            System.out.println("\n---------------------------------------");
            System.out.println(" 📈 TIER " + (i + 1) + ": " + descriptions[i] + " [" + mult + "x]");
            System.out.println("---------------------------------------");
            System.out.println(" Current Pot: \u001B[32m" + currentPot + " €\u001B[0m");
            System.out.println(" Potential Win at this tier: \u001B[32m" + nextPotential + " €\u001B[0m");
            System.out.print(" Action -> [(P)lay / (C)ash out " + currentPot + " €]: ");

            String choice = scanner.nextLine().trim().toUpperCase();
            if (choice.equals("C")) {
                System.out.println("\n\u001B[32m[CASH OUT] Positions closed. Received: +" + currentPot + " €\u001B[0m");
                player.addMoney(currentPot);
                return;
            }

            // --- KÁRTYA GENERÁLÁSA ---
            int rank = random.nextInt(13) + 1; // 1 - 13
            int suitIdx = random.nextInt(4);   // 0 = Hearts, 1 = Diamonds, 2 = Clubs, 3 = Spades

            String suitName = "";
            String suitSymbol = "";
            boolean isRed = false;

            switch (suitIdx) {
                case 0 -> { suitName = "Hearts"; suitSymbol = "♥"; isRed = true; }
                case 1 -> { suitName = "Diamonds"; suitSymbol = "◆"; isRed = true; }
                case 2 -> { suitName = "Clubs"; suitSymbol = "♣"; isRed = false; }
                case 3 -> { suitName = "Spades"; suitSymbol = "♠"; isRed = false; }
            }

            String rankName = String.valueOf(rank);
            if (rank == 1) rankName = "Ace";
            else if (rank == 11) rankName = "Jack";
            else if (rank == 12) rankName = "Queen";
            else if (rank == 13) rankName = "King";

            String cardColorCode = isRed ? "\u001B[31m" : "\u001B[34m"; // Szép kék vagy piros kiírás
            String resetCode = "\u001B[0m";

            // --- JÁTÉK INTERAKTÍV MECHANIKÁK ---
            boolean win = false;

            switch (i) {
                case 0 -> { // TIER 1: Color
                    System.out.print(" Predict card color [(R)ed / (B)lack]: ");
                    String prediction = scanner.nextLine().trim().toUpperCase();
                    win = (prediction.startsWith("R") && isRed) || (prediction.startsWith("B") && !isRed);
                }
                case 1 -> { // TIER 2: Suit Selection
                    System.out.print(" Predict exact suit [(H)earts ♥ / (D)iamonds ◆ / (C)lubs ♣ / (S)pades ♠]: ");
                    String prediction = scanner.nextLine().trim().toUpperCase();
                    if (prediction.startsWith("H") && suitIdx == 0) win = true;
                    if (prediction.startsWith("D") && suitIdx == 1) win = true;
                    if (prediction.startsWith("C") && suitIdx == 2) win = true;
                    if (prediction.startsWith("S") && suitIdx == 3) win = true;
                }
                case 2 -> { // TIER 3: Higher/Lower than 7
                    System.out.print(" Will the card rank be (H)igher or (L)ower than 7? (7 counts as loss): ");
                    String prediction = scanner.nextLine().trim().toUpperCase();
                    win = (prediction.equals("H") && rank > 7) || (prediction.equals("L") && rank < 7);
                }
                case 3 -> { // TIER 4: Face vs Number
                    System.out.print(" Will it be a (F)ace Card (J,Q,K) or a (N)umber Card (A-10)? ");
                    String prediction = scanner.nextLine().trim().toUpperCase();
                    boolean isFace = (rank >= 11);
                    win = (prediction.equals("F") && isFace) || (prediction.equals("N") && !isFace);
                }
                case 4 -> { // TIER 5: Ace Hunt
                    System.out.print(" Last step! Will the card be an (A)ce or (N)ot an Ace? ");
                    String prediction = scanner.nextLine().trim().toUpperCase();
                    win = (prediction.equals("A") && rank == 1) || (prediction.equals("N") && rank != 1);
                }
            }

            // --- MUTATJUK A KÁRTYÁT ---
            System.out.println("\n Dealer draws: " + cardColorCode + "[" + rankName + " of " + suitName + " " + suitSymbol + "]" + resetCode);

            if (win) {
                currentPot = nextPotential;
                System.out.println("\u001B[32m [WIN] Tier cleared! Pot upgraded to: " + currentPot + " €\u001B[0m");
            } else {
                System.out.println("\n\u001B[31m=======================================");
                System.out.println(" 🛑 [BUST] LIQUIDATION EVENT!");
                System.out.println(" Your prediction failed at Tier " + (i + 1) + ".");
                System.out.println(" Total Capital Lost: -" + baseBet + " €");
                System.out.println("=======================================\u001B[0m");
                return;
            }
        }

        // Ha mind az 5 kört túlélte
        System.out.println("\n\u001B[36m=======================================");
        System.out.println(" 🎉🎉🎉 MAX TIER ACHIEVED !!! 🎉🎉🎉");
        System.out.println(" Ultimate ROI unlocked! Total Payout: " + currentPot + " €");
        System.out.println("=======================================\u001B[0m");
        player.addMoney(currentPot);
    }
}
