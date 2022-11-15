package bot.command.commands;

import bot.CommandManager;
import bot.Config;
import bot.command.CommandContext;
import bot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class HelpCommand implements ICommand {
    private final CommandManager manager;

    public HelpCommand (CommandManager manager) { this.manager = manager; }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();
        String prefix = Config.get("prefix");

        if (args.isEmpty()) {
            EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("__List of Commands__");

            for(ICommand command : manager.getCommands()) {
                embedBuilder.addField(prefix + command.getName(), command.getDescription(), false);
            }

            embedBuilder.setFooter("For more information on each command use " + prefix + "help [command]");

            channel.sendMessageEmbeds(embedBuilder.build()).queue();

            return;
        }

        String search = args.get(0);
        ICommand command = manager.getCommand(search);

        if (command == null) {
            channel.sendMessageEmbeds(new EmbedBuilder().setAuthor("Command " + search + " does not exist.").build()).queue();
            return;
        }

        channel.sendMessageEmbeds(command.getHelp(prefix)).queue();
    }

    @Override
    public String getName() { return "help"; }

    @Override
    public MessageEmbed getHelp(String prefix) {
        return new EmbedBuilder()
                .setTitle("__Command: " + prefix + "help__")
                .addField("Description: ", "Gives details on what a command does.", false)
                .addField("Usage: ", prefix + "help [command]", false)
                .build();
    }

    @Override
    public String getDescription() { return "Help command"; }

    @Override
    public List<String> getAliases() { return List.of("h"); }
}
