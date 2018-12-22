package me.prouser123.bungee.discord;

import org.javacord.api.DiscordApiBuilder;

import java.util.concurrent.CompletionException;

import org.javacord.api.DiscordApi;

import me.prouser123.bungee.discord.Main;

public class Discord {
	
	public static String token = null; // Bot token
	
	/**
	 * Discord API Instance
	 */
	public static DiscordApi api = null; // Api Instance
	
	/**
	 * Class
	 * @param token bot token
	 */
	public Discord(String token) {
		Discord.token = token;
		
		// Create an Instance of the DiscordApi
		try {
			api = new DiscordApiBuilder().setToken(token).login().join();
		} catch (CompletionException IllegalStateException) {
			Main.inst().getLogger().info("Connection Error. Did you put a valid token in the config?");
		}
		
        // Print the invite url of the bot
        Main.inst().getLogger().info("Bot Invite Link: " + api.createBotInvite());
        
        // Create server join Listeners
        api.addServerJoinListener(event -> Main.inst().getLogger().info("Joined Server: " + event.getServer().getName()));
        api.addServerLeaveListener(event -> Main.inst().getLogger().info("Left Server: " + event.getServer().getName()));
	}
}