package me.prouser123.bungee.discord.commands.ingame.sub;

import me.prouser123.bungee.discord.Discord;
import me.prouser123.bungee.discord.commands.ingame.InGameCommand;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Invite {
	
	public static void execute(CommandSender sender) {
    	if (sender instanceof ProxiedPlayer) {
        	TextComponent message = new TextComponent(InGameCommand.prefix + "Invite Link (click me!)");
        	message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, Discord.api.createBotInvite()));
        	message.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Invite your bot to your server!").create() ) );
        	sender.sendMessage(message);
    	} else {
        	sender.sendMessage(new TextComponent(InGameCommand.prefix + "Invite Link: " + Discord.api.createBotInvite()));
    	}
	}
}