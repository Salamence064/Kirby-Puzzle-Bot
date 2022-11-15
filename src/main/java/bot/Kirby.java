package bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Kirby {
    public static void main (String[] args) throws LoginException {
        JDABuilder
                .createDefault(Config.get("token"))
                // disable unnecessary intents
                .disableIntents(GatewayIntent.GUILD_INVITES, GatewayIntent.GUILD_MESSAGE_TYPING, GatewayIntent.DIRECT_MESSAGE_TYPING)
                // specify gateway intents
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_BANS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_MESSAGE_REACTIONS,
                        GatewayIntent.DIRECT_MESSAGES, GatewayIntent.DIRECT_MESSAGE_REACTIONS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_PRESENCES)
                .disableCache(CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS)
                .enableCache(CacheFlag.EMOTE)
                .addEventListeners(new Listener())
                .setActivity(Activity.playing("-help"))
                .build();
    }
}
