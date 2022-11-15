package bot.command.game;

import bot.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;

public class TutorialManager {
    private static final HashMap<String, TutorialManager> tutorialManagers = new HashMap<>();

    // messages
    private static final EmbedBuilder tut1 = new EmbedBuilder()
            .setTitle("Tutorial Level 1")
            .setImage(Config.get("Tutorial1"))
            .setFooter("Choose the ability Kirby needs to win!");

    private static final EmbedBuilder tut2 = new EmbedBuilder()
            .setTitle("Tutorial Level 2")
            .setImage(Config.get("Tutorial2_0"))
            .setFooter("Choose the ability Kirby needs to win!");

    private static final EmbedBuilder tut2_1 = new EmbedBuilder()
            .setTitle("Another Electric Tutorial")
            .setImage(Config.get("Tutorial2_1"))
            .setFooter("Choose the ability Kirby needs to win!");

    private static final EmbedBuilder tut3 = new EmbedBuilder()
            .setTitle("Tutorial Level 3")
            .setImage(Config.get("Tutorial3"))
            .setFooter("Choose the ability Kirby needs to win!");

    private static final EmbedBuilder tut4 = new EmbedBuilder()
            .setTitle("Tutorial Level 4")
            .setImage(Config.get("Tutorial4"))
            .setFooter("Choose the ability Kirby needs to win!");

    private static final EmbedBuilder tut5 = new EmbedBuilder()
            .setTitle("Tutorial Level 5")
            .setImage(Config.get("Tutorial5"))
            .setFooter("Choose the ability Kirby needs to win!");

    private static final EmbedBuilder tut6 = new EmbedBuilder()
            .setTitle("Tutorial Level 6")
            .setImage(Config.get("Tutorial6"))
            .setFooter("Choose the ability Kirby needs to win!");

    private static final EmbedBuilder tut7 = new EmbedBuilder()
            .setTitle("Tutorial Level 7")
            .setImage(Config.get("Tutorial7"))
            .setFooter("Choose the ability Kirby needs to win!");

    private static final EmbedBuilder tut8 = new EmbedBuilder()
            .setTitle("Tutorial Level 8")
            .setImage(Config.get("Tutorial8"))
            .setFooter("Choose the ability Kirby needs to win!");

    private static final EmbedBuilder tut9 = new EmbedBuilder()
            .setTitle("Tutorial Level 9")
            .setImage(Config.get("Tutorial9"))
            .setFooter("Choose the ability Kirby needs to win!");

    private static final EmbedBuilder tut10 = new EmbedBuilder()
            .setTitle("Tutorial Level 10")
            .setImage(Config.get("Tutorial10"))
            .setFooter("Choose the ability Kirby needs to win!");

    private static final EmbedBuilder tut11 = new EmbedBuilder()
            .setTitle("Tutorial Level 11")
            .setImage(Config.get("Tutorial11"))
            .setFooter("Choose the ability Kirby needs to win!");

    private static final EmbedBuilder tut12 = new EmbedBuilder()
            .setTitle("Tutorial Level 12")
            .setImage(Config.get("Tutorial12"))
            .setFooter("Choose the ability Kirby needs to win!");

    private static final EmbedBuilder tut13 = new EmbedBuilder()
            .setTitle("Tutorial Level 13")
            .setImage(Config.get("Tutorial13"))
            .setFooter("Choose the ability Kirby needs to win!");

    private static final EmbedBuilder tut14 = new EmbedBuilder()
            .setTitle("Tutorial Level 14")
            .setImage(Config.get("Tutorial14"))
            .setFooter("Choose the ability Kirby needs to win!");

    private static final EmbedBuilder tut15 = new EmbedBuilder()
            .setTitle("Tutorial Level 15")
            .setImage(Config.get("Tutorial15"))
            .setFooter("Choose the ability Kirby needs to win!");

