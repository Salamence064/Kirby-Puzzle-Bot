package bot.command.commands;

import bot.command.CommandContext;
import bot.command.ICommand;
import bot.command.game.TutorialManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class TutorialCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        String id = ctx.getAuthor().getId();

        MessageEmbed embed = TutorialManager.start(id);

        channel.sendMessage("Tutorial starting...").queue();
        channel.sendMessageEmbeds(embed).queue(
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

    @Override
    public String getName() {
        return "tutorial";
    }

    @Override
    public MessageEmbed getHelp(String prefix) {
        return new EmbedBuilder()
                .setTitle("__Command: " + prefix + "tutorial__")
                .addField("Description: ", "Starts the game's tutorial.", false)
                .addField("Usage: ", prefix + "tutorial", false)
                .build();
    }

    @Override
    public String getDescription() {
        return "Start the game's tutorial.";
    }

    @Override
    public List<String> getAliases() {
        return List.of("tut", "starttutorial", "t");
    }
}
