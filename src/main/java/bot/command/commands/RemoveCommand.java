package bot.command.commands;

import bot.Config;
import bot.command.CommandContext;
import bot.command.ICommand;
import bot.command.game.GameManager;
import bot.command.game.LevelManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class RemoveCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();
        String prefix = Config.get("prefix");

        if (args.isEmpty()) {
            channel.sendMessageEmbeds(new EmbedBuilder()
                    .setTitle("__Command: " + prefix + "remove__")
                    .addField("Description: ", "Removes Kirby's active ability.", false)
                    .addField("Usage: ", prefix + "remove [ability]", false).build())
                    .queue();

            return;
        }

        String id = ctx.getAuthor().getId();

        if (GameManager.notRunning(id)) {
            channel.sendMessage("You must start the game before adding abilities.").queue();
            return;
        }

        if (GameManager.isTutorial(id)) {
            channel.sendMessage("You cannot remove any abilities as the solution takes only 1 ability.").queue();
            return;
        }

        StringBuilder ability = new StringBuilder();
        for (int i = 0; i < args.size(); i++) {
            ability.append(args.get(i));
            if (i != args.size() - 1) { ability.append(" "); }
        }

        String messageID = GameManager.getLevelMessageID(id);

        if (ability.toString().equals("all")) {
            ctx.getChannel().retrieveMessageById(messageID).queue(message ->
                    message.clearReactions().queue(success ->
                            LevelManager.addReactions(id, message))
            );
            channel.sendMessage(GameManager.removeAbility(id, "all")).queue();
            return;
        }

        // remove reaction and ability
        ctx.getChannel().retrieveMessageById(messageID).queue(message ->
                message.removeReaction(Config.get(ability.toString()), ctx.getAuthor()).queue()
        );
    }

    @Override
    public String getName() { return "remove"; }

    @Override
    public MessageEmbed getHelp(String prefix) {
        return new EmbedBuilder().setTitle("__Command: " + prefix + "remove__")
                .addField("Description: ", "Removes Kirby's active ability.", false)
                .addField("Usage: ", prefix + "remove [ability]", false)
                .build();
    }

    @Override
    public String getDescription() {
        return "Remove Kirby's current ability";
    }

    @Override
    public List<String> getAliases() {
        return List.of("rem", "remability", "removeability");
    }
}
