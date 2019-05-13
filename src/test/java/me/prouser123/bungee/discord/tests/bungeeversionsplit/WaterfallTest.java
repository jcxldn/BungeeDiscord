package me.prouser123.bungee.discord.tests.bungeeversionsplit;

import me.prouser123.bungee.discord.BungeeVersionSplit;
import me.prouser123.bungee.discord.Main;

import org.powermock.api.mockito.PowerMockito;

import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;

public class WaterfallTest {
	
	public static Core c = new Core("git:Waterfall-Bootstrap:1.14-SNAPSHOT:b438f80:282");

	@BeforeClass
    public static void setUp() throws Exception {
		Main.instance = c.main;
        PowerMockito.when(c.main.getLogger()).thenReturn(c.getLogger());
        c.bvs = new BungeeVersionSplit(c.getProxyServer());
    }

	@Test
	public void testGetJenkinsBuildNumber() {
		Assert.assertEquals("282", c.bvs.getJenkinsBuildNumber());
	}
	
	@Test
	public void testGetMCVersion() {
		Assert.assertEquals("1.14-SNAPSHOT", c.bvs.getMCVersion());
	}
	
	@Test
	public void testGetProxyProductName() {
		Assert.assertEquals("Waterfall", c.bvs.getProxyProductName());
	}
	
	@Test
	public void testGetSha() {
		Assert.assertEquals("b438f80", c.bvs.getSha());
	}
	
	@Test
	public void testIsValid() {
		Assert.assertTrue(c.bvs.isValid);
	}
	
	@Test
	public void testIsPaperMCBungee() {
		Assert.assertTrue(c.bvs.isPaperMCBungee());
	}

}