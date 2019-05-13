package me.prouser123.bungee.discord.tests;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginDescription;

/**
 * Mock and test the plugin's description.
 */
public class MockPlugin {
	
	public Plugin plugin;
	
	/**
	 * Constructor for creating a mock plugin.
	 * 
	 * @param version mock plugin version
	 * @param author mock plugin author
	 */
	public MockPlugin(String version, String author) {
		plugin = new Plugin() {
			
			// Create a plugin description class
			private final PluginDescription description = new PluginDescription() {
				
				public String getVersion() { return version; }
				public String getAuthor() { return author; }
			};
			
			// getter - plugin description
			public PluginDescription getDescription() {
				return description;
			}
		};
	}
}