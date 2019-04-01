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
    	// Log all variables for debugging
    	sender.sendMessage(new TextComponent("token: " + Main.getMCM().getToken()));
    	sender.sendMessage(new TextComponent("jclid: " + Main.getMCM().getJoinLeaveChatId()));
    	sender.sendMessage(new TextComponent("debug: " + Main.getMCM().getDebugEnabled()));
    	
    	// Reconnect DiscordApi if it is null
    	if (Discord.api == null) {
    		new Discord(Main.getMCM().getToken());
    		if (Discord.api != null) {
                Main.inst().setLocalBotOptions();
                Main.inst().getLogger().info("bd reload: token " + Main.getMCM().getToken());
                sender.sendMessage(new TextComponent(InGameCommand.connectedAsUser(true)));
    		}
    	}
    	
    	// reload the debug logger
    	Main.debugLogger = new DebugLogger();
	}
}