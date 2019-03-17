package me.prouser123.bungee.discord;

import java.util.Arrays;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class InGameCommand extends Command {

	public InGameCommand() {
		super("bd", "bungeediscord.use");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		// bd command
		if ( args.length == 0 )
        {
            sender.sendMessage(new TextComponent(this.title + " " + ChatColor.GRAY + Main.inst().getDescription().getVersion()));

            if (UpdateChecker.isUpdateAvailable) {
            	sender.sendMessage(new TextComponent(ChatColor.GOLD + "There is a new update available: v" + UpdateChecker.availableVersion));
            }
            
            sender.sendMessage(new TextComponent(this.connectedAsUser(false)));
            
            if (Discord.isConnected()) {
                Integer servers = Discord.api.getServers().size();
                String suffix = (servers == 1)?  " server.": " servers.";
                sender.sendMessage(new TextComponent("Connected to " + Integer.toString(servers) + suffix));
                sender.sendMessage(new TextComponent(Discord.api.getServers().toString()));
                
            } else {
            	sender.sendMessage(new TextComponent(ChatColor.RED + "Please use /bd token <token> or the config file to enter your bot token." ));
            	sender.sendMessage(new TextComponent(ChatColor.RED + "If you edit the config file, please either use /bd reload or restart the server." ));
            }
            
        // bd help command
        } else if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(new TextComponent(this.title + ChatColor.GRAY + " Commands"));
            sender.sendMessage(new TextComponent(ChatColor.DARK_GREEN + "/bd" + ChatColor.GRAY + " - " + ChatColor.GOLD + "View plugin and status information"));
            sender.sendMessage(new TextComponent(ChatColor.DARK_GREEN + "/bd help" + ChatColor.GRAY + " - " + ChatColor.GOLD + "This page."));
            sender.sendMessage(new TextComponent(ChatColor.DARK_GREEN + "/bd reload" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Reload from the config file."));
            sender.sendMessage(new TextComponent(ChatColor.DARK_GREEN + "/bd token" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Set the bot token."));
            sender.sendMessage(new TextComponent(ChatColor.DARK_GREEN + "/bd invite" + ChatColor.GRAY + " - " + ChatColor.GOLD + "Get the bot's invite link."));

            
        // bd save command (testing purposes only)
        } else if (args[0].equalsIgnoreCase("save")) {
        	if (args.length == 1) {
            	Main.getMCM().write();
				sender.sendMessage(new TextComponent(this.prefix + ChatColor.DARK_GREEN + "Saved!"));
        	} else if (args[1].equalsIgnoreCase("token")) {
        		Main.getMCM().setToken(args[2]);
        	} else if (args[1].equalsIgnoreCase("jlcid")) {
        		Main.getMCM().setJoinLeaveChatId(args[2]);
        	} else if (args[1].equalsIgnoreCase("debug")) {
        		Main.getMCM().setDebugEnabled(Boolean.parseBoolean(args[2]));
        	}
        	
        	
        // bd reload command
        } else if (args[0].equalsIgnoreCase("reload")) {
        	Main.getMCM().load();
        	sender.sendMessage(new TextComponent(this.prefix + ChatColor.DARK_GREEN + "Reloaded from config."));
        	// Log all variables for debugging
        	sender.sendMessage(new TextComponent("token: " + Main.getMCM().getToken()));
        	sender.sendMessage(new TextComponent("jclid: " + Main.getMCM().getJoinLeaveChatId()));
        	sender.sendMessage(new TextComponent("debug: " + Main.getMCM().getDebugEnabled()));
        	
        	// Reconnect DiscordApi if it is null
        	if (Discord.api == null) {
        		new Discord(Main.getMCM().getToken());
        		if (Discord.api != null) {
                    Main.inst().setLocalBotOptions();
                    Main.inst().getLogger().info("bd reload: token " + Main.getMCM().getToken());
                    sender.sendMessage(new TextComponent(connectedAsUser(true)));
        		}
        	}
        	
        	
        // bd invite command
        } else if (args[0].equalsIgnoreCase("invite")) {
        	if (sender instanceof ProxiedPlayer) {
        		Main.inst().getDebugLogger().info("is player");
            	TextComponent message = new TextComponent(this.prefix + "Invite Link (click me!)");
            	message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, Discord.api.createBotInvite()));
            	message.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Invite your bot to your server!").create() ) );
            	sender.sendMessage(message);
        	} else {
        		Main.inst().getDebugLogger().info("is not player");
            	sender.sendMessage(new TextComponent(this.prefix + "Invite Link: " + Discord.api.createBotInvite()));
        	}
        	
        	
        // bd token command
        } else if (args[0].equalsIgnoreCase("token")) {
        	// 2 command arguments (e.g. /bd token <token>)
        	if (args.length == 2) {
        		sender.sendMessage(new TextComponent("token: " + args[1]));
        		
                new Discord(args[1]);
                if (Discord.api != null) {
                    Main.inst().setLocalBotOptions();
                    Main.inst().getLogger().info(Main.getMCM().getToken());
                    
                    // Attempt to set the new token and save the file
                    Main.getMCM().setToken(args[1]);
                    Main.getMCM().write();
					sender.sendMessage(new TextComponent(this.prefix + ChatColor.DARK_GREEN + "Saved to config."));

                    Main.inst().getLogger().info(Main.getMCM().getToken());
                    sender.sendMessage(new TextComponent(this.connectedAsUser(true)));
                } else {
                	sender.sendMessage(new TextComponent(this.prefix + ChatColor.RED + "Invalid token! Please try another token. This has not been saved to the config."));
                	sender.sendMessage(new TextComponent(this.prefix + ChatColor.RED + "If you had a working token before running this command, use /bd reload to use the old token"));
                }
                
            // User only put /bd token (1 command argument)
        	} else {
                sender.sendMessage(new TextComponent(ChatColor.RED + "Please specify a bot token with: /bd token <bot token>"));
                this.helpMessage(sender);
        	}
        	
        // Show generic usage help command
        } else {
            this.invalidMessage(sender);
            sender.sendMessage(new TextComponent("args: " +  Arrays.toString(args)));
        }
		
	}
	
	public void helpMessage(CommandSender sender) {
        sender.sendMessage(new TextComponent(ChatColor.RED + this.helpMessageText));
	}
	
	public void invalidMessage(CommandSender sender) {
        sender.sendMessage(new TextComponent(ChatColor.RED + this.invalidCommandText + " " + this.helpMessageText));
	}
	
	
	// Strings for messages so that they can be edited easily.
	public String helpMessageText = "Use /bd help for help.";
	public String invalidCommandText = "Invalid command.";
	
	public String title = (ChatColor.DARK_AQUA + "Bungee" + ChatColor.LIGHT_PURPLE + "Discord");
	public String prefix = (ChatColor.WHITE + "[" + this.title + ChatColor.WHITE + "] " + ChatColor.GRAY);
	
	//public String connectedAsUser = (this.prefix + ChatColor.DARK_GREEN + "Connected as " + Discord.api.getAccountType().toString().toLowerCase() +  " user: " + ChatColor.GRAY + Discord.api.getYourself().getName());
	public String connectedAsUser(boolean showPrefix) {
		String output = "";
		Main.inst().getLogger().info(Boolean.toString(showPrefix));
		if (showPrefix) output += this.prefix;
		try {
			output += ChatColor.DARK_GREEN + "Connected as " + Discord.api.getAccountType().toString().toLowerCase() +  " user: " + ChatColor.GRAY + Discord.api.getYourself().getName();
		} catch (NullPointerException ex) {
			output += ChatColor.DARK_GREEN + "Not connected!";
		}
		return output;
	}
}