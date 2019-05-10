package me.prouser123.bungee.discord.commands.bot.sub;

import java.util.Collection;

import org.javacord.api.Javacord;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import me.prouser123.bungee.discord.Constants;
import me.prouser123.bungee.discord.Discord;
import me.prouser123.bungee.discord.Main;
import me.prouser123.bungee.discord.base.BaseSubCommand;
import net.md_5.bungee.api.plugin.Plugin;

public class Plugins implements MessageCreateListener, BaseSubCommand {
	
	private base base;

	public Plugins(int piority, String command, String helpText) {
		base = this.easyBaseSetup(piority, command, helpText);
	}

	@Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessage().getContent().equalsIgnoreCase(base.command)) {
        	
        	// Check if the message author is a server admin
            if (!event.getMessage().getAuthor().isServerAdmin()) {
                event.getChannel().sendMessage("You are not allowed to use this command!");
                return;
            }
        	
        	Integer moduleCount = 0;
        	
            // Get a collection of plugins
        	Collection<Plugin> plugins = Main.inst().getProxy().getPluginManager().getPlugins();
        	
        	// Create the initial embed
        	EmbedBuilder embed = new EmbedBuilder().setAuthor("BungeeCord Plugins", Constants.url, Constants.authorIconURL);
        	
        	for (Plugin plugin: plugins) {
        		
            	// Hide modules
            	if (plugin.getDescription().getAuthor().equals("SpigotMC")) {
            		// Plugin is likely a module
            		moduleCount += 1;
            	} else {
            		// Plugin is not a module
            		embed.addField(plugin.getDescription().getName(), plugin.getDescription().getVersion() + " by " + plugin.getDescription().getAuthor());
            	}
        	}
        	
        	// Create and set the description
        	embed.setDescription("Detected " + plugins.size() + " plugins. (" + moduleCount + " modules, " + (plugins.size() - moduleCount) + " plugins)");
        	    
            // Set footer
            Discord.setFooter(embed);
                
            // Send the embed
            event.getChannel().sendMessage(embed);
            return;
        }
            
    }
	
}