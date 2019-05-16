package me.prouser123.bungee.discord.tests;

import me.prouser123.bungee.discord.Main;
import me.prouser123.bungee.discord.tests.bungeeversionsplit.Core;

import org.powermock.api.mockito.PowerMockito;

import org.junit.Test;
import org.junit.BeforeClass;

import javax.management.InstanceAlreadyExistsException;

import org.junit.AfterClass;
import org.junit.Assert;

public class MainTest {
	
	private static Core c;
	
	@BeforeClass
	public static void setUp() {
		c = new Core(Core.ConstantVersions.bungeecord);
        PowerMockito.when(c.main.getProxy()).thenReturn(c.getProxy());
	}
	
	@AfterClass
	public static void stripDown() {
		c.main.onDisable();
	}
	
	@Test
	public void isModuleTestAsModule() {
		MockPlugin mp = new MockPlugin("git:cmd_list:1.13-SNAPSHOT:a907bbd:11", "SpigotMC");
		Assert.assertTrue(Main.isModule(mp.plugin));
	}
	
	@Test
	public void isModuleTestAsNotModule() {
		MockPlugin mp = new MockPlugin("69.0-STABLE", "MemeSquad");
		Assert.assertFalse(Main.isModule(mp.plugin));
	}
	
	@Test
	public void setInstAlreadySet() {
		Main main = PowerMockito.mock(Main.class);
		try {
			// There is already a set instance, so it should throw the exception
			Main.setInst(main);
			
			// If not, fail the test.
			Assert.fail("Method was expected to throw an InstanceAlreadyExistsException.");
		} catch (InstanceAlreadyExistsException e) {
			// Expected.
			System.out.println("InstanceAlreadyExistsException thrown, but was expected.");
		}
	}
}