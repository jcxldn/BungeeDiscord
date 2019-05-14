package me.prouser123.bungee.discord;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class BuildOrigin {
	
	// -------------------------------------------
	private String fileName = "origin.temp.yml";
	private String placeholder = "${build.origin}";
	// -------------------------------------------
	
	public Configuration originConfig;
	
	public String origin;
	
	public BuildOrigin() {
		try {
			File f = new File(Main.inst().getDataFolder(), fileName);
			
			Main.inst().getDebugLogger().info("[BuildOrigin@init] exists: " + f.exists());
			
			// Delete the file if it exists.
			if (f.exists()) {
				Boolean deleted = f.delete();
				if (!deleted) throw new FileDeleteException("Error deleting " + fileName);
			}
			

			Main.loadResource(Main.inst(), fileName);
			originConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(f);
			
		} catch (IOException | FileDeleteException e) {
			origin = "unknown";
			return;
		}
		
		origin = originConfig.getString("origin");

		if (origin.equalsIgnoreCase(placeholder)) origin = "other";
		

		Main.inst().getDebugLogger().info("[BuildOrigin@init] value: " + origin);
	}
	
	public class FileDeleteException extends Exception {
		// Randomly generated
		private static final long serialVersionUID = 1674948645649473295L;

		public FileDeleteException(String errorMessage) {
	        super(errorMessage);
	    }
	}
}