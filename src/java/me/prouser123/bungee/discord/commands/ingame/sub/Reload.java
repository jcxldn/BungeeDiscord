package me.prouser123.bungee.discord.commands.ingame.sub;

import me.prouser123.bungee.discord.DebugLogger;
import me.prouser123.bungee.discord.Discord;
import me.prouser123.bungee.discord.Main;
import me.prouser123.bungee.discord.commands.ingame.InGameCommand;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class Reload {
	
	public static void execute(CommandSender sender) {
    	Main.getMCM().load();
    	sender.sendMessage(new TextComponent(InGameCommand.prefix + ChatColor.DARK_GREEN + "Reloaded from config."));
    	
    	// Reconnect DiscordApi if it is null
    	if (Discord.api == null) {
    		new Discord(Main.getMCM().getToken());
    		if (Discord.api != null) {
                Main.inst().setLocalBotOptions();
                sender.sendMessage(new TextComponent(InGameCommand.connectedAsUser(true)));
    		}
    	}

    	// reload the debug logger
    	Main.debugLogger = new DebugLogger();

    	if (Discord.api != null) {
    		// Update status
    		Discord.setStatus();
    		
    		// reload player join leave
    		Main.registerListeners.playerJoinLeave();

    		// Message about join leave status
    		if (Main.registerListeners.jlcidEnabled) {
            	sender.sendMessage(new TextComponent(InGameCommand.jlcEnabledForChannel()));
    		} else {
    			sender.sendMessage(new TextComponent(InGameCommand.prefix + ChatColor.RED + "Join Leave Chat disabled. Did you put a valid channel ID in the config?"));
    		}
    	}
	}
}