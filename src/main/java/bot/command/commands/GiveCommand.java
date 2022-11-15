package bot.command.commands;

import bot.Config;
import bot.command.CommandContext;
import bot.command.ICommand;
import bot.command.game.GameManager;
import bot.command.game.LevelManager;
import bot.command.game.TutorialManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class GiveCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();
        String prefix = Config.get("prefix");

        if (args.isEmpty()) {
            channel.sendMessageEmbeds(new EmbedBuilder()
                    .setTitle("__Command: " + prefix + "give__")
                    .addField("Description: ", "Gives Kirby the specified ability.", false)
                    .addField("Usage: ", prefix + "give [ability]", false)
                    .build()
            ).queue();

            return;
        }

        String id = ctx.getAuthor().getId();

        if (GameManager.notRunning(id)) {
            channel.sendMessage("You must start the game before adding abilities.").queue();
            return;
        }

        StringBuilder ability = new StringBuilder();
        for (int i = 0; i < args.size(); i++) {
            ability.append(args.get(i));
            if (i != args.size() - 1) { ability.append(" "); }
        }

        // handle tutorial levels
        if (GameManager.isTutorial(id)) {
            if (TutorialManager.notValid(id, ability.toString().toLowerCase())) {
                channel.sendMessage("The ability **" + ability + "** is not available for this level." +
                        "\nPick an ability the bot's reacted with to use an ability.").queue();
                return;
            }

            channel.sendMessageEmbeds(TutorialManager.addAbility(id, ability.toString(), channel)).queue(
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

            return;
        }

        // handle standard levels
        if (LevelManager.notValid(id, ability.toString())) {
            channel.sendMessage("The ability **" + ability + "** is not available for this level." +
                    "\nPick an ability the bot's reacted with to use an ability.").queue();
            return;
        }

        MessageEmbed embed = GameManager.addAbility(id, ability.toString(), channel);
        if (embed == null) { return; }

        channel.sendMessageEmbeds(embed).queue(
                message -> {
                    if (LevelManager.notWon(id)) {
                        GameManager.setLevelMessageID(id, message.getId());
                        LevelManager.addReactions(id, message);
                        channel.sendMessage(GameManager.getNumAbilities(id)).queue();
                    }
                }
        );
    }

    @Override
    public String getName() { return "give"; }

    @Override
    public MessageEmbed getHelp(String prefix) {
        return new EmbedBuilder()
                .setTitle("__Command: " + prefix + "give__")
                .addField("Description: ", "Gives Kirby the specified ability.", false)
                .addField("Usage: ", prefix + "give [ability]", false)
                .build();
    }

    @Override
    public String getDescription() { return "Give Kirby a new ability"; }

    @Override
    public List<String> getAliases() { return List.of("g", "giveability", "add", "addability"); }
}
