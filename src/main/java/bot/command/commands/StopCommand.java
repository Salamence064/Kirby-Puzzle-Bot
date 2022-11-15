package bot.command.commands;

import bot.command.CommandContext;
import bot.command.ICommand;
import bot.command.game.GameManager;
import bot.command.game.TutorialManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class StopCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        String id = ctx.getAuthor().getId();

        if (GameManager.notRunning(id)) {
            channel.sendMessage("You do not have a running game instance.").queue();
            return;
        }

        channel.sendMessage("Stopping game...").queue();

        // handle tutorial
        if (GameManager.isTutorial(id)) {
            channel.sendMessage(TutorialManager.stop(id)).queue();
            return;
        }

        // handle standard levels
        channel.sendMessage(GameManager.stop(id)).queue();
    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public MessageEmbed getHelp(String prefix) {
        return new EmbedBuilder()
                .setTitle("__Command: " + prefix + "stop__")
                .addField("Description: ", "Stops the user's game.", false)
                .addField("Usage: ", prefix + "stop", false)
                .build();
    }

    @Override
    public String getDescription() {
        return "Stops the game";
    }

    @Override
    public List<String> getAliases() {
        return List.of("exit", "quit", "end");
    }
}
