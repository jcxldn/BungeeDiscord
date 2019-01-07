package me.prouser123.bungee.discord;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import me.prouser123.bungee.discord.Main;

public class MainCommand implements MessageCreateListener {
	
	/**
	 * Listener Command to show server information
	 * Usage: (DiscordApi - e.g. Discord.api) api.addMessageCreateListener(new ServerInfo());
	 */
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        // Check if the message content equals "!copyAvatar"
        if (event.getMessage().getContent().equalsIgnoreCase("!bd")) {
        	
        	EmbedBuilder embed = new EmbedBuilder();
        	
        	embed.setTitle("Commands");
        	embed.addField("!serverinfo", "Show server information");
        	embed.addField("!getOwnerAvatar", "Replace my avatar with the bot owner's  (" + Discord.getBotOwner(event) + ") avatar");
        	
        	event.getChannel().sendMessage(embed);
        	
        	
        	EmbedBuilder embed2 = new EmbedBuilder();
        	
        	if (JoinLeave.channel != null) {
            	embed2.setTitle("Enabled Features");
            	embed2.addField("Join / Leave Messages", "Message to a channel when a player joins the network.");
        	} else {
        		embed2.addField("Disabled Features", "Join / Leave Messages");
        	}
        	
        	event.getChannel().sendMessage(embed2);
        	
        	//EmbedBuilder embed = new EmbedBuilder()
        	//		.setTitle("Features")
        	//		.addField("Join / Leave Messages", jcl + "Message to a channel when a player joins the network.")
            //        .setTitle("Commands")
            //        //.setDescription("Description")
            //		//.setAuthor("BungeeDiscord Commands", "https://github.com/Prouser123/KodiCore", "https://cdn.discordapp.com/embed/avatars/0.png")
            //		.addField("!serverinfo", "Show server information")
            //		.addField("!getOwnerAvatar", "Replace my avatar with the bot owner's  (" + Discord.getBotOwner(event) + ") avatar");
            //    	//.setFooter("Bungee Discord " + Main.inst().getDescription().getVersion().toString() + " | !bd"/*.split("-")[0]*/, "https://cdn.discordapp.com/avatars/215119410103451648/575d90fdda8663b633e36f8b8c06c719.png");
            //    	// Send the embed
            //    event.getChannel().sendMessage(embed);
                
                return;
        }
        return;
    }

}