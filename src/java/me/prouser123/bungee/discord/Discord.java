package me.prouser123.bungee.discord;

import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

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
		// Disconnect from the current instance if applicable, and set the instance to null
		if (api != null) {
			api.disconnect();
			api = null;
		}
		
		Discord.token = token;
		
		// Create an Instance of the DiscordApi
		try {
			api = new DiscordApiBuilder().setToken(token).login().join();
		} catch (CompletionException IllegalStateException) {
			Main.inst().getLogger().warning("Connection Error. Did you put a valid token in the config?");
			
			// Return without running the rest of the setup.
			return;
		}
		
        // Print the invite url of the bot
        Main.inst().getLogger().info("Bot Invite Link: " + api.createBotInvite());
        
        // Set Activity
        setStatus();
        
        // Create server join Listeners
        api.addServerJoinListener(event -> Main.inst().getLogger().info("Joined Server: " + event.getServer().getName()));
        api.addServerLeaveListener(event -> Main.inst().getLogger().info("Left Server: " + event.getServer().getName()));
        
        // Add Reconnect Listener to re-add status
        api.addReconnectListener(event -> {
        	Main.inst().getLogger().info(("Reconnected to Discord."));
        	setStatus();
        });
        
        api.addResumeListener(event -> {
        	Main.inst().getLogger().info(("Resumed connection to Discord."));
        	setStatus();
        });
	}
	
	public static String getBotOwner(MessageCreateEvent event) {
    	String bot_owner = "<@";
    	try {
			bot_owner += Long.toString(event.getApi().getApplicationInfo().get().getOwnerId());
			bot_owner += ">";
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return bot_owner;
	}
	
	// Sets the footer, done here to keep it standardised.
	public static void setFooter(EmbedBuilder embed) {
		// Add first part of footer
		String info = "Bungee Discord " + Main.inst().getDescription().getVersion().toString();
		
		// Add update text if there is an update available
		if (UpdateChecker.isUpdateAvailable) {
			info += " (update available!)";
		}
		
		embed.setFooter(info + " | !bd", Constants.footerIconURL);
	}
	
	public static boolean isConnected() {
		return (Discord.api != null);
	}
	
	public static void setStatus() {
		String status = Main.getMCM().getStatus();
		status = status.replace("$prefix", "!bd");
		api.updateActivity(status);
	}
}