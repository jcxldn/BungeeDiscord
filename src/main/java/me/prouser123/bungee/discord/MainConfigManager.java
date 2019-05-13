package me.prouser123.bungee.discord;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

// So that we can write to the config file and preserve comments, this *exists*
public class MainConfigManager {
	
	public MainConfigManager() {
		this.load();
	}

	private String[] lines = new String[23];
	
	public File getDataFolder() {
		return Main.inst().getDataFolder();
	}
	
	private static class defaultValues {
		private static String token = "bot-token-here";
		private static String joinLeaveChatId = "123456789";
		private static Boolean debugEnabled = false;
		private static Boolean disableUpdateCheck = false;
		private static String status = "$prefix | BungeeDiscord";
	}
	
	private static class values {
		private static String token;
		private static String joinLeaveChatId;
		private static Boolean debugEnabled;
		private static Boolean disableUpdateCheck;
		private static String status;
	}
	
	// GET
	
	public String getToken() {
		return (values.token != null) ? values.token : defaultValues.token;
	}
	
	public String getJoinLeaveChatId() {
		return (values.joinLeaveChatId != null) ? values.joinLeaveChatId : defaultValues.joinLeaveChatId;
	}
	
	public Boolean getDebugEnabled() {
		return (values.debugEnabled != null) ? values.debugEnabled : defaultValues.debugEnabled;
	}
	
	public Boolean getDisableUpdateCheck() {
		return (values.disableUpdateCheck != null) ? values.disableUpdateCheck : defaultValues.disableUpdateCheck;
	}
	
	public String getStatus() {
		return (!isEmpty(values.status)) ? values.status : defaultValues.status;
	}
	
	// SET
	
	public void setToken(String value) {
		values.token = value;
	}
	
	public void setJoinLeaveChatId(String value) {
		values.joinLeaveChatId = value;
	}
	
	public void setDebugEnabled(Boolean value) {
		values.debugEnabled = value;
	}
	
	public void setDisableUpdateCheck(Boolean value) {
		values.disableUpdateCheck = value;
	}
	
	public void setStatus(String value) {
		values.status = value;
	}
	
	public void populateLines() {
		lines[0] = "# Bungee Discord Config File MCM";
		lines[1] = "# Make sure there is always a space after any :";
		lines[2] = "";
		lines[3] = "";
		lines[4] = "token: " + this.getToken();
		lines[5] = "";
		lines[6] = "";
		lines[7] = "# Chat to a text channel when someone connects or disconnnects from the proxy / bungeecord / network";
		lines[8] = "# Leave as-is to disable";
		lines[9] = "join-leave-chat-id: '" + this.getJoinLeaveChatId() +"'";
		lines[10] = "";
		lines[11] = "";
		lines[12] = "# Discord Status";
		lines[13] = "# Use the $prefix variable for the prefix";
		lines[14] = "status: " + this.getStatus();
		lines[15] = "";
		lines[16] = "";
		lines[17] = "# Enable debug logging? Only useful if you are developing this plugin.";
		lines[18] = "debug-enabled: " + this.getDebugEnabled();
		lines[19] = "";
		lines[20] = "";
		lines[21] = "# Disable update checking?";
		lines[22] = "disable-update-check: " + this.getDisableUpdateCheck();
	}
	
	public void write() {
		try {
			//Main.inst().getDebugLogger().info("MCM_WRITE");
			populateLines();
			FileWriter fw=new FileWriter(getDataFolder() + File.separator + "config.yml");

			String writeOutput = "";
			
			for (String line: lines) {
				writeOutput += line + "\n";
			}
			
	        fw.write(writeOutput);    
	        fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void load() {
		try {
			Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
			
			String configToken = configuration.getString("token");
			if (configToken != this.getToken()) {
				this.setToken(configToken);
			}
			
			String configJLCID = configuration.getString("join-leave-chat-id");
			if (configJLCID != this.getJoinLeaveChatId()) {
				this.setJoinLeaveChatId(configJLCID);
			}
			
			Boolean configDebug = configuration.getBoolean("debug-enabled");
			if (configDebug != this.getDebugEnabled()) {
				this.setDebugEnabled(configDebug);
			}
			
			// done as disable as this defaults to false, so upgraders will get it.
			Boolean configUpdate = configuration.getBoolean("disable-update-check");
			if (configUpdate != this.getDisableUpdateCheck()) {
				this.setDisableUpdateCheck(configUpdate);
			}
			
			String configStatus = configuration.getString("status");
			if (configStatus != this.getStatus()) {
				this.setStatus(configStatus);
			}
		
		} catch (FileNotFoundException e) {
			Main.inst().getLogger().info("config.yml not found, creating...");
			write();
			//e.printStackTrace();
			
		} catch (IOException e) {
			Main.inst().getLogger().severe("Error loading config.yml. Run /bd save to re-make the config file. \n" + e.toString());
		}
	}
	
	private Boolean isEmpty(String string) {
		if (string == null) {
			return true;
		} else if (string.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}