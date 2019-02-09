package me.prouser123.bungee.discord.commands.sub;

import org.javacord.api.Javacord;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import me.prouser123.bungee.discord.Constants;
import me.prouser123.bungee.discord.Discord;
import me.prouser123.bungee.discord.Main;
import me.prouser123.bungee.discord.base.BaseCommand.base;
import me.prouser123.bungee.discord.base.BaseSubCommand;

public class Debug implements MessageCreateListener, BaseSubCommand {
	
	private base base;

	public Debug(int piority, String command, String helpText) {
		base = this.easyBaseSetup(piority, command, helpText);
	}

	@Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessage().getContent().equalsIgnoreCase(base.command)) {
            
        	EmbedBuilder embed = new EmbedBuilder()
            		.setAuthor("BungeeDiscord Debug Information", Constants.url, Constants.authorIconURL)
            		.setDescription("Detailed information about the plugin and dependencies. Useful for debugging but not normally required.")
            		.addInlineField("JavaCord Version", Javacord.VERSION)
            		.addInlineField("JavaCord API/Gateway Version", Javacord.DISCORD_API_VERSION + "/" + Javacord.DISCORD_GATEWAY_VERSION)
            		.addInlineField("JavaCord User Agent", Javacord.USER_AGENT)
            		.addInlineField("JavaCord Commit ID", Javacord.COMMIT_ID)
            		.addInlineField("JavaCord Build Timestamp", Javacord.BUILD_TIMESTAMP.toString())
            		.addInlineField("Registered Listeners", Integer.toString(Discord.api.getListeners().size()));
                
            // Set footer
            Discord.setFooter(embed);
                
            // Send the embed
            event.getChannel().sendMessage(embed);
            return;
        }
            
    }
	
}