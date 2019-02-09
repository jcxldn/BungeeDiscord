package me.prouser123.bungee.discord;

public class DebugLogger {
	
	private boolean debugEnabled = false;
	
	public void info(String message) {
		if (debugEnabled) {
			Main.inst().getLogger().info("[DEBUG] " + message);
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