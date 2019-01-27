package me.prouser123.bungee.discord.commands;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import me.prouser123.bungee.discord.Discord;
import me.prouser123.bungee.discord.JoinLeave;

public class MainCommand implements MessageCreateListener {
	
	/**
	 * Listener Command to show server information
	 * Usage: (DiscordApi - e.g. Discord.api) api.addMessageCreateListener(new ServerInfo());
	 */
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        // Check if the message content equals "!bd"
        if (event.getMessage().getContent().equalsIgnoreCase("!bd")) {
        	
        	EmbedBuilder embed = new EmbedBuilder()
        			.setTitle("Commands")
        			.addField("!serverinfo", "Show server information.")
        			.addField("!getOwnerAvatar", "Replace my avatar with the bot owner's  (" + Discord.getBotOwner(event) + ") avatar.")
        			.addField("!players", "Show players currently on the network and their servers.");
        	
        	event.getChannel().sendMessage(embed);
        	
        	
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

}