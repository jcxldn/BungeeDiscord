package me.prouser123.bungee.discord.commands.ingame.sub;

import me.prouser123.bungee.discord.Main;
import me.prouser123.bungee.discord.commands.ingame.InGameCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class JLCID {
	
	public static void execute(CommandSender sender, String[] args) {

    	// 2 command arguments (e.g. /bd jlcid <id>)
    	if (args.length == 2) {
    		sender.sendMessage(new TextComponent("jlcid: " + args[1]));
    		
    		// if user put /bd jlcid off
    		if (args[1].equalsIgnoreCase("off")) {
    			// set the channel ID to the default
    			Main.getMCM().setJoinLeaveChatId("123456789");
        		sender.sendMessage(new TextComponent("argOff"));
    		} else {
    			Main.getMCM().setJoinLeaveChatId(args[1]);
    		}
    		
    		Main.getMCM().write();
    		Main.registerListeners.playerJoinLeave();
    		sender.sendMessage(new TextComponent("writeRes: " + Main.registerListeners.jlcidEnabled.toString()));
    		
    		if (Main.registerListeners.jlcidEnabled) {
            	sender.sendMessage(new TextComponent(InGameCommand.jlcEnabledForChannel()));
    		} else {
    			if (args[1].equalsIgnoreCase("off")) {
    				sender.sendMessage(new TextComponent(InGameCommand.prefix + ChatColor.RED + "Join Leave Chat disabled."));
    			} else {
    				sender.sendMessage(new TextComponent(InGameCommand.prefix + ChatColor.RED + "Join Leave Chat disabled. Did you put a valid channel id?"));
    			}
    		}
            
        // User only put /bd jlcid (1 command argument)
    	} else {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Please specify a channel id with: /bd jlcid <id>, or type /bd jlcid off to disable."));
            InGameCommand.helpMessage(sender);
    	}
	}
}