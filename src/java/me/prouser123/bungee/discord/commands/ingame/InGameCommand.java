package me.prouser123.bungee.discord.commands.ingame;

import java.util.Arrays;

import me.prouser123.bungee.discord.Discord;
import me.prouser123.bungee.discord.Main;
import me.prouser123.bungee.discord.commands.ingame.sub.*;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class InGameCommand extends Command {
	
	// Strings for messages so that they can be edited easily.
	public static String helpMessageText = "Use /bd help for help.";
	public static String invalidCommandText = "Invalid command.";	
	public static String title = (ChatColor.DARK_AQUA + "Bungee" + ChatColor.LIGHT_PURPLE + "Discord");
	public static String prefix = (ChatColor.WHITE + "[" + title + ChatColor.WHITE + "] " + ChatColor.GRAY);
	
	// Constructor
	public InGameCommand() {
		super("bd", "bungeediscord.use");
	}
	
	// Main command method
	@Override
	public void execute(CommandSender sender, String[] args) {
		// bd command
		if ( args.length == 0 ) {
			me.prouser123.bungee.discord.commands.ingame.sub.Main.execute(sender);
            
			
        // bd help command
        } else if (args[0].equalsIgnoreCase("help")) {
        	Help.execute(sender);

            
        // bd save command (testing purposes only)
        } else if (args[0].equalsIgnoreCase("save")) {
        	if (args.length == 1) {
            	Main.getMCM().write();
				sender.sendMessage(new TextComponent(prefix + ChatColor.DARK_GREEN + "Saved!"));
        	} else if (args[1].equalsIgnoreCase("token")) {
        		Main.getMCM().setToken(args[2]);
        	} else if (args[1].equalsIgnoreCase("jlcid")) {
        		Main.getMCM().setJoinLeaveChatId(args[2]);
        	} else if (args[1].equalsIgnoreCase("debug")) {
        		Main.getMCM().setDebugEnabled(Boolean.parseBoolean(args[2]));
        	}
        	
        	
        // bd reload command
        } else if (args[0].equalsIgnoreCase("reload")) {
        	Reload.execute(sender);
        	
        	
        // bd invite command
        } else if (args[0].equalsIgnoreCase("invite")) {
        	Invite.execute(sender);
        	
        	
        // bd token command
        } else if (args[0].equalsIgnoreCase("token")) {
        	Token.execute(sender, args);
        	
        	
        // bd debug command
        } else if (args[0].equalsIgnoreCase("debug")) {
        	Debug.execute(sender);
        	
        	
        // Show generic usage help command
        } else {
            invalidMessage(sender);
            sender.sendMessage(new TextComponent("args: " +  Arrays.toString(args)));
        }
	}
	
	// Other methods to keep them in one place
	public static void helpMessage(CommandSender sender) {
	    sender.sendMessage(new TextComponent(ChatColor.RED + helpMessageText));
	}
		
	public static void invalidMessage(CommandSender sender) {
	    sender.sendMessage(new TextComponent(ChatColor.RED + invalidCommandText + " " + helpMessageText));
	}
		
	public static String connectedAsUser(boolean showPrefix) {
		String output = "";
		Main.inst().getLogger().info(Boolean.toString(showPrefix));
		if (showPrefix) output += prefix;
		try {
			output += ChatColor.DARK_GREEN + "Connected as " + Discord.api.getAccountType().toString().toLowerCase() +  " user: " + ChatColor.GRAY + Discord.api.getYourself().getName();
		} catch (NullPointerException ex) {
			output += ChatColor.DARK_GREEN + "Not connected!";
		}
		return output;
	}
}