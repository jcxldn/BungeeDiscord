package me.prouser123.bungee.discord.tests.bungeeversionsplit;

import me.prouser123.bungee.discord.BungeeVersionSplit;
import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;

public class BungeeCordTest {
	
	public static Core c = new Core(Core.ConstantVersions.bungeecord);
	
	@BeforeClass
    public static void setUp() throws Exception {
        c.bvs = new BungeeVersionSplit(c.getProxy());
    }
	
	@Test
	public void testGetJenkinsBuildNumber() {
		Assert.assertEquals("1396", c.bvs.getJenkinsBuildNumber());
	}
	
	@Test
	public void testGetMCVersion() {
		Assert.assertEquals("1.13-SNAPSHOT", c.bvs.getMCVersion());
	}
	
	@Test
	public void testGetProxyProductName() {
		Assert.assertEquals("BungeeCord", c.bvs.getProxyProductName());
	}
	
	@Test
	public void testGetSha() {
		Assert.assertEquals("0dd538f", c.bvs.getSha());
	}
	
	@Test
	public void testIsValid() {
		Assert.assertTrue(c.bvs.isValid);
	}
	
	@Test
	public void testIsPaperMCBungee() {
		Assert.assertFalse(c.bvs.isPaperMCBungee());
	}

}