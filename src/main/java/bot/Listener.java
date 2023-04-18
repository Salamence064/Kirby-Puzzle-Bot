package bot;

import bot.command.game.GameManager;
import bot.command.game.LevelManager;
import bot.command.game.TutorialManager;
import me.duncte123.botcommons.BotCommons;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Listener extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    private static final CommandManager manager = new CommandManager();
    private static boolean removal = true;

    @Override
    public void onReady (@NotNull ReadyEvent event) {
        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onGuildMessageReceived (@NotNull GuildMessageReceivedEvent event) {
        User user = event.getAuthor();
        TextChannel channel = event.getChannel();

        if (user.isBot() || event.isWebhookMessage()) { return; }

        String prefix = Config.get("prefix");
        String raw = event.getMessage().getContentRaw();

        if (raw.equalsIgnoreCase(prefix + "shutdown") && user.getId().equals(Config.get("owner_id"))) {
            LOGGER.info("Shutting down.");
            event.getJDA().shutdown();
            BotCommons.shutdown(event.getJDA());

            return;
        }

        if (raw.equalsIgnoreCase(prefix + "shutdown")) {
            channel.sendMessage("You do not have permission to shut down Kirby. Permissions needed: Bot Owner").queue();
            LOGGER.info("Failed shutdown by @" + user.getAsTag());

            return;
        }

        if (raw.startsWith(prefix)) {
            manager.handle(event, prefix);
        }
    }

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        if (event.getUser().isBot()) { return; }

        String id = event.getUser().getId();

        // handle the tutorial
        if (GameManager.isTutorial(id)) {
            if (event.getMessageId().equals(TutorialManager.getMessageID(id))) {
                TextChannel channel = event.getChannel();
                String ability = event.getReactionEmote().getName().toLowerCase();

                if (TutorialManager.notValid(id, ability)) {
                    event.getReaction().removeReaction(event.getUser()).queue();
                    channel.sendMessage("The ability**" + ability + "** is not available for this level." +
                            "\nPick an ability the bot's reacted with to use an ability.").queue();
                    return;
                }

                channel.sendMessageEmbeds(TutorialManager.addAbility(id, ability, channel)).queue(
                        message -> {
                            if (TutorialManager.notWon(id)) {
                                TutorialManager.setMessageID(id, message.getId());
                                TutorialManager.addReactions(id, message);
                                channel.sendMessage(TutorialManager.getTutorialMessage(id)).queue(
                                        message1 -> channel.sendMessage(TutorialManager.getNumAbilities()).queue()
                                );
                            }
                        }
                );
            }

            return;
        }

        // handle standard levels
        // ensure the message being checked is the current level
        if (event.getMessageId().equals(GameManager.getLevelMessageID(id))) {
            TextChannel channel = event.getChannel();
            String ability = event.getReactionEmote().getName().toLowerCase();

            if (LevelManager.notValid(id, ability)) {
                removal = false;
                event.getReaction().removeReaction(event.getUser()).queue();
                channel.sendMessage("The ability **" + ability + "** is not available for this level." +
                        "\nPick an ability the bot's reacted with to use an ability.").queue();
                return;
            }

            MessageEmbed message = GameManager.addAbility(id, ability, channel);
            if (message == null) { return; }

            channel.sendMessageEmbeds(message).queue(
                    message1 -> {
                        if (LevelManager.notWon(id)) {
                            GameManager.setLevelMessageID(id, message1.getId());
                            LevelManager.addReactions(id, message1);
                            channel.sendMessage(GameManager.getNumAbilities(id)).queue();
                        }
                    }
            );
        }
    }

    @Override
    public void onGuildMessageReactionRemove(@NotNull GuildMessageReactionRemoveEvent event) {
        String id = Objects.requireNonNull(event.getUser()).getId();
        if (GameManager.isTutorial(id)) { return; } // cannot remove abilities in the tutorial

        // ensure the message being checked is the current level
        if (event.getMessageId().equals(GameManager.getLevelMessageID(id)) && !event.getUser().isBot() && removal) {
            TextChannel channel = event.getChannel();
            String ability = event.getReactionEmote().getName().toLowerCase();

            channel.sendMessage(GameManager.removeAbility(id, ability)).queue();
            return;
        }

        removal = true;
    }
}
