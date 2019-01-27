package me.prouser123.bungee.discord.commands.sub;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import me.prouser123.bungee.discord.Discord;

public class InviteSubCommand implements MessageCreateListener {

	@Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessage().getContent().equalsIgnoreCase("!bd invite")) {
            
            event.getChannel().sendMessage("Bot Invite Link: " + Discord.api.createBotInvite());
        }
            
    }
	
}