package bot.command.game;

import bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {
    // todo plan for next time
        // intro section to help players learn the game

    private static final HashMap<String, GameManager> gameManagers = new HashMap<>();

    private static final int[][] numChoices = {
            {2},
            {3},
            {2, 1},
            {3},
            {3}
    };

    private static final String[][] discoveries = {
            {"bomb", "ninja", "tornado"}, // level 3
            {"beam", "sword", "wheel"}, // level 4
            {"sleep", "sword", "wheel"}, {"ice", "sleep", "stone"}, {"ninja", "tornado", "wheel"} // level 5
    };

    private boolean tutorial;

    private String levelMessageID;
    private final ArrayList<String> abilities;
    private boolean discover; // used to ensure the user is only adding valid abilities through discoveries
    private int level, bossLevel, discovery, numChoicesIndex, startIndex, turn, bossAttack;

    private GameManager() {
        level = 1;
        bossLevel = 0;
        discovery = -1;
        numChoicesIndex = 0;
        startIndex = 0;
        turn = 1;
        bossAttack = (int)(Math.random() * 5);
        discover = false;
        tutorial = false;
        LevelManager.set5(bossAttack);
        abilities = new ArrayList<>();
    }

    public static MessageEmbed start(String id) {
        // new game instance
        if (!gameManagers.containsKey(id)) {
            gameManagers.put(id, new GameManager());
            return loadLevel(id);
        }

        // if the player has already won, reset
        if (!LevelManager.notWon(id)) { return reset(id); }
        return loadLevel(id);
    }

    private static MessageEmbed loadLevel(String id) {
        GameManager manager = gameManagers.get(id);
        if (manager.level%5 == 0 && manager.level/5 == manager.bossLevel) { return LevelManager.loadBoss(manager.bossLevel); }
        return LevelManager.loadLevel(manager.level);
    }

    private static MessageEmbed changeLevel(String id) {
        GameManager manager = gameManagers.get(id);
        if (manager.level%5 == 0 && manager.level/5 != manager.bossLevel) { manager.bossLevel++; }
        else { manager.level++; }
        return loadLevel(id);
    }

    public static Message getNumAbilities(String id) {
        GameManager manager = gameManagers.get(id);
        if (manager.level%5 == 0 && manager.level/5 == manager.bossLevel) {
            return new MessageBuilder()
                    .append("You have 5 turns to beat the boss.")
                    .build();
        }

        return new MessageBuilder()
                .append("You need ")
                .append(numChoices[manager.level - 1][0])
                .append(" abilities to beat this level.")
                .build();
    }

    // used to check which emojis the bot should react with
    protected static int getLevel(String id) {
        return gameManagers.get(id).level;
    }

    // used to check which emojis the bot should react with
    protected static int getBossLevel(String id) {
        return gameManagers.get(id).bossLevel;
    }

    // manage abilities
    public static MessageEmbed addAbility (String id, String ability, TextChannel channel) {
        GameManager manager = gameManagers.get(id);

        // handle normal level ability additions
        if (manager.level%5 != 0 || manager.bossLevel != manager.level/5) {
            if (manager.abilities.contains(ability.toLowerCase()) && !ability.equalsIgnoreCase("katniss")) {
                channel.sendMessage("Kirby already has this ability.").queue();
                return null;
            }

            manager.abilities.add(ability.toLowerCase());
            int choices = numChoices[manager.level - 1][manager.numChoicesIndex];

            // check if there are enough abilities to finish the level
            if (manager.abilities.size() == choices) {
                // check if the player beat the level
                if (LevelManager.finishedLevel(manager.abilities, manager.level, manager.startIndex, choices)) {
                    manager.abilities.clear();

                    if (manager.numChoicesIndex == numChoices[manager.level - 1].length - 1) {
                        channel.sendMessage(
                                new MessageBuilder().append("Kirby was given **").append(ability).append("**.\n")
                                        .append("Congrats! You beat the level.\nBeginning next level...")
                                        .build()).queue();

                        manager.discover = false;
                        manager.startIndex = 0;
                        manager.numChoicesIndex = 0;
                        return GameManager.changeLevel(id);
                    }

                    // handle ability discovery
                    channel.sendMessage(new MessageBuilder()
                            .append("Kirby was given **")
                            .append(ability)
                            .append("**.")
                            .build()).queue();

                    manager.numChoicesIndex++;
                    manager.startIndex += choices;
                    discover(id, channel);
                    manager.discover = true;
                    return null;
                }

                // reset the level if the player failed
                manager.abilities.clear();
                manager.discovery -= manager.numChoicesIndex;
                manager.startIndex = 0;
                manager.numChoicesIndex = 0;
                manager.discover = false;

                channel.sendMessage(
                        new MessageBuilder().append("Kirby was given **").append(ability).append("**.\n")
                                .append("You didn't get the right combo.\nResetting level...")
                                .build()).queue();

                return loadLevel(id);
            }

            channel.sendMessage(new MessageBuilder().append("Kirby was given **").append(ability).append("**.").build()).queue();
            return null;
        }

        // handle boss levels
        if (manager.turn > LevelManager.getTurns(manager.bossLevel)) {
            manager.abilities.clear();
            manager.turn = 1;
            manager.bossLevel--;
            manager.bossAttack = (int)(Math.random() * 5);
            LevelManager.set5(manager.bossAttack);

            channel.sendMessage("""
                    Kirby has no abilities left and lost the fight.
                    Dedede's attacks are hidden earlier in the level.
                    Restarting level...""").queue();

            return loadLevel(id);
        }

        // ensure each ability can only be used once
        if (manager.abilities.contains(ability)) {
            channel.sendMessage("Kirby has already used this ability.").queue();
            return null;
        }

        String status = LevelManager.finishedBoss(ability, manager.bossLevel, manager.bossAttack, manager.turn);

        // check for the status of the boss fight
        if (status.startsWith("error")) {
            manager.abilities.clear();
            manager.turn = 1;
            manager.bossLevel--;
            manager.bossAttack = (int)(Math.random() * 5);
            LevelManager.set5(manager.bossAttack);

            channel.sendMessage("""
                    An error occurred during the boss fight so the fight must be restarted.
                    Dedede's attacks are hidden earlier in the level.
                    Restarting level...""").queue();

            return loadLevel(id);
        }

        if (status.startsWith("lost")) {
            String attack = status.substring(status.indexOf(",") + 1);
            manager.abilities.clear();
            manager.turn = 1;
            manager.bossLevel--;
            manager.bossAttack = (int)(Math.random() * 5);
            LevelManager.set5(manager.bossAttack);

            channel.sendMessage("Kirby was hit by **" + attack + "** while using **" + ability + "**." + "" +
                    "\nKing Dedede wins!\nDedede's attacks are hidden earlier in the level." +
                    "\nRestarting level...").queue();

            return loadLevel(id);
        }

        if (status.startsWith("won")) {
            String attack = status.substring(status.indexOf(",") + 1);
            manager.abilities.clear();
            manager.turn = 1;
            manager.bossLevel++;

            channel.sendMessage("King Dedede used **" + attack + "** and was hit by Kirby using **"
                    + ability + "**.\nKirby wins!").queue();

            return new EmbedBuilder()
                    .setTitle("Victory")
                    .addField("Congrats! You won!", "If you would like to play again, use -reset to restart the game.", false)
                    .build();
        }

        manager.abilities.add(ability);
        manager.turn++;

        String attack = status.substring(status.indexOf(",") + 1, status.indexOf(",", status.indexOf(",") + 1));

        // inform the player which attacks were used and if the boss was damaged
        if (status.substring(status.indexOf(",", status.indexOf(",") + 1) + 1).equals("not")) {
            channel.sendMessage("Kirby used **" + ability + "** and was immune to King Dedede's " +
                    "**" + attack + "** attack.").queue();

            return null;
        }

        channel.sendMessage("Kirby used **" + ability + "** and hit King Dedede." +
                "\nDedede used **" + attack + "** and missed.").queue();

        return null;
    }

    public static Message removeAbility (String id, String ability) {
        GameManager manager = gameManagers.get(id);

        // handle removal attempts during boss fights
        if (manager.level%5 == 0 && manager.bossLevel == manager.level/5) {
            return !ability.equals("all") ?
                    new MessageBuilder().append("Kirby has already used this ability, so it cannot be removed.").build()
                    : new MessageBuilder().append("Kirby has no abilities that can be removed.").build();
        }

        // remove all abilities
        if (ability.equals("all")) {
            if (manager.abilities.size() < 1) {
                return new MessageBuilder()
                        .append("Kirby has no abilities so none could be removed.")
                        .build();
            }

            manager.abilities.clear();
            return new MessageBuilder()
                    .append("All abilities were removed.")
                    .build();
        }

        // standard level ability removal
        if (!manager.abilities.contains(ability)) {
            return new MessageBuilder()
                    .append("Kirby does not have this ability, so it cannot be removed.")
                    .build();
        }

        manager.abilities.remove(ability);
        return new MessageBuilder()
                .append("Kirby lost the **")
                .append(ability)
                .append("** ability.")
                .build();
    }

    // check if an instance of the game is running for any given user
    public static boolean notRunning(String id) {
        return !gameManagers.containsKey(id);
    }

    public static MessageEmbed reset(String id) {
        GameManager manager = gameManagers.get(id);
        manager.level = 1;
        manager.startIndex = 0;
        manager.numChoicesIndex = 0;
        manager.discovery = -1;
        manager.turn = 1;
        manager.bossLevel = 0;
        manager.bossAttack = (int)(Math.random() * 5);
        LevelManager.set5(manager.bossAttack);
        manager.abilities.clear();
        return loadLevel(id);
    }

    public static Message stop(String id) {
        gameManagers.remove(id);
        return new MessageBuilder().append("Game successfully stopped.").build();
    }

    // used for handling reactions
    public static void setLevelMessageID(String id, String messageID) {
         gameManagers.get(id).levelMessageID = messageID;
    }

    public static String getLevelMessageID(String id) {
        return gameManagers.get(id).levelMessageID;
    }

    // methods for handling discoveries
    private static String[] getDiscovery (String id) {
        GameManager manager = gameManagers.get(id);
        manager.discovery++;
        return discoveries[manager.discovery];
    }

    private static void discover (String id, TextChannel channel) {
        String[] reactions = getDiscovery(id);

        channel.sendMessage("Choose which ability Kirby should take!").queue(
                message -> {
                    setLevelMessageID(id, message.getId());
                    for (String reaction : reactions)
                        message.addReaction(Config.get(reaction)).queue();
                }
        );
    }

    // used for ensuring the user only adds valid abilities through discoveries
    protected static String[] getDiscoveryAbilities (String id) {
        GameManager manager = gameManagers.get(id);
        if (!manager.discover) { return null; }

        return discoveries[manager.discovery];
    }

    // used for tutorial handling
    public static void setTutorial(String id, boolean tut) { gameManagers.get(id).tutorial = tut; }
    public static boolean isTutorial(String id) { return gameManagers.get(id).tutorial; }
}
