package fishinggamejava.fishinggamejava;

import java.util.Random;

public class QuestManager {
    private final Random random = new Random();
    private final Quest[] activeQuests = new Quest[3];

    public static class Quest {
        public String description = "No active quest.";
        public int type = 0;        // 1 = Total Fish, 2 = Money, 3 = Rarity-Specific Task
        public int target = 0;
        public int progress = 0;
        public int rewardMoney = 0;
        public int rewardXp = 0;
        public boolean completed = false;
        public int questRarityRoll = 0; // 1 = Common, 2 = Rare, 3 = Epic, 4 = Mythic, 5 = Legendary

        public void loadQuestProperties(int type, int target, int progress, boolean completed) {
            this.type = type;
            this.target = target;
            this.progress = progress;
            this.completed = completed;

            switch (type) {
                case 1 -> this.description = "Catch " + target + " fish of any kind.";
                case 2 -> this.description = "Earn " + target + " € from fish valuation.";
                case 3 -> this.description = "Catch " + target + " specific rarity fish.";
                default -> this.description = "No active quest.";
            }
            this.rewardMoney = target * 50;
            this.rewardXp = target * 20;
        }
    }

    public QuestManager() {
        for (int i = 0; i < 3; i++) {
            activeQuests[i] = new Quest();
        }
    }

    /**
     * Generates a scaled quest based on area multipliers and dynamic balancing formulas.
     */
    public void generateScaledQuest(int slotIndex, int playerLevel, int currentZone) {
        if (slotIndex < 0 || slotIndex >= 3) return;

        Quest q = activeQuests[slotIndex];
        q.progress = 0;
        q.completed = false;

        int zone = currentZone;
        int areaBaseReward = 300;
        if (zone == 2) areaBaseReward = 450;
        else if (zone == 3) areaBaseReward = 675;
        else if (zone == 4) areaBaseReward = 1000;
        else if (zone >= 5) areaBaseReward = 1500;

        q.type = random.nextInt(3) + 1;
        q.questRarityRoll = 0;

        // ANSI Escape codes for rarity text formatting
        String gray = "\u001B[90m";
        String green = "\u001B[32m";
        String purple = "\u001B[35m";
        String red = "\u001B[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";

        switch (q.type) {
            case 1 -> {
                q.target = random.nextInt(26) + 10;
                double fishBonusPercent = q.target * 0.04;
                q.rewardMoney = (int) (areaBaseReward * (1.0 + fishBonusPercent));
                q.rewardXp = (int) (areaBaseReward + (areaBaseReward * 1.5));
                q.description = "Catch " + q.target + " fish from Area " + zone;
            }

            case 2 -> {
                double randomMultiplier = 1.15 + (random.nextDouble() * 0.85);
                q.target = (int) (areaBaseReward * randomMultiplier);
                q.rewardMoney = (int) (q.target / 1.25);
                q.rewardXp = (int) (q.target / 1.5);
                q.description = "Earn " + q.target + " € from fish valuation";
            }

            case 3 -> {
                q.questRarityRoll = random.nextInt(5) + 1;

                if (q.questRarityRoll == 1) {
                    int maxCommon = Math.min(30, 6 * zone);
                    q.target = random.nextInt(maxCommon - 5 + 1) + 5;
                    q.rewardMoney = (int) (areaBaseReward / 1.25);
                    q.rewardXp = (int) (areaBaseReward / 1.5);
                    q.description = "Catch " + q.target + " " + gray + "Common" + reset + " fish";

                } else if (q.questRarityRoll == 2) {
                    int maxRare = Math.min(15, 3 * zone);
                    q.target = random.nextInt(maxRare - 3 + 1) + 3;
                    double bonusPercent = q.target * 0.02;
                    int augmentedBase = (int) (areaBaseReward * (1.0 + bonusPercent));
                    q.rewardMoney = (int) (augmentedBase / 1.25);
                    q.rewardXp = (int) (augmentedBase / 1.5);
                    q.description = "Catch " + q.target + " " + green + "Rare" + reset + " fish";

                } else if (q.questRarityRoll == 3) {
                    int maxEpic = Math.min(10, 2 * zone);
                    q.target = random.nextInt(maxEpic - 2 + 1) + 2;
                    double bonusPercent = q.target * 0.04;
                    int augmentedBase = (int) (areaBaseReward * (1.0 + bonusPercent));
                    q.rewardMoney = (int) (augmentedBase / 1.25);
                    q.rewardXp = (int) (augmentedBase / 1.5);
                    q.description = "Catch " + q.target + " " + purple + "Epic" + reset + " fish";

                } else if (q.questRarityRoll == 4) {
                    int maxMythic = Math.max(1, zone);
                    q.target = random.nextInt(maxMythic) + 1;
                    double bonusPercent = q.target * 0.08;
                    int augmentedBase = (int) (areaBaseReward * (1.0 + bonusPercent));
                    q.rewardMoney = (int) (augmentedBase / 1.25);
                    q.rewardXp = (int) (augmentedBase / 1.5);
                    q.description = "Catch " + q.target + " " + red + "Mythic" + reset + " fish";

                } else {
                    if (zone <= 2) {
                        q.target = random.nextInt(2) + 1;
                    } else {
                        q.target = random.nextInt(3) + 1;
                    }
                    double bonusPercent = q.target * 0.20;
                    int augmentedBase = (int) (areaBaseReward * (1.0 + bonusPercent));
                    q.rewardMoney = (int) (augmentedBase / 1.25);
                    q.rewardXp = (int) (augmentedBase / 1.5);
                    q.description = "Catch " + q.target + " " + yellow + "Legendary" + reset + " fish";
                }
            }
        }

        q.rewardMoney = Math.min(30000, q.rewardMoney);
        q.rewardXp = Math.min(8000, q.rewardXp);
    }

