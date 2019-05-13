package me.prouser123.bungee.discord.tests;

import me.prouser123.bungee.discord.Main;
import me.prouser123.bungee.discord.tests.bungeeversionsplit.Core;

import org.powermock.api.mockito.PowerMockito;

import org.junit.Test;
import org.junit.Assert;

public class MainTest {
	
	@Test
	public void isModuleTest_module() {
		MockPlugin mp = new MockPlugin("git:cmd_list:1.13-SNAPSHOT:a907bbd:11", "SpigotMC");
		Core cc = new Core(Core.ConstantVersions.bungeecord);
		
        PowerMockito.when(cc.main.getProxy()).thenReturn(cc.getProxy());

		Assert.assertTrue(Main.isModule(mp.plugin));
	}
	
	@Test
	public void isModuleTest_not_module() {
		MockPlugin mp = new MockPlugin("69.0-STABLE", "MemeSquad");
		
		Core cc = new Core(Core.ConstantVersions.bungeecord);
		
        PowerMockito.when(cc.main.getProxy()).thenReturn(cc.getProxy());
        
		Assert.assertFalse(Main.isModule(mp.plugin));
	}
}