package me.prouser123.bungee.discord.tests.bungeeversionsplit;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.powermock.api.mockito.PowerMockito;

import me.prouser123.bungee.discord.BungeeVersionSplit;
import me.prouser123.bungee.discord.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyConfig;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ReconnectHandler;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.config.ConfigurationAdapter;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.api.scheduler.TaskScheduler;

/**
 * 
 * Intended for use by the BungeeVersionSplit test classes.
 * 
 * <p>Mock and test:
 * 
 * <ul>
 *   <li>Main.inst().getProxy().getVersion()</li>
 *   <li>Main.inst().getLogger()</li>
 * </ul>
 */
@SuppressWarnings("deprecation")
public class Core {
	
	/**
	 * Server version constants for popular platforms.
	 */
	public static final class ConstantVersions {
		public static final String bungeecord = "git:BungeeCord-Bootstrap:1.13-SNAPSHOT:0dd538f:1396";
		public static final String travertine = "git:Travertine-Bootstrap:1.14-SNAPSHOT:68f2b82:99";
		public static final String waterfall = "git:Waterfall-Bootstrap:1.14-SNAPSHOT:b438f80:282";
	}
	
	private static String proxyVersion;
	
	private static Logger logger = Logger.getLogger(Core.class.getName());
	
	private static final ProxyServer proxyServer = new ProxyServer() {
		
		@Override
		public String getVersion() {
			return proxyVersion;
		}

		@Override
		public void broadcast(String message) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void broadcast(BaseComponent... message) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void broadcast(BaseComponent message) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public ServerInfo constructServerInfo(String name, InetSocketAddress address, String motd, boolean restricted) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Title createTitle() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<String> getChannels() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ProxyConfig getConfig() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ConfigurationAdapter getConfigurationAdapter() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public CommandSender getConsole() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<String> getDisabledCommands() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getGameVersion() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Logger getLogger() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getOnlineCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public ProxiedPlayer getPlayer(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ProxiedPlayer getPlayer(UUID uuid) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<ProxiedPlayer> getPlayers() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PluginManager getPluginManager() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public File getPluginsFolder() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getProtocolVersion() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public ReconnectHandler getReconnectHandler() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public TaskScheduler getScheduler() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ServerInfo getServerInfo(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, ServerInfo> getServers() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getTranslation(String name, Object... args) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<ProxiedPlayer> matchPlayer(String match) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void registerChannel(String channel) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setConfigurationAdapter(ConfigurationAdapter adapter) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setReconnectHandler(ReconnectHandler handler) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void start() throws Exception {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void stop() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void stop(String reason) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void unregisterChannel(String channel) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	public BungeeVersionSplit bvs;
	
	public Main main = PowerMockito.mock(Main.class);

	public Core(String proxyVersion) {
		Core.proxyVersion = proxyVersion;
		Main.instance = main;
        PowerMockito.when(main.getLogger()).thenReturn(logger);
	}
	
	public ProxyServer getProxy() {
		return proxyServer;
	}
}