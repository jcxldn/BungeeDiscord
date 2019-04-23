package me.prouser123.bungee.discord;

import org.javacord.api.entity.channel.TextChannel;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinLeave implements Listener {
	
	public static TextChannel channel = null;
	
	// Instancing
	private static JoinLeave instance;
	
	public static JoinLeave inst() {
		return instance;
	}
	
	public JoinLeave(String id) {
		JoinLeave.channel = Discord.api.getTextChannelById(id).get();
		instance = this;
	}
	
	@EventHandler
	public void onPlayerJoin(PostLoginEvent event) {
			channel.sendMessage("`" + event.getPlayer().getName() + "` has joined the network.");
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerDisconnectEvent event) {
			channel.sendMessage("`" + event.getPlayer().getName() + "` has left the network.");
	}
}