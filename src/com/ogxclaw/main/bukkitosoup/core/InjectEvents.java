package com.ogxclaw.main.bukkitosoup.core;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.ogxclaw.main.bukkitosoup.permissions.Group;

public class InjectEvents implements Listener {

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent e) {
		SettingsManager.getInstance().injectPlayer(e.getPlayer());
		List<String> groups = SettingsManager.getInstance().getGroups(e.getPlayer());
		String groupName = groups.get(groups.size() - 1).toString();
		Group group = SettingsManager.getInstance().getGroup(groupName);
		String loginColor = group.getLoginColor().replaceAll("&", "\u00a7");
		String displayName = e.getPlayer().getDisplayName();
		e.setJoinMessage("\u00a72[+] \u00a7ePlayer " + loginColor + displayName + "\u00a7e connected");
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent e) {
		List<String> groups = SettingsManager.getInstance().getGroups(e.getPlayer());
		String groupName = groups.get(groups.size() - 1).toString();
		Group group = SettingsManager.getInstance().getGroup(groupName);
		String loginColor = group.getLoginColor().replaceAll("&", "\u00a7");
		String displayName = e.getPlayer().getDisplayName();
		e.setQuitMessage("\u00a74[-] \u00a7ePlayer " + loginColor + displayName + "\u00a7e disconnected");
		SettingsManager.getInstance().uninjectPlayer(e.getPlayer());
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		List<String> groups = SettingsManager.getInstance().getGroups(e.getPlayer());
		String groupname = groups.get(groups.size() - 1).toString();
		Group group = SettingsManager.getInstance().getGroup(groupname);
		String prefix = group.getPrefix().replaceAll("&", "\u00A7");
		String suffix = group.getSuffix().replaceAll("&", "\u00A7");
		String displayname = e.getPlayer().getDisplayName();
		String message = e.getMessage();
		e.setFormat(SettingsManager.getInstance().getConfig().getString("chat.format").replaceAll("&prefix", prefix).replaceAll("&displayname", displayname).replaceAll("&suffix", suffix).replaceAll("&message", message).replace("&white", "\u00a7f"));
	}

}
