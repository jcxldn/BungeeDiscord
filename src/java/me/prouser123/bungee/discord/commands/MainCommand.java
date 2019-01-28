package me.prouser123.bungee.discord.commands;

import java.util.ArrayList;
import java.util.Arrays;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import me.prouser123.bungee.discord.Discord;
import me.prouser123.bungee.discord.JoinLeave;
import me.prouser123.bungee.discord.Main;
import me.prouser123.bungee.discord.base.BaseCommand;

public class MainCommand implements MessageCreateListener, BaseCommand {
	
	//public static EmbedBuilder CommandEmbed = null;
	public static ArrayList<String> array;
	public static ArrayList<String> subArray;
	
	public MainCommand() {
		array = new ArrayList<String>();
		subArray = new ArrayList<String>();
		Main.inst().getLogger().info("[MainCommand@Init] Loaded MainCommand and Array");
		Main.inst().getLogger().info("[MainCommand@Init] Arr: " + array);
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
    		Main.inst().getLogger().info("[MainCommand@OnMessage] Command: " + split[0] + ", HelpText: " + split[1]);
    		
    		if (split[1].startsWith(this.generateOnDemandPrefix)) {
    			String[] splitGOD = split[1].split(this.generateOnDemandPrefix);
    			Main.inst().getLogger().info("[MainCommand@OnMessage.GoD] " + Arrays.toString(splitGOD) + "ITEM1: " + splitGOD[1]);
    			
    			if (splitGOD[1].equalsIgnoreCase("copyOwnerAvatar")) {
    				Main.inst().getLogger().info("[MainCommand@OnMessage.GoD.copyOwnerAvatar]");
    				embed.addField(split[0], "Replace my avatar with the bot owner's  (" + Discord.getBotOwner(event) + ") avatar.");
    				//return;
    			}
    		} else {
        		embed.addField(split[0], split[1]);
    		}
    	}
    	
    	// return the embed
    	return embed;
    }
    
    private EmbedBuilder createSubCommandEmbed(MessageCreateEvent event) {
    	EmbedBuilder embed = new EmbedBuilder().setTitle("Sub-Commands");
    	
    	//Main.inst().getLogger().info(array.toString());
    	for (String command: subArray) {
    		String[] split = command.split(this.arraySeperator);
    		Main.inst().getLogger().info("[MainCommand@OnMessage] Command: " + split[0] + ", HelpText: " + split[1]);
    		
    		if (split[1].startsWith(this.generateOnDemandPrefix)) {
    			String[] splitGOD = split[1].split(this.generateOnDemandPrefix);
    			Main.inst().getLogger().info("[MainCommand@OnMessage.GoD] " + Arrays.toString(splitGOD) + "ITEM1: " + splitGOD[1]);
    			
    			if (splitGOD[1].equalsIgnoreCase("copyOwnerAvatar")) {
    				Main.inst().getLogger().info("[MainCommand@OnMessage.GoD.copyOwnerAvatar]");
    				embed.addField(split[0], "Replace my avatar with the bot owner's  (" + Discord.getBotOwner(event) + ") avatar.");
    				//return;
    			}
    		} else {
        		embed.addField(split[0], split[1]);
    		}
    	}
    	
    	// return the embed
    	return embed;
    }

}