package bot.command.commands;

import bot.command.CommandContext;
import bot.command.ICommand;
import bot.command.game.GameManager;
import bot.command.game.LevelManager;
import bot.command.game.TutorialManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.util.List;

public class StartCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        String id = ctx.getAuthor().getId();

        MessageEmbed embed = GameManager.start(id);
        if (GameManager.isTutorial(id)) { GameManager.setTutorial(id, false); }

        channel.sendMessage("Game starting...").queue();
        channel.sendMessageEmbeds(embed).queue(
                message -> {
                    if (LevelManager.notWon(id)) {
                        GameManager.setLevelMessageID(id, message.getId());
                        LevelManager.addReactions(id, message);
                        channel.sendMessage(TutorialManager.getTutorialMessage(id)).queue();
                        channel.sendMessage(GameManager.getNumAbilities(id)).queue();
                    }
                }
        );
    }

    @Override
    public String getName() { return "start"; }

    @Override
    public MessageEmbed getHelp(String prefix) {
        return new EmbedBuilder()
                .setTitle("__Command: " + prefix + "start__")
                .addField("Description: ", "Starts the game.", false)
                .addField("Usage: ", prefix + "start", false)
                .build();
    }

    @Override
    public String getDescription() {
        return "Start the game";
    }

    @Override
    public List<String> getAliases() {
        return List.of("begin");
    }
}