    private static final Message mess1 = new MessageBuilder()
            .append("""
                    **__General Game Tutorial__**
                    > First let's learn about how the game works.
                    > The goal is to __get to the door__ in each level.
                    > You can reach the door by __clearing obstacles__ in your path.
                    > If any are left Kirby will not be able to beat the level.
                    > Kirby is always __able to jump__ unless he has the stone ability.
                    > You will be told how many abilities you get to choose for each level.
                    > For the purposes of the tutorial each level will only have 1 ability solutions.
                    > However, in levels with multiple abilities needed, the order of abilities will matter.
                    
                    **__Fire Tutorial__**
                    > The first ability we'll introduce is the fire ability.
                    > Fire can be used to __melt ice__. This most commonly is used to __get behind ice blocks__.
                    > Try using the fire ability in the level above.
                    """)
            .build();

    private static final Message mess2 = new MessageBuilder()
            .append("""
                    **__Electric Tutorial__**
                    > The next ability we'll introduce is the electric ability.
                    > Electric can be used to send an electric shock out.
                    > This shock can be carried as a current in wires.
                    > Wires will __drop any items they are holding__ and can __kill enemies__ while carrying a current.
                    > However, this __will not destroy the wire__ so you would not be able to pass through it.
                    > Wires will break if stone blocks are dropped on them, but reinforced wires will not.
                    > Try using the electric ability in this level.
                    """)
            .build();

    private static final Message mess2_1 = new MessageBuilder()
            .append("""
                    **__Electric Tutorial__**
                    > Here's another example of how the electric ability can be used.
                    > Try using it here.
                    """)
            .build();

    private static final Message mess3 = new MessageBuilder()
            .append("""
                    **__Beam Tutorial__**
                    > Now we'll introduce the beam ability.
                    > The beam ability can send out a beam sword.
                    > This beam sword will __damage all enemies it hits__.
                    > Try using the beam ability in this level.
                    """)
            .build();

    private static final Message mess4 = new MessageBuilder()
            .append("""
                    **__Ice Tutorial__**
                    > Next we'll introduce the ability ice.
                    > Ice allows Kirby to shoot ice out from his mouth.
                    > Kirby's ice breath __freezes level components__ and can __put out fires__.
                    > Ice can __stop electrical currents flowing in wires__ and can be __combined with fire to melt through wires and ropes__.
                    > __Enemies are immune__ to Kirby's ice breath though.
                    > Try using ice in the level above.
                    """)
            .build();

    private static final Message mess5 = new MessageBuilder()
            .append("""
                    **__Sword Tutorial__**
                    > We'll introduce the sword ability now.
                    > Sword gives Kirby a sword to wield.
                    > This sword is able to __cut through ropes, damage enemies, and break star blocks__.
                    > Try using it in this level.
                    """)
            .build();

    private static final Message mess6 = new MessageBuilder()
            .append("""
                    **__Bomb Tutorial__**
                    > Here we'll introduce you to the bomb ability.
                    > The bomb ability gives Kirby access to bombs.
                    > These bombs can be placed and will detonate after a certain amount of time.
                    > Bombs can be used to __blow up star blocks and stone blocks__; however, they can only detonate __while on the ground__.
                    > Enemies will move out of the way before bombs detonate too, so they __cannot damage enemies__.
                    > Try using bomb in this level.
                    """)
            .build();

    private static final Message mess7 = new MessageBuilder()
            .append("""
                    **__Cutter Tutorial__**
                    > The cutter ability allows Kirby to throw boomerang-esk projectiles.
                    > These boomerangs can __cut through wires and ropes__, forcing them to __drop anything they're holding__.
                    > However, cutter is unable to cut through reinforced wires.
                    > Try using cutter in the level above.
                    """)
            .build();

    private static final Message mess8 = new MessageBuilder()
            .append("""
                    **__Sleep Tutorial__**
                    > The sleep ability makes Kirby fall asleep.
                    > This __does not help you in any way__ and will force you to restart the level.
                    > This ability is only used for ability discovers to make it impossible to win if you pick up an unnecessary one.
                    > Use sleep in the level to move onto the next stage.
                    """)
            .build();

