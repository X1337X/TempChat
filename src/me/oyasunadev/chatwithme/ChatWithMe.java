/**
 * MineRay Development™
 *
 * ChatWithMe™ v1.0
 * Created by Oliver Yasuna
 *
 * Source Approved and Distributed by Oliver Yasuna
 **/

/**
 *
 **/

package me.oyasunadev.chatwithme;

import me.oyasunadev.chatwithme.updater.UpdatePlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import me.oyasunadev.chatwithme.listeners.playerListener;
import org.bukkit.util.config.Configuration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ChatWithMe extends JavaPlugin
{
	
	HashMap<String,String> servermap = new HashMap<String,String>();
	
	private boolean updateAvailable = false;
	private PluginManager pm;

	public final Logger log = Logger.getLogger("Minecraft");

	public PluginDescriptionFile pdfFile;

	private final playerListener PlayerListener = new playerListener(this);

	public Map<Player, Player> chatType = new HashMap<Player, Player>();

	public Configuration chatConfig;
	public Configuration config;
	public Configuration servers;

	public void onEnable()
	{
		boolean cancel = false;

		pdfFile = getDescription();
		pm = getServer().getPluginManager();

		log.info("[" + pdfFile.getName() + " v" + pdfFile.getVersion() + "] Checking for updates...");
		if(UpdatePlugin.isUpToDate(pdfFile.getVersion(), UpdatePlugin.getLatestVersion("http://oyasdev.site40.net/latest.html")))
		{
			log.info("[" + pdfFile.getName() + " v" + pdfFile.getVersion() + "] Up to date!");
		} else {
			log.info("[" + pdfFile.getName() + " v" + pdfFile.getVersion() + "] UPDATE AVAILABLE!");

			cancel = true;
			updateAvailable = true;

			pm.disablePlugin(this);
		}

		if(!cancel)
		{
			log.info("[" + pdfFile.getName() + " v" + pdfFile.getVersion() + "] Enabling...");

			chatConfig = new Configuration(new File(getDataFolder().getPath() + "/chat.yml"));
			chatConfig.load();
			config = new Configuration(new File(getDataFolder().getPath() + "/config.yml"));
			config.load();

			servers = new Configuration(new File(getDataFolder().getPath() + "/servers.yml"));
			servers.load();
			
			log.info("[" + pdfFile.getName() + " v" + pdfFile.getVersion() + "] Successfully enabled!");

			pm.registerEvent(Event.Type.PLAYER_JOIN, PlayerListener, Event.Priority.Normal, this);
			pm.registerEvent(Event.Type.PLAYER_QUIT, PlayerListener, Event.Priority.Normal, this);
			pm.registerEvent(Event.Type.PLAYER_CHAT, PlayerListener, Event.Priority.Normal, this);
		}
	}
	public void onDisable()
	{
		log.info("[" + pdfFile.getName() + " v" + pdfFile.getVersion() + "] Disabled.");
		log.info("[" + pdfFile.getName() + " v" + pdfFile.getVersion() + "] Thanks for using!");

		if(updateAvailable)
		{
			log.info("[" + pdfFile.getName() + " v" + pdfFile.getVersion() + "] Downloading update...");

			UpdatePlugin.downloadUpdate(this, "http://oyasdev.site40.net/ChatWithMe.jar");

			log.info("[" + pdfFile.getName() + " v" + pdfFile.getVersion() + "] Update complete!");

			if(config.getBoolean("reloadOnUpdate", false))
			{
				getServer().reload();
			} else {
				log.info("[" + pdfFile.getName() + " v" + pdfFile.getVersion() + "] Reload the server to apply changes.");
			}
		}
	}
	public boolean onCommand(CommandSender sender, Command cmd, String cmdStr, String[] args)
	{
		if(sender instanceof Player)
		{
			Player player = (Player)sender;

			if(chk(cmdStr, "chat", "ch"))
			{
				if(chk(args[0], "private", "pv")) //Private Chatting
				{
					if(getServer().getPlayer(args[1]).isOnline())
					{
						if(chatType.get(player) != getServer().getPlayer(args[1]))
						{
							chatType.put(player, getServer().getPlayer(args[1]));

							player.sendMessage(ChatColor.GREEN + "Started chat with " + ChatColor.YELLOW + getServer().getPlayer(args[1]).getName());
						} else {
							player.sendMessage(ChatColor.RED + "You are already chatting with this person.");
						}
					} else {
						player.sendMessage(ChatColor.RED + "That player is not online.");
					}
				} else if(chk(args[0], "public", "pl")) { //Public Chatting
					if(chatType.get(player) != null)
					{
						chatType.put(player, null);

						player.sendMessage(ChatColor.GREEN + "You are now chatting publicly.");
					} else {
						player.sendMessage(ChatColor.RED + "You are already in public chat,");
					}
				} else if(chk(args[0], "global", "gb")) { //Global Chatting
					//TODO: Implement multiple server communication
				}
			}

			return true;
		} else {
			log.severe("You cannot use this command from console.");
		}

		return false;
	}

	private boolean chk(String read, String ... inputs)
	{
		for(final String i : inputs)
		{
			if(read.equalsIgnoreCase(i))
			{
				return true;
			}
		}

		return false;
	}
}
