package com.ogxclaw.main.bukkitosoup.core;

import java.util.List;

import org.bukkit.entity.Player;

import com.ogxclaw.main.bukkitosoup.permissions.Group;

public class PlayerHandler {

	public PlayerHandler() {
	}

	private static PlayerHandler instance = new PlayerHandler();

	public static PlayerHandler getInstance() {
		return instance;
	}

	public String getRank(Player player) {
		List<String> groups = SettingsManager.getInstance().getGroups(player);
		String groupname = groups.get(groups.size() - 1).toString();
		return groupname;
	}

	public String getFullDisplayName(Player player) {
		List<String> groups = SettingsManager.getInstance().getGroups(player);
		String groupname = groups.get(groups.size() - 1).toString();
		Group group = SettingsManager.getInstance().getGroup(groupname);
		String prefix = group.getPrefix().replaceAll("&", "\u00A7");
		String suffix = group.getSuffix().replaceAll("&", "\u00A7");
		String displayName = player.getDisplayName();
		return prefix + displayName + suffix;
	}
	
	public int getLevel(Player player){
		List<String> groups = SettingsManager.getInstance().getGroups(player);
		String groupname = groups.get(groups.size() - 1).toString();
		Group group = SettingsManager.getInstance().getGroup(groupname);
		int level = group.getLevel();
		return level;
	}
}
