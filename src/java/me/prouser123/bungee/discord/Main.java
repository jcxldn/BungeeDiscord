package me.prouser123.bungee.discord;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.util.NoSuchElementException;

import com.google.common.io.ByteStreams;

import me.prouser123.bstatsplus.bungee.MetricsLite;
// Since we need all the commands here, this is fine.
import me.prouser123.bungee.discord.commands.*;
import me.prouser123.bungee.discord.commands.sub.*;

public class Main extends Plugin {

	// Instancing
	private static Main instance;
	private static Configuration configuration;
	private static Configuration botCommandConfiguration;
	private static DebugLogger debugLogger;
	
    public static Main inst() {
    	  return instance;
    }
    
    public static Configuration getConfig() {
    	return configuration;
    }
    
    public static Configuration getConfigBotCommand() {
    	return botCommandConfiguration;
    }
    
    public DebugLogger getDebugLogger() {
    	return debugLogger;
    }
	
	@Override
	public void onEnable() {
		// Instancing
		instance = this;
		
		getLogger().info("Welcome!");
		
		// Start bStats
		new MetricsLite(this);
		
		// Setup config
		loadResource(this, "config.yml");
		try {
			configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
		} catch (IOException e) {
			getLogger().severe("Error loading config.yml");
		}
		
		// Setup bot commands config
		loadResource(this, "bot-command-options.yml");
		try {
			botCommandConfiguration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "bot-command-options.yml"));
		} catch (IOException e) {
			getLogger().severe("Error loading bot-command-options.yml");
		}
		
		// Setup Debug Logging
		debugLogger = new DebugLogger();
        
        new Discord(getConfig().getString("token"));
        
        if (Discord.api != null) {
        	
            // Cache a maximum of 10 messages per channel for and remove messages older than 1 hour
            Discord.api.setMessageCacheSize(10, 60*60);
    		
    		// Register customizable bot commands from the config, falling back to hard-coded defaults
    		Main.registerListeners.botCommands();
    		Main.registerListeners.subCommands();
    		
    		// Register Bungee Player Join/Leave Listeners
    		Main.registerListeners.playerJoinLeave();
    		
        }
        
		// Register in-game /bd command
        getProxy().getPluginManager().registerCommand(this, new InGameCommand());
	}
	
	private static class registerListeners {
		
		private static void playerJoinLeave() {
			// Register Bungee Player Join/Leave Listeners
			String jlcID = getConfig().getString("join-leave-chat-id");
			
			try {
				Main.inst().getProxy().getPluginManager().registerListener(Main.inst(), new JoinLeave(jlcID));
				Main.inst().getLogger().info("Join Leave Chat enabled for channel: #" + Discord.api.getChannelById(jlcID).toString().replaceAll(".*\\[|\\].*", "") + " (id: " + jlcID + ")");
			} catch (NoSuchElementException e) {

				Main.inst().getLogger().info("Join Leave Chat disabled. Did you put a valid channel ID in the config?");
				return;
			}
		}
		
		private static void botCommands() {
			Main.inst().getLogger().info("Registering commands...");
			
			// Register Main Command Class
			Discord.api.addMessageCreateListener(new MainCommand());
			
			// Attempt to register server-info from the config, falling back to the defaults.
			if (getConfigBotCommand().contains("server-info")) {
				Discord.api.addMessageCreateListener(new ServerInfo(0, getConfigBotCommand().getString("server-info.command"), getConfigBotCommand().getString("server-info.description")));	
			} else {
					Main.inst().getLogger().warning("[Bot Command Options] Missing the server-info path. You will not be able to customize the !serverinfo command.");
					Discord.api.addMessageCreateListener(new ServerInfo(0, "!serverinfo", "Show server information."));
			}
			
			// Attempt to register players from the config, falling back to the defaults.
			if (getConfigBotCommand().contains("players")) {
				Discord.api.addMessageCreateListener(new Players(1, getConfigBotCommand().getString("players.command"), getConfigBotCommand().getString("players.description")));
			} else {
					Main.inst().getLogger().warning("[Bot Command Options] Missing the players path. You will not be able to customize the !players command.");
					Discord.api.addMessageCreateListener(new Players(2, "!players", "Show players currently on the network and their servers."));
			}
			
			
		}
		
		private static void subCommands() {
			Main.inst().getLogger().info("Registering sub-commands...");

			// generate on demand (GoD) - copy owner avatar
			Discord.api.addMessageCreateListener(new BotInfo(0, "!bd botinfo", "Show bot information."));
			Discord.api.addMessageCreateListener(new StealAvatar(1, "!bd steal", "GoD.stealAvatar"));
			Discord.api.addMessageCreateListener(new Invite(2, "!bd invite", "Show a bot invite link to add the bot to other servers."));
			Discord.api.addMessageCreateListener(new Debug(3, "!bd debug", "Show debug information."));
		}
	}
	
	
	public static File loadResource(Plugin plugin, String resource) {
        File folder = plugin.getDataFolder();
        if (!folder.exists())
            folder.mkdir();
        File resourceFile = new File(folder, resource);
        try {
            if (!resourceFile.exists()) {
                resourceFile.createNewFile();
                try (InputStream in = plugin.getResourceAsStream(resource);
                     OutputStream out = new FileOutputStream(resourceFile)) {
                    ByteStreams.copy(in, out);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resourceFile;
    }
	
	@Override
	public void onDisable() {
		if (Discord.api != null) {
			Discord.api.disconnect();
		}
	}
}