package me.prouser123.bungee.discord.commands;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import me.prouser123.bungee.discord.Constants;
import me.prouser123.bungee.discord.Discord;
import me.prouser123.bungee.discord.Main;
import me.prouser123.bungee.discord.base.BaseCommand;

public class ServerInfo implements MessageCreateListener, BaseCommand {
	
	private base base;
	
	public ServerInfo(int piority, String command, String helpText) {
		
		base = this.createBase();
		
		Main.inst().getLogger().info("[ServerInfo@Init] " + piority + " | " + command + " | " + helpText);
		base.command = command;
		base.helpPriority = piority;
		base.helpText = helpText;
		Main.inst().getLogger().info("[ServerInfo@Init] BASE() | " + base.helpPriority + " | " + base.command + " | " + base.helpText);
		this.addCommandToHelp(base);
	}
	
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessage().getContent().equalsIgnoreCase(this.base.command)) {
        	
        	SimpleDateFormat formatter = new SimpleDateFormat("dd:HH:mm:ss.SSS");
        	String uptime = formatter.format(ManagementFactory.getRuntimeMXBean().getUptime());
        	String[] uptime_split = uptime.split(":");
        	
        	String uptime_days = Integer.toString(new Integer(Integer.parseInt(uptime_split[0])) - 1);
        	String uptime_hours = Integer.toString(new Integer(Integer.parseInt(uptime_split[1])) - 1);
        	String uptime_minutes = uptime_split[2];
        	String uptime_seconds_2 = uptime_split[3];
        	String uptime_seconds = uptime_seconds_2.substring(0, uptime_seconds_2.indexOf("."));
        	
        	String uptime_output = "";
        	
        	if(Integer.parseInt(uptime_days) != 0 ) {
        		
        		uptime_output += uptime_days + "d ";
        	}
        
        	if(Integer.parseInt(uptime_hours) + Integer.parseInt(uptime_days) != 0 ) {
        		
        		uptime_output += (uptime_hours) + "h ";
        	}
        	
        	
        	if(Integer.parseInt(uptime_minutes) != 0 ) {
        		
        		uptime_output += uptime_minutes + "m ";
        	}
        	
        	uptime_output += uptime_seconds + "s";
        	
        	// Get Bot Owner
        	String bot_owner = "<@";
        	try {
				bot_owner += Long.toString(event.getApi().getApplicationInfo().get().getOwnerId());
				bot_owner += ">";
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	EmbedBuilder embed = new EmbedBuilder()
        		.setAuthor("BungeeCord Server Information", Constants.url, Constants.authorIconURL)
            	.addInlineField("Players", Integer.toString(Main.inst().getProxy().getPlayers().size()) + "/" + Integer.toString(Main.inst().getProxy().getConfig().getPlayerLimit()))
            	.addInlineField("Uptime", uptime_output)
            	.addInlineField("Memory", Long.toString(Runtime.getRuntime().freeMemory() / 1024 / 1024 ) + "/" + Long.toString(Runtime.getRuntime().totalMemory() / 1024 / 1024) + " MB free")
            	.addInlineField("Servers", Integer.toString(Main.inst().getProxy().getServers().size()))
            	.addInlineField("Server Versions", Main.inst().getProxy().getGameVersion().toString())
            	.addInlineField("Bot Owner", bot_owner)
            	.addInlineField("Server Version", System.getProperty("os.name") + ", " + Main.inst().getProxy().getVersion());
        	
        	// Set footer
        	Discord.setFooter(embed);
            
        	// Send the embed
            event.getChannel().sendMessage(embed);
            return;
        }
    }

}