package me.prouser123.bungee.discord.commands.ingame.sub;

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
        
        sender.sendMessage(new TextComponent(InGameCommand.connectedAsUser(false)));
        
        if (Discord.isConnected()) {
            Integer servers = Discord.api.getServers().size();
            String suffix = (servers == 1)?  " server.": " servers.";
            sender.sendMessage(new TextComponent("Connected to " + Integer.toString(servers) + suffix));
            sender.sendMessage(new TextComponent(Discord.api.getServers().toString()));
            
        } else {
        	sender.sendMessage(new TextComponent(ChatColor.RED + "Please use /bd token <token> or the config file to enter your bot token." ));
        	sender.sendMessage(new TextComponent(ChatColor.RED + "If you edit the config file, please either use /bd reload or restart the server." ));
        }
	}
}