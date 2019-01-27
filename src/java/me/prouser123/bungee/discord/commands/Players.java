package me.prouser123.bungee.discord.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import me.prouser123.bungee.discord.Main;
import me.prouser123.bungee.discord.base.BaseCommand;
import me.prouser123.bungee.discord.base.BaseCommand.base;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Players implements MessageCreateListener, BaseCommand {

	private base base;
	
	public Players(int piority, String command, String helpText) {
		
		base = this.createBase();
		
		Main.inst().getLogger().info("[Players@Init] " + piority + " | " + command + " | " + helpText);
		base.command = command;
		//this.base().helpPriority = piority;
		base.helpText = helpText;
		Main.inst().getLogger().info("[Players@Init] BASE() | " + base.helpPriority + " | " + base.command + " | " + base.helpText);
		this.addCommandToHelp(base);
	}
	
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessage().getContent().equalsIgnoreCase("!players")) {
        	
        	EmbedBuilder embed2 = new EmbedBuilder()
        			.setTitle("Online Players")
                    .setDescription("All players currently online on the network.")
                	.setFooter("Bungee Discord " + Main.inst().getDescription().getVersion().toString() + " | !bd"/*.split("-")[0]*/, "https://cdn.discordapp.com/avatars/215119410103451648/575d90fdda8663b633e36f8b8c06c719.png");
        	
        	// Create an array of players and their servers
            List<String> players = new ArrayList<>();
            
            for ( ProxiedPlayer player : Main.inst().getProxy().getPlayers() )
            {
                players.add(player.getDisplayName() + " at " + player.getServer().getInfo().getName());
            }
            Collections.sort( players, String.CASE_INSENSITIVE_ORDER );
            
            if (players.size() == 0) {
            	embed2.setDescription("There are no players online.");
            }
        	
        	// Create a field in the embed for every player online
            for(String player : players) {
            	String[] playerArray = player.split(" at ");
            	// Player name is item 0, Player server is item 1
            	embed2.addField(playerArray[0], "online at " + playerArray[1]);
            }
        	
        	// Send the embed
            event.getChannel().sendMessage(embed2);
            return;
        }
    }

}