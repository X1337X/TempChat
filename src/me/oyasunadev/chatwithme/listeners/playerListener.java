package me.oyasunadev.chatwithme.listeners;

import me.oyasunadev.chatwithme.ChatWithMe;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class playerListener extends PlayerListener
{
	private ChatWithMe chatWithMe;
	public playerListener(ChatWithMe chatWithMe)
	{
		this.chatWithMe = chatWithMe;
	}

	public void onPlayerChat(PlayerChatEvent event)
	{
		Player player = event.getPlayer();
        String message = event.getMessage();
		if(chatWithMe.chatType.get(player) != null)
		{
			event.setCancelled(true);

			player.sendMessage("To " + ChatColor.YELLOW + chatWithMe.chatType.get(player).getName() + ": " + ChatColor.WHITE + event.getMessage());
			chatWithMe.chatType.get(player).sendMessage("From " + ChatColor.YELLOW + player.getName() + ": " + ChatColor.WHITE + event.getMessage());
		} else {
			event.setCancelled(true);

			for(final Player players : chatWithMe.getServer().getOnlinePlayers())
			{
				players.sendMessage(player.getDisplayName() + event.getMessage());
			}
		}
	}

	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();

		chatWithMe.chatType.put(player, null);

		String groups = chatWithMe.chatConfig.getString("groups");
		String[] groupsList;
		groupsList = groups.split(",");

		for(final String s : groupsList)
		{
			if(player.hasPermission("cwm.chat." + s))
			{
				String format = chatWithMe.chatConfig.getString(s);
				format = format.replaceAll("%aqua%", ChatColor.AQUA.toString());
				format = format.replaceAll("%black%", ChatColor.BLACK.toString());
				format = format.replaceAll("%blue%", ChatColor.BLUE.toString());
				format = format.replaceAll("%darkaqua%", ChatColor.DARK_AQUA.toString());
				format = format.replaceAll("%darkblue%", ChatColor.DARK_BLUE.toString());
				format = format.replaceAll("%darkgray%", ChatColor.DARK_GRAY.toString());
				format = format.replaceAll("%darkgreen%", ChatColor.DARK_GREEN.toString());
				format = format.replaceAll("%darkpurple%", ChatColor.DARK_PURPLE.toString());
				format = format.replaceAll("%darkred%", ChatColor.DARK_RED.toString());
				format = format.replaceAll("%gold%", ChatColor.GOLD.toString());
				format = format.replaceAll("%gray%", ChatColor.GRAY.toString());
				format = format.replaceAll("%green%", ChatColor.GREEN.toString());
				format = format.replaceAll("%lightpurple%", ChatColor.LIGHT_PURPLE.toString());
				format = format.replaceAll("%red%", ChatColor.RED.toString());
				format = format.replaceAll("%white%", ChatColor.WHITE.toString());
				format = format.replaceAll("%yellow%", ChatColor.YELLOW.toString());
				format = format.replaceAll("%name%", player.getName());

				player.setDisplayName(format);
			}
		}
	}

	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();

		if(chatWithMe.chatType.get(player) != null)
		{
			chatWithMe.chatType.put(player, null);
			chatWithMe.chatType.put(chatWithMe.chatType.get(player), null);

			chatWithMe.chatType.get(player).sendMessage(ChatColor.GREEN + "Chat ended with " + player.getName() + ".");
			chatWithMe.chatType.get(player).sendMessage(ChatColor.GREEN + "Reason: Player Disconnected.");
		}
	}
}
