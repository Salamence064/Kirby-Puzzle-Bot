package bot.command.game;

import bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.ArrayList;
import java.util.HashMap;

public class LevelManager {
    // message embeds using the images for each level
    // stored as the builder so the image on level5 can be changed as needed
    // this was created to be upscaled
    private static final EmbedBuilder level1 = new EmbedBuilder()
            .setTitle("Level 1")
            .setImage(Config.get("level1"))
            .setFooter("Enter the abilities Kirby needs to win!");

    private static final EmbedBuilder level2 = new EmbedBuilder()
            .setTitle("Level 2")
            .setImage(Config.get("level2"))
            .setFooter("Enter the abilities Kirby needs to win!");

    private static final EmbedBuilder level3 = new EmbedBuilder()
            .setTitle("Level 3")
            .setImage(Config.get("level3"))
            .setFooter("Enter the abilities Kirby needs to win!");

    private static final EmbedBuilder level4 = new EmbedBuilder()
            .setTitle("Level 4")
            .setImage(Config.get("level4"))
            .setFooter("Enter the abilities Kirby needs to win!");

    private static final EmbedBuilder level5 = new EmbedBuilder()
            .setTitle("Level 5")
            .setFooter("Enter the abilities Kirby needs to win!");

    private static final EmbedBuilder level5boss = new EmbedBuilder()
            .setTitle("Boss Fight!")
            .setImage(Config.get("level5boss"))
            .setFooter("Enter the abilities Kirby should attack with!");

    private static final EmbedBuilder[] levels = {
            level1, level2, level3, level4, level5
    };

    // abilities needed to beat each level
    private static final String[][] levelAbilities = {
            {"cutter", "fire"},
            {"cutter", "beam", "none"},
            {"wheel", "ice", "ninja"},
            {"throw", "cutter", "sword"},
            {"bubble", "cutter", "sword"}
    };

    // ability options for each level
    private static final String[][] givenAbilities = {
            {"beam", "cutter", "electric", "fire"},
            {"beam", "bomb", "cutter", "electric", "none", "sleep"},
            {"electric", "fire", "ice", "none", "stone", "wheel"},
            {"beam", "cutter", "electric", "fire", "none", "stone", "sword", "throw"},
            {"beam", "bomb", "bubble", "cutter", "electric", "fire", "none", "sword"}
    };

    // boss handling -- handled this way to be able to expand in the future
    private static final EmbedBuilder[] bossLevels = {
            level5boss
    };

    private static final String[][][] bossAttacks = {
            {
                    {"Waddle Dee Throw", "Hammer", "Ground Pound", "Hammer", "Waddle Dee Throw"},
                    {"Ground Pound", "Hammer", "Waddle Dee Throw", "Ground Pound", "Hammer"},
                    {"Waddle Dee Throw", "Ground Pound", "Waddle Dee Throw", "Hammer", "Ground Pound"},
                    {"Hammer", "Ground Pound", "Waddle Dee Throw", "Ground Pound", "Hammer"},
                    {"Hammer", "Waddle Dee Throw", "Waddle Dee Throw", "Hammer", "Ground Pound"}
            },
    };

    private static final HashMap<String, String[]> bossWrongAbilities = new HashMap<>();

    private static final String[][] bossGivenAbilities = {
            {"cutter", "ninja", "stone", "sword", "throw"}
    };

    // first index = current number of hits on boss; second index = number of hits needed on the boss
    private static final int[][] bossHits = {
            {0, 2}
    };

    static {
        bossWrongAbilities.put("Waddle Dee Throw", new String[]{"ninja", "throw"});
        bossWrongAbilities.put("Hammer", new String[]{"cutter", "sword"});
        bossWrongAbilities.put("Ground Pound", new String[]{"cutter", "sword", "throw"});
    }

    // used to send embeds with the levels
    protected static MessageEmbed loadLevel(int level) {
        if (level < 1 || level > levels.length) { return null; }
        return levels[level - 1].build();
    }

