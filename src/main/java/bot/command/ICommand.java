package bot.command;

import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.List;

public interface ICommand {
    void handle(CommandContext ctx);

    String getName();

    MessageEmbed getHelp(String prefix);

    String getDescription();

    default List<String> getAliases() {
        return List.of();
    }
}
