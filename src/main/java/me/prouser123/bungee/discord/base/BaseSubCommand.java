package me.prouser123.bungee.discord.base;

import me.prouser123.bungee.discord.Main;
import me.prouser123.bungee.discord.commands.bot.MainCommand;

public interface BaseSubCommand extends BaseCommand {
	
	default void addCommandToHelp(base b) {
		Main.inst().getDebugLogger().info("[BaseSubCommand@Add2Help] Adding " + b.command);
		MainCommand.subArray.add(b.helpPriority, b.command + arraySeperator + b.helpText);
		Main.inst().getDebugLogger().info("[BaseSubCommand@Add2Help] " + MainCommand.subArray);
	}
}