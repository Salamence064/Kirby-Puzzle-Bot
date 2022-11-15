package bot.command.commands;

import bot.command.CommandContext;
import bot.command.ICommand;
import bot.command.game.GameManager;
import bot.command.game.LevelManager;
import bot.command.game.TutorialManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

public class ResetCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        String id = ctx.getAuthor().getId();

        if (GameManager.notRunning(id)) {
            channel.sendMessage("You do not have a running game instance.").queue();
            return;
        }

        channel.sendMessage("Resetting game...").queue();
        channel.sendMessage("Game successfully restarted.").queue();

        // handle tutorial levels
        if (GameManager.isTutorial(id)) {
            channel.sendMessageEmbeds(TutorialManager.reset(id)).queue(
                    message -> {
                        TutorialManager.setMessageID(id, message.getId());
                        TutorialManager.addReactions(id, message);
                        channel.sendMessage(TutorialManager.getTutorialMessage(id)).queue(
                                message1 -> channel.sendMessage(TutorialManager.getNumAbilities()).queue()
                        );
                    }
            );

            return;
        }

        // handle standard levels
        channel.sendMessageEmbeds(GameManager.reset(id)).queue(
                message -> {
                    GameManager.setLevelMessageID(id, message.getId());
                    LevelManager.addReactions(id, message);
                    channel.sendMessage(GameManager.getNumAbilities(id)).queue();
                }
        );
    }

    @Override
    public String getName() {
        return "reset";
    }

    @Override
    public MessageEmbed getHelp(String prefix) {
        return new EmbedBuilder()
                .setTitle("__Command: " + prefix + "reset__")
                .addField("Description: ", "Resets the game to the first level.", false)
                .addField("Usage: ", prefix + "reset", false)
                .build();
    }

    @Override
    public String getDescription() {
        return "Resets the user's game.";
    }
}
