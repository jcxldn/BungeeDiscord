package me.prouser123.bungee.discord;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;

import com.google.common.io.ByteStreams;

import me.prouser123.bstatsplus.bungee.Metrics;
// Since we need all the commands here, this is fine.
import me.prouser123.bungee.discord.commands.bot.*;
import me.prouser123.bungee.discord.commands.bot.sub.*;
import me.prouser123.bungee.discord.commands.ingame.InGameCommand;

public class Main extends Plugin {
	
	private Integer totalDiscordUsers = 0;

	// Instancing
	private static Main instance;
	private static Configuration botCommandConfiguration;
	// this one is public so that it can be accessed by the debug command 
	public static DebugLogger debugLogger;
	private static MainConfigManager mcm;
	
    public static Main inst() {
    	  return instance;
    }
    
    public static Configuration getConfigBotCommand() {
    	return botCommandConfiguration;
    }
    
    public DebugLogger getDebugLogger() {
    	return debugLogger;
    }
    
    public static MainConfigManager getMCM() {
    	return mcm;
    }
	
	@Override
	public void onEnable() {
		// Instancing
		instance = this;
		
		getLogger().info("Welcome!");
		
		// Start bStats
		Metrics metrics = new Metrics(this);
		
		// Setup bot commands config
		loadResource(this, "bot-command-options.yml");
		try {
			botCommandConfiguration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "bot-command-options.yml"));
		} catch (IOException e) {
			getLogger().severe("Error loading bot-command-options.yml");
		}
		
		// Setup config (MainConfigManager)
        mcm = new MainConfigManager();

		// Check for updates
        getLogger().info("Update DISABLED: " + mcm.getDisableUpdateCheck().toString());
		UpdateChecker.getLatestVersion();
		getLogger().info("UpdateChecker.isUpdateAvailable: " + UpdateChecker.isUpdateAvailable);
		getLogger().info("UpdateChecker.availableVersion: " + UpdateChecker.availableVersion);
		
		// Setup Debug Logging
		debugLogger = new DebugLogger();
        
        new Discord(getMCM().getToken());
        
        if (Discord.api != null) {
        	this.setLocalBotOptions();
        	
        	// bStats custom chart - Users on Discord servers using BungeeDiscord
            metrics.addCustomChart(new Metrics.SingleLineChart("discordServerUsers", new Callable<Integer>() {
                @Override
                // get member count of every server the bot is in
                public Integer call() throws Exception {
                	totalDiscordUsers = 0;
                    Discord.api.getServers().forEach(server -> totalDiscordUsers += server.getMemberCount());
                    return totalDiscordUsers;
                }
            }));
        }
        
		// Register in-game /bd command
        getProxy().getPluginManager().registerCommand(this, new InGameCommand());
	}
	
	public void setLocalBotOptions() {
        // Cache a maximum of 10 messages per channel for and remove messages older than 1 hour
        Discord.api.setMessageCacheSize(10, 60*60);
		
		// Register customizable bot commands from the config, falling back to hard-coded defaults
		Main.registerListeners.botCommands();
		Main.registerListeners.subCommands();
		
		// Register Bungee Player Join/Leave Listeners
		Main.registerListeners.playerJoinLeave();
		
	}
	
	// Public so it can be accessed from in-game commands
	public static class registerListeners {
		
		public static Boolean jlcidEnabled = false;
		
		// Public so it can be accessed from in-game commands (bd jlcid)
		public static void playerJoinLeave() {
			// Register Bungee Player Join/Leave Listeners
			String jlcID = getMCM().getJoinLeaveChatId();
			
			try {
				Main.inst().getProxy().getPluginManager().registerListener(Main.inst(), new JoinLeave(jlcID));
				Main.inst().getLogger().info("Join Leave Chat enabled for channel: #" + Discord.api.getChannelById(jlcID).toString().replaceAll(".*\\[|\\].*", "") + " (id: " + jlcID + ")");
				jlcidEnabled = true;
			} catch (NoSuchElementException e) {

				Main.inst().getLogger().info("Join Leave Chat disabled. Did you put a valid channel ID in the config?");
				jlcidEnabled = false;
				
				// Deregister the listener and reset the JoinLeave channel if required
				try {
					Listener l = JoinLeave.inst();
					Main.instance.getProxy().getPluginManager().unregisterListener(l);
					JoinLeave.channel = null;
					Main.inst().getLogger().info("deregistered listener and cleared channel");
				} catch (NullPointerException ex) {
					Main.inst().getLogger().info("Failed to deregister listener - NPE");
					return;
				}
				
				// Return
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
			Discord.api.addMessageCreateListener(new Plugins(4, "!bd plugins", "Show active plugins. (requires admin)"));
			Discord.api.addMessageCreateListener(new Modules(5, "!bd modules", "Show active modules. (requires admin)"));
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
	
	public static void saveResource(Plugin plugin, String resourcePath, Configuration config) throws IOException {
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(plugin.getDataFolder(), resourcePath));
	}
	

	// if plugin version has the same style, mc version and sha
	// OR
	// author is SpigotMC/WaterfallMC (in rare cases where the mc version might not match up)
	public static boolean isModule(Plugin plugin) {
		String version = plugin.getDescription().getVersion();
		String author = plugin.getDescription().getAuthor();
		
		String[] proxyVersionSplit = Main.inst().getProxy().getVersion().split(":");
		
		return (version.startsWith("git") && version.endsWith(proxyVersionSplit[2] + ":" + proxyVersionSplit[3]) || author.equals("SpigotMC") || author.equals("WaterfallMC"));
	}
	
	@Override
	public void onDisable() {
		if (Discord.api != null) {
			Discord.api.disconnect();
		}
	}
}