package me.prouser123.bungee.discord.commands.ingame.sub;

import java.util.ArrayList;

import org.javacord.api.entity.server.Server;

import me.prouser123.bungee.discord.Discord;
import me.prouser123.bungee.discord.UpdateChecker;
import me.prouser123.bungee.discord.commands.ingame.InGameCommand;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class Main {
	
	public static void execute(CommandSender sender) {

        sender.sendMessage(new TextComponent(InGameCommand.title + " " + ChatColor.GRAY + me.prouser123.bungee.discord.Main.inst().getDescription().getVersion()));

        if (UpdateChecker.isUpdateAvailable) {
        	sender.sendMessage(new TextComponent(ChatColor.GOLD + "There is a new update available: v" + UpdateChecker.availableVersion));
        }
        
        sender.sendMessage(new TextComponent(InGameCommand.connectedAsUser(false) + " (Playing " + ChatColor.DARK_AQUA + Discord.getStatus() + ChatColor.GRAY + ")"));
        
        if (Discord.isConnected()) {
        	// Create and print a nicely formatted list of the names of discord servers the bot is in
            ArrayList<String> servers = new ArrayList<String>();
            for (Server s : Discord.api.getServers()) {
            	servers.add(s.getName());
            }
            
            String suffix = (servers.size() == 1)?  " server: ": " servers: ";
            sender.sendMessage(new TextComponent(ChatColor.GRAY + "Connected to " + Integer.toString(servers.size()) + suffix + servers.toString().replace("[","").replace("]","")));
            
            // Add info about join leave chat if it is enabled
            if (me.prouser123.bungee.discord.Main.registerListeners.jlcidEnabled) {
            	sender.sendMessage(new TextComponent(InGameCommand.jlcEnabledForChannel()));
    		} else {
            	sender.sendMessage(new TextComponent(ChatColor.RED + "Join Leave Chat not enabled. To enable, edit the config file or type /bd jlcid"));
            }
            
        } else {
        	sender.sendMessage(new TextComponent(ChatColor.RED + "Please use /bd token <token> or the config file to enter your bot token." ));
        	sender.sendMessage(new TextComponent(ChatColor.RED + "If you edit the config file, please either use /bd reload or restart the server." ));
        }
	}
}