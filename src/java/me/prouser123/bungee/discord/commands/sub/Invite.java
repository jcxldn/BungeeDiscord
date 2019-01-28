package me.prouser123.bungee.discord.commands.sub;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import me.prouser123.bungee.discord.Discord;
import me.prouser123.bungee.discord.Main;
import me.prouser123.bungee.discord.base.BaseSubCommand;

public class Invite implements MessageCreateListener, BaseSubCommand {
	
	private base base;

	public Invite(int piority, String command, String helpText) {

		base = this.createBase();

		Main.inst().getLogger().info("[Invite@Init] " + piority + " | " + command + " | " + helpText);
		base.command = command;
		base.helpPriority = piority;
		base.helpText = helpText;
		Main.inst().getLogger().info("[Invite@Init] BASE() | " + base.helpPriority + " | " + base.command + " | " + base.helpText);
		this.addCommandToHelp(base);
	}

	@Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessage().getContent().equalsIgnoreCase(base.command)) {
            
            event.getChannel().sendMessage("Bot Invite Link: " + Discord.api.createBotInvite());
        }
            
    }
	
}