package me.prouser123.bungee.discord.commands.sub;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;


import org.javacord.api.Javacord;

import me.prouser123.bungee.discord.Discord;
import me.prouser123.bungee.discord.Main;
import me.prouser123.bungee.discord.base.BaseSubCommand;
import me.prouser123.bungee.discord.Constants;

public class BotInfo implements MessageCreateListener, BaseSubCommand {
	
	private base base;
	
	public BotInfo(int piority, String command, String helpText) {
		base = this.easyBaseSetup(piority, command, helpText);
	}
	
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessage().getContent().equalsIgnoreCase(base.command)) {
        	
        	Object[] currentserverroles = Discord.api.getYourself().getRoles(event.getServer().get()).toArray();
        	String roles = "";
        	for (Object roleObject : currentserverroles) {
        		String role = roleObject.toString().split(", name: ")[1].split(", server:")[0];
        		
        		// Remove the initial @ from the role name to avoid role spam
        		if (role.startsWith(("@"))) {
        			Main.inst().getDebugLogger().info("StartsWith @ - Removing...");
        			role = role.replace("@", "");
        		}
        		
        		// Adds to the list of roles
        		roles += role + " ";
        		Main.inst().getDebugLogger().info("Has role: " + role);
        	}
        	
        	EmbedBuilder embed = new EmbedBuilder()
        		.setAuthor("BungeeDiscord Bot Information", Constants.url, Constants.authorIconURL)
        		.addInlineField("BungeeDiscord Version", Main.inst().getDescription().getVersion().toString())
            	.addInlineField("JavaCord Version", Javacord.VERSION + " (API v" + Javacord.DISCORD_API_VERSION + ")")
            	.addInlineField("Bot Servers", Long.toString(Discord.api.getServers().size()))
            	.addInlineField("User Type", Discord.api.getAccountType().toString())
            	.addInlineField("Roles (Current Server)", roles);
            
        	// Set footer
        	Discord.setFooter(embed);
            
        	// Send the embed
            event.getChannel().sendMessage(embed);
            return;
        }
    }

}