    private static final Message mess9 = new MessageBuilder()
            .append("""
                    **__None Ability Tutorial__**
                    > The none ability isn't really an ability but rather a lack of one.
                    > Kirby is able to __inhale star blocks and spit them out__ at enemies and other star blocks without an ability.
                    > Try using it in this level.
                    """)
            .build();

    private static final Message mess10 = new MessageBuilder()
            .append("""
                    **__Stone Tutorial__**
                    > The stone ability turns Kirby into a solid stone.
                    > Kirby is __invincible__ while in his stone form, but he __cannot jump__ as a stone.
                    > Kirby can __walk past enemies__ as a stone, but can't damage them.
                    > Kirby can also __walk through wires, ropes, star blocks, and flames__ in this form.
                    > Try using the stone ability in the level above.
                    """)
            .build();

    private static final Message mess11 = new MessageBuilder()
            .append("""
                    **__Tornado Tutorial__**
                    > The tornado ability turns Kirby into a tornado.
                    > This Kirby tornado is able to __break through star blocks and damage enemies__.
                    > Try using it in this level.
                    """)
            .build();

    private static final Message mess12 = new MessageBuilder()
            .append("""
                    **__Wheel Tutorial__**
                    > The wheel ability allows Kirby to turn into a wheel.
                    > As a wheel, Kirby will __continue rolling until he hits a wall__.
                    > He can __break through stone blocks and damage enemies__ as a wheel too.
                    > Kirby is able to __climb up walls if there is a slope__ leading up to it while in wheel form.
                    > Try out the wheel ability in the level above.
                    """)
            .build();

    private static final Message mess13 = new MessageBuilder()
            .append("""
                    **__Ninja Tutorial__**
                    > The ninja ability transforms Kirby into a ninja.
                    > This allows Kirby to __wall jump, damage enemies with ninja stars, and sneak through dotted blocks__.
                    > Ninja is the only ability that allows Kirby to pass through dotted blocks.
                    > Try it out in the level above.
                    """)
            .build();

    private static final Message mess14 = new MessageBuilder()
            .append("""
                    **__Bubble Tutorial__**
                    > The bubble ability lets Kirby shoot out bubbles.
                    > These bubbles can __move level components__ upward.
                    > The bubbles can cause the components to latch onto ropes and wires, but if there aren't any above the bubble, the object will fall back to its original spot.
                    > Try using it in this level.
                    """)
            .build();

    private static final Message mess15 = new MessageBuilder()
            .append("""
                    **__Throw Tutorial__**
                    > The throw ability lets Kirby __throw star blocks and enemies the same size as him__.
                    > These star blocks can be __used to damage enemies or break star blocks__.
                    > Kirby can throw star blocks into objects suspended by a rope or wire, __forcing the object to swing like a pendulum__.
                    > Throw can be combined with cutter to __launch objects suspended by a rope or wire__.
                    > Try using throw in the level above.
                    """)
            .build();

    // array handlers
    private static final EmbedBuilder[] levels = {
            tut1, tut2, tut2_1, tut3, tut4, tut5, tut6, tut7, tut8, tut9, tut10,
            tut11, tut12, tut13, tut14, tut15
    };

    private static final Message[] tutorialMessages = {
            mess1, mess2, mess2_1, mess3, mess4, mess5, mess6, mess7, mess8, mess9, mess10,
            mess11, mess12, mess13, mess14, mess15
    };

    private static final String[] completionAbilities = {
            "fire",
            "electric",
            "electric",
            "beam",
            "ice",
            "sword",
            "bomb",
            "cutter",
            "sleep",
            "none",
            "stone",
            "tornado",
            "wheel",
            "ninja",
            "bubble",
            "throw"
    };

