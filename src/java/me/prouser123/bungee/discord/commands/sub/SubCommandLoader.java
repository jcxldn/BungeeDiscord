package me.prouser123.bungee.discord.commands.sub;

import org.javacord.api.DiscordApi;

import me.prouser123.bungee.discord.Main;

public class SubCommandLoader {
	
	public SubCommandLoader(DiscordApi api) {
		Main.inst().getLogger().info("Registering sub-commands...");
		api.addMessageCreateListener(new Debug());
		api.addMessageCreateListener(new Invite());
	}
}