    public void generateAllQuests(int playerLevel, int currentZone) {
        for (int i = 0; i < 3; i++) {
            generateScaledQuest(i, playerLevel, currentZone);
        }
    }

    public void updateAllProgress(int type, int amount, String rarity, Player player) {
        for (int i = 0; i < 3; i++) {
            Quest q = activeQuests[i];
            if (q.completed || q.type == 0) continue;

            boolean progresses = false;

            if (q.type == 1 && type == 1) progresses = true;
            if (q.type == 2 && type == 2) progresses = true;

            if (q.type == 3 && type == 1) {
                if (q.questRarityRoll == 1 && rarity.equalsIgnoreCase("Common")) progresses = true;
                if (q.questRarityRoll == 2 && rarity.equalsIgnoreCase("Rare")) progresses = true;
                if (q.questRarityRoll == 3 && rarity.equalsIgnoreCase("Epic")) progresses = true;
                if (q.questRarityRoll == 4 && rarity.equalsIgnoreCase("Mythic")) progresses = true;
                if (q.questRarityRoll == 5 && rarity.equalsIgnoreCase("Legendary")) progresses = true;
            }

            if (progresses) {
                q.progress += amount;
                if (q.progress >= q.target) {
                    q.progress = q.target;
                    q.completed = true;
                    claimReward(q, player);
                }
            }
        }
    }

    private void claimReward(Quest q, Player player) {
        System.out.println("\n\u001B[33m======================================================\u001B[0m");
        System.out.println("🎉 QUEST COMPLETED: " + q.description);
        System.out.println("💰 Reward claimed: +" + q.rewardMoney + " € | ⭐ +" + q.rewardXp + " XP");
        player.addMoney(q.rewardMoney);
        player.addXp(q.rewardXp);
        System.out.println("\u001B[33m======================================================\u001B[0m");
    }

    public void showQuestStatus() {
        System.out.println("\n\u001B[35m=================== ACTIVE JOBS & QUESTS ===================\u001B[0m");
        for (int i = 0; i < 3; i++) {
            Quest q = activeQuests[i];
            System.out.print(" Slot [" + (i + 1) + "]: " + q.description);
            if (q.completed) {
                System.out.println(" -> \u001B[32m[COMPLETED]\u001B[0m");
            } else {
                System.out.println("\n          Progress: [" + q.progress + " / " + q.target + "] | Rewards: " + q.rewardMoney + "€ / " + q.rewardXp + "XP");
            }
            if (i < 2) System.out.println(" -----------------------------------------------------------");
        }
        System.out.println("\u001B[35m============================================================\u001B[0m");
    }

    public Quest[] getActiveQuests() { return activeQuests; }
}
