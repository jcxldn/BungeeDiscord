package me.prouser123.bungee.discord.commands.ingame.sub;

import me.prouser123.bungee.discord.Discord;
import me.prouser123.bungee.discord.Main;
import me.prouser123.bungee.discord.commands.ingame.InGameCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class Token {
	
	public static void execute(CommandSender sender, String[] args) {

    	// 2 command arguments (e.g. /bd token <token>)
    	if (args.length == 2) {
    		sender.sendMessage(new TextComponent("token: " + args[1]));
    		
            new Discord(args[1]);
            if (Discord.api != null) {
                Main.inst().setLocalBotOptions();
                Main.inst().getLogger().info(Main.getMCM().getToken());
                
                // Attempt to set the new token and save the file
                Main.getMCM().setToken(args[1]);
                Main.getMCM().write();
				sender.sendMessage(new TextComponent(InGameCommand.prefix + ChatColor.DARK_GREEN + "Saved to config."));

                Main.inst().getLogger().info(Main.getMCM().getToken());
                sender.sendMessage(new TextComponent(InGameCommand.connectedAsUser(true)));
            } else {
            	sender.sendMessage(new TextComponent(InGameCommand.prefix + ChatColor.RED + "Invalid token! Please try another token. This has not been saved to the config."));
            	sender.sendMessage(new TextComponent(InGameCommand.prefix + ChatColor.RED + "If you had a working token before running this command, use /bd reload to use the old token"));
            }
            
        // User only put /bd token (1 command argument)
    	} else {
            sender.sendMessage(new TextComponent(ChatColor.RED + "Please specify a bot token with: /bd token <bot token>"));
            InGameCommand.helpMessage(sender);
    	}
	}
}