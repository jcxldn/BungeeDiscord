package me.prouser123.bungee.discord.commands.ingame.sub;

import me.prouser123.bungee.discord.commands.ingame.InGameCommand;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class Help {
	
	public static void execute(CommandSender sender) {
        sender.sendMessage(new TextComponent(InGameCommand.title + ChatColor.GRAY + " Commands"));
        sender.sendMessage(new TextComponent(ChatColor.DARK_GREEN + "/bd" + ChatColor.GRAY + " - " + ChatColor.GOLD + "View plugin and status information"));
        sender.sendMessage(new TextComponent(ChatColor.DARK_GREEN + "/bd help" + ChatColor.GRAY + " - " + ChatColor.GOLD + "This page."));
        sender.sendMessage(new TextComponent(ChatColor.DARK_GREEN + "/bd reload" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Reload from the config file."));
        sender.sendMessage(new TextComponent(ChatColor.DARK_GREEN + "/bd invite" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Get the bot's invite link."));
        sender.sendMessage(new TextComponent(ChatColor.DARK_GREEN + "/bd token" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Set the bot token."));
        sender.sendMessage(new TextComponent(ChatColor.DARK_GREEN + "/bd debug" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Toggle debug logging."));
	}
}