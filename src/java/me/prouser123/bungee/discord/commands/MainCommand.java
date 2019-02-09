package me.prouser123.bungee.discord.commands;

import java.util.ArrayList;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import me.prouser123.bungee.discord.JoinLeave;
import me.prouser123.bungee.discord.Main;
import me.prouser123.bungee.discord.base.BaseCommand;
import me.prouser123.bungee.discord.base.GenerateOnDemand;

public class MainCommand implements MessageCreateListener, BaseCommand, GenerateOnDemand {
	
	//public static EmbedBuilder CommandEmbed = null;
	public static ArrayList<String> array;
	public static ArrayList<String> subArray;
	
	public MainCommand() {
		array = new ArrayList<String>();
		subArray = new ArrayList<String>();
		
		// Debug information
		Main.inst().getDebugLogger().info("[MainCommand@Init] Loaded MainCommand and Array");
		Main.inst().getDebugLogger().info("[MainCommand@Init] Arr: " + array);
	}
	
	/**
	 * Listener Command to show server information
	 * Usage: (DiscordApi - e.g. Discord.api) api.addMessageCreateListener(new ServerInfo());
	 */
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        // Check if the message content equals "!bd"
        if (event.getMessage().getContent().equalsIgnoreCase("!bd")) {
        	
        	//EmbedBuilder embed = new EmbedBuilder()
        	//	.setTitle("Commands");
        	//
        		//.addField("!serverinfo", "Show server information.")
        		//.addField("!botinfo", "Show bot information.")
        		//.addField("!players", "Show players currently on the network and their servers.")
        		//.addField("!getOwnerAvatar", "Replace my avatar with the bot owner's  (" + Discord.getBotOwner(event) + ") avatar.");
        		
        	// Create and send the Main Command Embed
        	event.getChannel().sendMessage(this.createMainCommandEmbed(event));
        	event.getChannel().sendMessage(this.createSubCommandEmbed(event));
        	
        	
        	EmbedBuilder embed2 = new EmbedBuilder();
        	
        	if (JoinLeave.channel != null) {
            	embed2.setTitle("Enabled Features");
            	embed2.addField("Join / Leave Messages", "Message to a channel when a player joins the network.");
        	} else {
        		embed2.addField("Disabled Features", "Join / Leave Messages");
        	}
        	
        	event.getChannel().sendMessage(embed2);
            return;
        }
    }
    
    private EmbedBuilder createMainCommandEmbed(MessageCreateEvent event) {
    	EmbedBuilder embed = new EmbedBuilder().setTitle("Commands");
    	
    	//Main.inst().getLogger().info(array.toString());
    	for (String command: array) {
    		String[] split = command.split(this.arraySeperator);
    		
    		// Debug information
    		Main.inst().getDebugLogger().info("[MainCommand@OnMessage] Command: " + split[0] + ", HelpText: " + split[1]);
    		
    		// Currently not required as there are no main commands that require it.
    		// Instead we will just add the field.
    		
    		//if (this.isGoD(split[1])) {
    		//	this.runGoD(split, embed, event);
    		//} else {
        	//	embed.addField(split[0], split[1]);
    		//}

        	embed.addField(split[0], split[1]);
    	}
    	
    	// return the embed
    	return embed;
    }
    
    private EmbedBuilder createSubCommandEmbed(MessageCreateEvent event) {
    	EmbedBuilder embed = new EmbedBuilder().setTitle("Sub-Commands");
    	
    	//Main.inst().getLogger().info(array.toString());
    	for (String command: subArray) {
    		String[] split = command.split(this.arraySeperator);
    		
    		// Debug information
    		Main.inst().getDebugLogger().info("[MainCommand@OnMessage] Command: " + split[0] + ", HelpText: " + split[1]);
    		
    		if (this.isGoD(split[1])) {
    			this.runGoD(split, embed, event);
    		} else {
        		embed.addField(split[0], split[1]);
    		}
    	}
    	
    	// return the embed
    	return embed;
    }

}