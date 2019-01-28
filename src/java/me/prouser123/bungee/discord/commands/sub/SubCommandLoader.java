package me.prouser123.bungee.discord.commands.sub;

import org.javacord.api.DiscordApi;

import me.prouser123.bungee.discord.Main;

public class SubCommandLoader {
	
	public SubCommandLoader(DiscordApi api) {
		Main.inst().getLogger().info("Registering sub-commands...");
		api.addMessageCreateListener(new Invite(0, "!bd invite", "Show a bot invite link to add the bot to other servers."));
		api.addMessageCreateListener(new Debug(1, "!bd debug", "Show debug information."));
	}
}