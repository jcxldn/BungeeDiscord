package me.prouser123.bungee.discord.commands.ingame.sub;

import me.prouser123.bungee.discord.Main;
import me.prouser123.bungee.discord.commands.ingame.InGameCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class Debug {
	
	public static void execute(CommandSender sender, String[] args) {
		// if debug is already enabled, we will disable it.
		if (Main.getMCM().getDebugEnabled()) {
			Main.getMCM().setDebugEnabled(false);
            Main.getMCM().write();
			sender.sendMessage(new TextComponent(InGameCommand.prefix + ChatColor.GREEN + "Disabled debug logging."));
			
		// debug logging is disabled, we will enable it.
		} else {
			Main.getMCM().setDebugEnabled(true);
			Main.getMCM().write();
			sender.sendMessage(new TextComponent(InGameCommand.prefix + ChatColor.GREEN + "Enabled debug logging."));
		}
	}
}