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

public class Main extends Plugin {

	// Instancing
	private static Main instance;
	private static Configuration configuration;
	
    public static Main inst() {
    	  return instance;
    }
    
    public static Configuration getConfig() {
    	return configuration;
    }
	
	@Override
	public void onEnable() {
		// Instancing
		instance = this;
		
		getLogger().info("Welcome!");
		
		// Start bStats
		new MetricsLite(this, true);
		
		// Setup config
		loadResource(this, "config.yml");
		try {
			configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
		} catch (IOException e) {
			getLogger().severe("[Discord] Error loading config.yml");
		}
        
        new Discord(getConfig().getString("token"));
        
        // Cache a maximum of 10 messages per channel for and remove messages older than 1 hour
        Discord.api.setMessageCacheSize(10, 60*60);
        
		Discord.api.addMessageCreateListener(new ServerInfo());
		Discord.api.addMessageCreateListener(new MainCommand());
		Discord.api.addMessageCreateListener(new CopyOwnerAvatar("!getOwnerAvatar"));
		Discord.api.addMessageCreateListener(new Players());
		Discord.api.addMessageCreateListener(new BotInfo());
		
		// Register Bungee Player Join/Leave Listeners
		String jlcID = getConfig().getString("join-leave-chat-id");
		
		try {
			getProxy().getPluginManager().registerListener(this, new JoinLeave(jlcID));
			getLogger().info("Join Leave Chat enabled for channel: #" + Discord.api.getChannelById(jlcID).toString().replaceAll(".*\\[|\\].*", "") + " (id: " + jlcID + ")");
		} catch (NoSuchElementException e) {

			getLogger().info("Join Leave Chat disabled. Did you put a valid channel ID in the config?");
			return;
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