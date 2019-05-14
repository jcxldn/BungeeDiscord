package me.prouser123.bungee.discord.base;

import me.prouser123.bungee.discord.Main;
import me.prouser123.bungee.discord.commands.bot.MainCommand;

public interface BaseCommand {
	
	final String arraySeperator = ":";
	
	default base createBase() {
		return new base();
	}
	
	// setup and return base with only one command
	default base easyBaseSetup(int piority, String command, String helpText) {
		base b = this.createBase();
		
		b.add(piority, command, helpText);
		addCommandToHelp(b);
		
		return b;
	}
		
	public class base {
		public String command = "";
		public String helpText = "";
		public int helpPriority = 0;
		
		// Method to add variables from class init to base
		public void add(int piority, String command, String helpText) {
			this.helpPriority = piority;
			this.command = command;
			this.helpText = helpText;
		}
	}
	
	default void addCommandToHelp(base b) {
		Main.inst().getDebugLogger().info("[BaseCommand@Add2Help] Adding command '" + b.command + "' with piority " + b.helpPriority);
		MainCommand.array.add(b.helpPriority, b.command + arraySeperator + b.helpText);
	}
}