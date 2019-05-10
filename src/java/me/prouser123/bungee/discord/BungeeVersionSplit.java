package me.prouser123.bungee.discord;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeVersionSplit {
	
	private String rawVersion;
	private String[] split;
	
	public Boolean isValid = false;
	
	public BungeeVersionSplit(Plugin plugin) {
		rawVersion = plugin.getDescription().getVersion();
		verify();
	}
	
	public BungeeVersionSplit(ProxyServer ps) {
		rawVersion = ps.getVersion();
		verify();
		
	}
	
	private void verify() {
		// Verify version format, assuming rawVersion was already set
		split = rawVersion.split(":");
		
		if (split[0].equals("git") && split.length == 5) {
			isValid = true;
			Main.inst().getLogger().info("[BVS] Detected " + getProxyProductName() + " " + getMCVersion() + " (sha " + getSha() + ", build #" + getJenkinsBuildNumber() + ")");
		}
	}
	
	public String getProxyProductName() {
		String[] productSplit = this.split[1].split("-");
		if (productSplit.length == 2 && productSplit[1].equals("Bootstrap")) {
			return productSplit[0];
		} else {
			return null;
		}
	}
	
	public String getMCVersion() {
		return this.split[2];
	}
	
	public String getSha() {
		return this.split[3];
	}
	
	public String getJenkinsBuildNumber() {
		return this.split[4];
	}
}