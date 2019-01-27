package me.prouser123.bungee.discord.commands;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;


import org.javacord.api.Javacord;

import me.prouser123.bungee.discord.Discord;
import me.prouser123.bungee.discord.Main;

public class BotInfo implements MessageCreateListener {
	
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessage().getContent().equalsIgnoreCase("!botinfo")) {
        	
        	Object[] currentserverroles = Discord.api.getYourself().getRoles(event.getServer().get()).toArray();
        	String roles = "";
        	for (Object roleObject : currentserverroles) {
        		String role = roleObject.toString().split(", name: ")[1].split(", server:")[0];
        		
        		// Remove the initial @ from the role name to avoid role spam
        		if (role.startsWith(("@"))) {
        			Main.inst().getLogger().info("StartsWith @");
        			role = role.replace("@", "");
        		}
        		
        		// Adds to the list of roles
        		roles += role + " ";
        		Main.inst().getLogger().info(role);
        	}
        	
        	EmbedBuilder embed2 = new EmbedBuilder()
        		.setAuthor("BungeeDiscord Bot Information", "https://github.com/Prouser123/KodiCore", "https://cdn.discordapp.com/embed/avatars/0.png")
        		.addInlineField("BungeeDiscord Version", Main.inst().getDescription().getVersion().toString())
            	.addInlineField("JavaCord Version", Javacord.VERSION + " (API v" + Javacord.DISCORD_API_VERSION + ")")
            	.addInlineField("Bot Servers", Long.toString(Discord.api.getServers().size()))
            	.addInlineField("User Type", Discord.api.getAccountType().toString())
            	.addInlineField("Roles (Current Server)", roles)
            	// Set footer
            	.setFooter("Bungee Discord " + Main.inst().getDescription().getVersion().toString() + " | !bd"/*.split("-")[0]*/, "https://cdn.discordapp.com/avatars/215119410103451648/575d90fdda8663b633e36f8b8c06c719.png");
            	// Send the embed
            event.getChannel().sendMessage(embed2);
            return;
        }
    }

}