    // used to determine if the next level should be loaded
    protected static boolean finishedLevel(ArrayList<String> abilities, int level, int startIndex, int length) {
        if (level < 1 || levelAbilities.length < level || abilities.size() != length) { return false; }

        // Katniss Easter egg for level 1
        if (level == 1 && abilities.contains("katniss")) {
            for (String ability : abilities) { if (!ability.equals("katniss")) return false; }
            return true;
        }

        // standard check
        for (int i = startIndex; i < startIndex + length; i++) {
            if (!(levelAbilities[level - 1][i].equals(abilities.get(i - startIndex)))) { return false; }
        }

        return true;
    }

    // used to change the level 5 used to indicate the attacks King Dedede will use
    protected static void set5(int bossAttack) { level5.setImage(Config.get("level5_" + bossAttack)); }

    // used to send embeds with boss levels
    protected static MessageEmbed loadBoss(int bossLevel) {
        if (bossLevel < 1 || bossLevel > bossLevels.length) { return null; }
        bossHits[bossLevel - 1][0] = 0;
        return bossLevels[bossLevel - 1].build();
    }

    // return lost if kirby loses, won if kirby wins, and ongoing if the fight is still going on
    protected static String finishedBoss(String ability, int bossLevel, int bossAttack, int turn) {
        if (bossLevel < 1 || bossLevel > bossLevels.length || turn < 1
                || turn > bossGivenAbilities[bossLevel - 1].length) { return "error"; }

        ability = ability.toLowerCase();
        String attack = bossAttacks[bossLevel - 1][bossAttack][turn - 1];

        // check if kirby loses
        int count = 0;
        for (String ab : bossGivenAbilities[bossLevel - 1]) { if (ability.equals(ab)) count++; }
        if (count < 1) { return "lost," + attack; }

        for (String ab : bossWrongAbilities.get(attack)) { if (ability.equals(ab)) return "lost," + attack; }

        // check if the boss is damaged
        if (!ability.equals("stone")) {
            bossHits[bossLevel - 1][0]++;
            return bossHits[bossLevel - 1][0] == bossHits[bossLevel - 1][1] ? "won," + attack
                    : "ongoing," + attack + ",damaged";
        }

        return "ongoing," + attack + ",not";
    }

    protected static int getTurns(int bossLevel) {
        return bossGivenAbilities[bossLevel - 1].length;
    }

    // used to determine when the victory statement is sent
    public static boolean notWon(String id) {
        int level = GameManager.getLevel(id), bossLevel = GameManager.getBossLevel(id);
        return level != levels.length || bossLevel != bossLevels.length + 1;
    }

    // used to add reactions for abilities kirby can use for the level
    public static void addReactions(String id, Message message) {
        int level = GameManager.getLevel(id), bossLevel = GameManager.getBossLevel(id);

        // normal level reactions
        if (level%5 != 0 || bossLevel != level/5) {
            // discoveries
            String[] discoveryAbilities = GameManager.getDiscoveryAbilities(id);

            if (discoveryAbilities != null) {
                for (String ab : discoveryAbilities) { message.addReaction(Config.get(ab)).queue(); }
                return;
            }

            // standard level reactions
            for (String ab : givenAbilities[level - 1]) { message.addReaction(Config.get(ab)).queue(); }
            return;
        }

        // boss level reactions
        for (String ab : bossGivenAbilities[bossLevel - 1]) { message.addReaction(Config.get(ab)).queue(); }
    }

    // used to make sure a user can't react with an ability other than the ones provided
    public static boolean notValid(String id, String ability) {
        int level = GameManager.getLevel(id), bossLevel = GameManager.getBossLevel(id);

        // Katniss Easter egg -- Katniss is my cat
        if (level == 1 && ability.equalsIgnoreCase("katniss")) { return false; }

        // normal reactions
        if (level%5 != 0 || bossLevel != level/5) {
            // discoveries
            String[] discoveryAbilities = GameManager.getDiscoveryAbilities(id);

            if (discoveryAbilities != null) {
                for (String ab : discoveryAbilities) { if (ab.equalsIgnoreCase(ability)) return false; }
                return true;
            }

            // standard level reactions
            for (String ab : givenAbilities[level - 1]) { if (ab.equalsIgnoreCase(ability)) return false; }
            return true;
        }

        // boss reactions
        for (String ab : bossGivenAbilities[bossLevel - 1]) { if (ab.equalsIgnoreCase(ability)) return false; }
        return true;
    }
}