    private static final String[][] givenAbilities = {
            {"beam", "cutter", "fire"},
            {"bomb", "electric", "ice"},
            {"bomb", "electric", "ice"},
            {"beam", "fire", "throw"},
            {"bubble", "ice", "sword"},
            {"electric", "fire", "sword"},
            {"bomb", "electric", "ice"},
            {"cutter", "ice", "throw"},
            {"bubble", "ninja", "sleep"},
            {"beam", "none", "stone"},
            {"beam", "none", "stone"},
            {"cutter", "sleep", "tornado"},
            {"ice", "tornado", "wheel"},
            {"bubble", "ninja", "throw"},
            {"bubble", "stone", "sword"},
            {"electric", "ice", "throw"}
    };

    private String levelMessageID;
    private int level;

    private TutorialManager() {
        level = 1;
    }

    public static MessageEmbed start(String id) {
        // tell the game to handle tutorials
        GameManager.start(id);
        GameManager.setTutorial(id, true);

        // new game instance
        if (!tutorialManagers.containsKey(id)) {
            tutorialManagers.put(id, new TutorialManager());
            return loadLevel(id);
        }

        // reset if player has won
        if (!notWon(id)) { return reset(id); }
        return loadLevel(id);
    }

    // command handler
    public static MessageEmbed addAbility(String id, String ability, TextChannel channel) {
        MessageBuilder message = new MessageBuilder().append("Kirby was given **").append(ability).append("**\n");

        // check if the player beat the level
        if (finishedLevel(id, ability.toLowerCase())) {
            channel.sendMessage(message.append("Congrats! You beat the level.\nBeginning next level...").build()).queue();
            return changeLevel(id);
        }

        channel.sendMessage(message.append("You didn't get the right combo.\nResetting level...").build()).queue();
        return loadLevel(id);
    }

    public static MessageEmbed reset(String id) {
        tutorialManagers.get(id).level = 1;
        return loadLevel(id);
    }

    public static Message stop(String id) {
        tutorialManagers.remove(id);
        GameManager.stop(id);
        return new MessageBuilder().append("Game stopped successfully.").build();
    }

    // handle the active messageID
    public static void setMessageID (String id, String messageID) {
        tutorialManagers.get(id).levelMessageID = messageID;
    }

    public static String getMessageID(String id) {
        return tutorialManagers.get(id).levelMessageID;
    }

    public static Message getNumAbilities() {
        return new MessageBuilder()
                .append("You need 1 ability to beat this level.")
                .build();
    }

    public static Message getTutorialMessage(String id) {
        return tutorialMessages[tutorialManagers.get(id).level - 1];
    }

    // handle level components
    private static MessageEmbed loadLevel(String id) {
        int level = tutorialManagers.get(id).level;

        // completed the game
        if (level < 1 || level > levels.length) {
            GameManager.setTutorial(id, false);
            return new EmbedBuilder()
                    .setTitle("Victory")
                    .addField("Congrats! You finished the tutorial!", "Use -start if you would like to play the game.", false)
                    .build();
        }

        return levels[level - 1].build();
    }

    private static MessageEmbed changeLevel(String id) {
        tutorialManagers.get(id).level++;
        return loadLevel(id);
    }
    private static boolean finishedLevel(String id, String ability) {
        int level = tutorialManagers.get(id).level;
        if (level < 1 || completionAbilities.length < level) { return false; }
        return ability.equals(completionAbilities[level - 1]);
    }

    // used to determine when the victory statement is sent
    public static boolean notWon(String id) {
        return tutorialManagers.get(id).level <= levels.length;
    }

    // handle message reactions for the levels
    public static void addReactions(String id, Message message) {
        for (String ability : givenAbilities[tutorialManagers.get(id).level - 1]) {
            message.addReaction(Config.get(ability)).queue();
        }
    }

    // ensure only valid reactions can be added
    public static boolean notValid(String id, String ability) {
        for (String ab : givenAbilities[tutorialManagers.get(id).level - 1]) {
            if (ability.equalsIgnoreCase(ab)) { return false; }
        }

        return true;
    }
}
