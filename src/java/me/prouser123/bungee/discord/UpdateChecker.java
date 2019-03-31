package me.prouser123.bungee.discord;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class UpdateChecker {
	
	public static boolean isUpdateAvailable;
	public static String availableVersion;
	
	public static void getLatestVersion() {
		if (Main.getMCM().getDisableUpdateCheck()) {
			Main.inst().getLogger().info("Update Checking has been disabled in the config.");
			return;
		}
		
		Main.inst().getLogger().info("[Update] Checking for updates...");
		try {
			String uri = "https://github.com/Prouser123/BungeeDiscord/raw/master/pom.xml";
			URL url = new URL(uri);
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/xml");
			
			InputStream xml = connection.getInputStream();
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			org.w3c.dom.Document doc = db.parse(xml);
			
			String latest = doc.getDocumentElement().getElementsByTagName("version").item(0).getTextContent();
			String current = Main.inst().getDescription().getVersion();
			
			// Compare
			//compare(current, latest);
			compare("2.1-STABLE", "2.2-STABLE");
			compare(current, "2.9-FAKE");
			
		} catch (Exception e) {
			Main.inst().getLogger().warning("[Update] Error checking for updates.");
			Main.inst().getLogger().warning("err: " + e);
		}
	}
	
	private static void compare(String current, String latest) {
		String s1 = normalisedVersion(current);
        String s2 = normalisedVersion(latest);
        int cmp = s1.compareTo(s2);
        String cmpStr = cmp < 0 ? "<" : cmp > 0 ? ">" : "==";
        Main.inst().getLogger().info("[Update.DEBUG] " + current + " " + cmpStr + " " + latest);
        Main.inst().getLogger().info("[Update.DEBUG] " + current + " (current) " + cmpStr + " " + latest + " (latest - master branch)");
        if (cmpStr.equals("<")) {
        	Main.inst().getLogger().info("[Update] Update available! v" + latest + " (currently v" + current + ")");
        	isUpdateAvailable = true;
        	availableVersion = latest;
        } else {
        	Main.inst().getLogger().info("[Update] You are running the latest version.");
        	isUpdateAvailable = false;
        }
	}

    private static String normalisedVersion(String version) {
        return normalisedVersion(version, ".", 4);
    }

    private static String normalisedVersion(String version, String sep, int maxWidth) {
        String[] split = Pattern.compile(sep, Pattern.LITERAL).split(version);
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            sb.append(String.format("%" + maxWidth + 's', s));
        }
        return sb.toString();
    }
}