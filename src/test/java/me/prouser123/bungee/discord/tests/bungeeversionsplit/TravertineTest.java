package me.prouser123.bungee.discord.tests.bungeeversionsplit;

import me.prouser123.bungee.discord.BungeeVersionSplit;
import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

public class TravertineTest {
	
	private static Core c = new Core(Core.ConstantVersions.travertine);

	@BeforeClass
    public static void setUp() throws Exception {
        c.bvs = new BungeeVersionSplit(c.getProxy());
    }
	
	@AfterClass
	public static void stripDown() {
		c.main.onDisable();
	}

	@Test
	public void testGetJenkinsBuildNumber() {
		Assert.assertEquals("99", c.bvs.getJenkinsBuildNumber());
	}
	
	@Test
	public void testGetMCVersion() {
		Assert.assertEquals("1.14-SNAPSHOT", c.bvs.getMCVersion());
	}
	
	@Test
	public void testGetProxyProductName() {
		Assert.assertEquals("Travertine", c.bvs.getProxyProductName());
	}
	
	@Test
	public void testGetSha() {
		Assert.assertEquals("68f2b82", c.bvs.getSha());
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