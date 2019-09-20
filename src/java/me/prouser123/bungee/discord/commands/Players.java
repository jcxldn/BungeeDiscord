package me.prouser123.bungee.discord.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import me.prouser123.bungee.discord.Constants;
import me.prouser123.bungee.discord.Main;
import me.prouser123.bungee.discord.base.BaseCommand;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Players implements MessageCreateListener, BaseCommand {

	private base base;
	
	public Players(int piority, String command, String helpText) {
		base = this.easyBaseSetup(piority, command, helpText);
	}
	
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessage().getContent().equalsIgnoreCase(base.command)) {
        	
        	EmbedBuilder embed2 = new EmbedBuilder()
        			.setTitle("Online Players")
                    .setDescription("All players currently online on the network.")
                	.setFooter("Bungee Discord " + Main.inst().getDescription().getVersion().toString() + " | !bd", Constants.footerIconURL);
        	
        	// Make sure some players are online first
        	if (Main.inst().getProxy().getPlayers().size() == 0) {
        		// If not, just send this message.
            	embed2.setDescription("There are no players online.");
                event.getChannel().sendMessage(embed2);
                return;
            	
            }
        	
        	// Create an array of servers and their players
            Map<String, List<String>> map =  new HashMap<String, List<String>>();
            
            // Create a list for every server
            for(ServerInfo server : Main.inst().getProxy().getServers().values()) {
            	
            	// Add the new list to the HashMap
                map.put(server.getName(), new ArrayList<>());
            }
            
            // Add the players to the map
            for ( ProxiedPlayer player : Main.inst().getProxy().getPlayers() )
            {
                map.get(player.getServer().getInfo().getName()).add(player.getDisplayName());
            }
            
        	// Create a field in the embed for every player online
            for(Map.Entry<String, List<String>> entry : map.entrySet()) {
                embed2.addField(entry.getKey(), entry.getValue().toString().replace("[", "").replace("]", ""));
            }
        	
        	// Send the embed
            event.getChannel().sendMessage(embed2);
            return;
        }
    }

}