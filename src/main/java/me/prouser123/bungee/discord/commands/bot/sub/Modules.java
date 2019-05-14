package me.prouser123.bungee.discord.commands.bot.sub;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import me.prouser123.bungee.discord.Constants;
import me.prouser123.bungee.discord.Discord;
import me.prouser123.bungee.discord.Main;
import me.prouser123.bungee.discord.base.BaseSubCommand;
import net.md_5.bungee.api.plugin.Plugin;

public class Modules implements MessageCreateListener, BaseSubCommand {
	
	private base base;

	public Modules(int piority, String command, String helpText) {
		base = this.easyBaseSetup(piority, command, helpText);
	}

	@Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessage().getContent().equalsIgnoreCase(base.command)) {
        	
        	// Check if the message author is a server admin
            if (!Discord.isAdmin(event)) {
                event.getChannel().sendMessage("You are not allowed to use this command!");
                return;
            }
        	
            // Get a collection of plugins
        	Collection<Plugin> plugins = Main.inst().getProxy().getPluginManager().getPlugins();
        	
        	ArrayList<String> modules = new ArrayList<String>();
        	
        	// Create the initial embed
        	EmbedBuilder embed = new EmbedBuilder().setAuthor("BungeeCord Modules", Constants.url, Constants.authorIconURL);
        	
        	for (Plugin plugin: plugins) {
            	// Only add modules
            	if (Main.isModule(plugin)) {
            		// Plugin is likely a module
            		modules.add(plugin.getDescription().getName());
            	}
        	}
        	
        	// Create the initial description
        	String description = ("Detected " + plugins.size() + " plugins. (" + modules.size() + " modules, " + (plugins.size() - modules.size()) + " plugins)\n");
        	 
        	// Alphabetical sorting
        	Collections.sort(modules);
        	
        	// Add modules to the description
        	for (String module: modules) description += "\n - " + module;
        	
        	// Set the description
        	embed.setDescription(description);
        	
            // Set footer
            Discord.setFooter(embed);
                
            // Send the embed
            event.getChannel().sendMessage(embed);
            return;
        }
            
    }
	
}