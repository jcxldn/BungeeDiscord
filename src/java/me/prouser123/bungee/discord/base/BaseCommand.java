package me.prouser123.bungee.discord.base;

import me.prouser123.bungee.discord.Main;
import me.prouser123.bungee.discord.commands.MainCommand;

public interface BaseCommand {
	
	default base createBase() {
		return new base();
	}
		
	public class base {
		public String command = "";
		public String helpText = "";
		public int helpPriority = 0;
	}
	
	final String arraySeperator = ":";
	
	default void addCommandToHelp(base b) {
		Main.inst().getLogger().info("[BaseCommand@Add2Help]" + b.command);
		MainCommand.array.add(b.helpPriority, b.command + arraySeperator + b.helpText);
		Main.inst().getLogger().info("[BaseCommand@Add2Help] " + MainCommand.array);
	}
}