package me.prouser123.bungee.discord;

import java.util.logging.Level;

public class DebugLogger {
	
	private boolean debugEnabled = false;
	
	public void info(String message) {
		if (debugEnabled) {
			Main.inst().getProxy().getLogger().log(Level.INFO, "[" + Main.inst().getDescription().getName() + ".DEBUG] " + message);
		}
	}
	
	public DebugLogger() {
		try {
			if (Main.inst().getConfig().getBoolean("debug-enabled")) {
				debugEnabled = true;
				Main.inst().getLogger().info("Enabled debug logging.");
			}
		} catch (NullPointerException npex) {
			// Coudn't find the boolean, just return for now.
			return;
		}
	}
}