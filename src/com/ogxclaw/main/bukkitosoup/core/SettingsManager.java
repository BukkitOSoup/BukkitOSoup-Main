package com.ogxclaw.main.bukkitosoup.core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;

import com.ogxclaw.main.bukkitosoup.permissions.Group;

public class SettingsManager {

	private SettingsManager() {
	}

	private static SettingsManager instance = new SettingsManager();

	public static SettingsManager getInstance() {
		return instance;
	}

	private Plugin p;
	private FileConfiguration permissions;
	private FileConfiguration config;
	private FileConfiguration warp;
	private File pfile;
	private File cfile;
	private File wfile;

	private HashMap<UUID, PermissionAttachment> attachments = new HashMap<UUID, PermissionAttachment>();
	private ArrayList<Group> groups = new ArrayList<Group>();

	public void setup(Plugin p) {
		this.p = p;

		if (!p.getDataFolder().exists())
			p.getDataFolder().mkdir();

		pfile = new File(p.getDataFolder(), "permissions.yml");
		cfile = new File(p.getDataFolder(), "config.yml");
		wfile = new File(p.getDataFolder(), "warps.yml");

		if (!pfile.exists()) {
			try {
				pfile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (!cfile.exists()) {
			try {
				cfile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(!wfile.exists()){
			try {
				wfile.createNewFile();
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		permissions = YamlConfiguration.loadConfiguration(pfile);
		config = YamlConfiguration.loadConfiguration(cfile);
		warp = YamlConfiguration.loadConfiguration(wfile);

		initializeConfig();
		setupGroups();
	}

	public void initializeConfig() {
		if (pfile.length() < 1) {
			permissions.addDefault("groups.guest.default", true);
			permissions.addDefault("groups.guest.prefix", "&7[Guest] ");
			permissions.addDefault("groups.guest.suffix", "&f");
			permissions.addDefault("groups.guest.loginColor", "&7");
			permissions.addDefault("groups.guest.permissions", new ArrayList<String>());

			permissions.addDefault("groups.admin.prefix", "&2[Admin] ");
			permissions.addDefault("groups.admin.suffix", "&f");
			permissions.addDefault("groups.admin.loginColor", "&2");
			permissions.addDefault("groups.admin.inheritance", new ArrayList<String>());
			permissions.addDefault("groups.admin.permissions", new ArrayList<String>());
		}

		config.addDefault("chat.format", "&prefix&displayname&suffix: &message");
		config.addDefault("update.autoupdate", false);
		config.addDefault("update.notify.console", false);
		config.addDefault("update.notify.ops", false);
		config.addDefault("MOTD", "Welcome to BowlOCraft!");

		permissions.options().copyDefaults(true);
		config.options().copyDefaults(true);
		savePermissions();
		saveConfig();
	}

	public int getPlayerLevel(Player player) {
		List<String> groups = SettingsManager.getInstance().getGroups(player);
		String groupName = groups.get(groups.size() - 1).toString();
		Group group = SettingsManager.getInstance().getGroup(groupName);
		int level = group.getLevel();
		return level;
	}

	public void addPerm(OfflinePlayer p2, String perm) {
		if (p2 != null) {
			if (p2.isOnline()) {
				Player op = (Player) p2;
				injectPlayer(op);
			}
			attachments.get(p2.getUniqueId()).setPermission(perm, true);
		}

		List<String> perms = getPerms(p2);
		perms.add(perm);
		permissions.set("users." + p2.getUniqueId() + ".permissions", perms);

		savePermissions();
	}

	public void removePerm(OfflinePlayer p2, String perm) {
		if (p2 != null) {
			if (p2.isOnline()) {
				Player op = (Player) p2;
				injectPlayer(op);
			}
			attachments.get(p2.getUniqueId()).setPermission(perm, false);
		}

		List<String> perms = getPerms(p2);
		perms.remove(perm);
		permissions.set("users." + p2.getUniqueId() + ".permissions", perms);

		savePermissions();
	}

	public List<String> getPerms(OfflinePlayer p2) {
		if (!permissions.contains("users." + p2.getUniqueId() + ".permissions"))
			permissions.set("users." + p2.getUniqueId() + ".permissions", new ArrayList<String>());
		return permissions.getStringList("users." + p2.getUniqueId() + ".permissions");
	}

	public void setGroup(OfflinePlayer p2, String g) {
		List<String> groups = getGroups(p2);
		groups.clear();
		groups.add(g);
		permissions.set("users." + p2.getUniqueId() + ".groups", groups);

		savePermissions();

		if (p2 != null && p2.isOnline()) {
			Player op = (Player) p2;
			injectPlayer(op);
		}
	}

	public void addGroup(OfflinePlayer p2, String g) {
		List<String> groups = getGroups(p2);
		groups.add(g);
		permissions.set("users." + p2.getUniqueId() + ".groups", groups);

		savePermissions();

		if (p2 != null && p2.isOnline()) {
			Player op = (Player) p2;
			injectPlayer(op);
		}
	}

	public void removeGroup(OfflinePlayer p2, String g) {
		List<String> groups = getGroups(p2);
		groups.remove(g);
		permissions.set("users." + p2.getUniqueId() + ".groups", groups);

		savePermissions();

		if (p2 != null && p2.isOnline()) {
			Player op = (Player) p2;
			injectPlayer(op);
		}
	}

	public List<String> getGroups(OfflinePlayer p2) {
		if (!permissions.contains("users." + p2.getUniqueId() + ".groups"))
			permissions.set("users." + p2.getUniqueId() + ".groups", new ArrayList<String>());
		return permissions.getStringList("users." + p2.getUniqueId() + ".groups");
	}

	public void createGroup(String group) {
		permissions.getConfigurationSection("groups").set(group + ".permissions", new ArrayList<String>());
		savePermissions();
		setupGroups();
	}

	private void setupGroups() {
		groups.clear();

		for (String groupName : permissions.getConfigurationSection("groups").getKeys(false)) {
			groups.add(new Group(groupName));
		}
	}

	public void injectPlayer(Player... player) {
		uninjectPlayer(player);
		for (Player p : player) {
			if (attachments.get(p.getUniqueId()) == null)
				attachments.put(p.getUniqueId(), p.addAttachment(getPlugin()));

			for (Entry<String, Boolean> perm : attachments.get(p.getUniqueId()).getPermissions().entrySet()) {
				if (getPerms(p).contains(perm.getKey()))
					attachments.get(p.getUniqueId()).setPermission(perm.getKey(), true);
				else
					attachments.get(p.getUniqueId()).setPermission(perm.getKey(), false);
				if (getPerms(p).contains("*")) {
					for (Permission aperm : Bukkit.getServer().getPluginManager().getPermissions()) {
						attachments.get(p.getUniqueId()).setPermission(aperm, true);
					}
				}
			}

			if (!permissions.contains("users." + p.getUniqueId() + ".groups"))
				permissions.set("users." + p.getUniqueId() + ".groups", new ArrayList<String>());

			if (getGroups(p).isEmpty()) {
				for (String groupName : permissions.getConfigurationSection("groups").getKeys(false)) {
					if (getGroup(groupName).isDefault()) {
						addGroup(p, groupName);
						break;
					}
				}
			}

			if (getGroups(p).isEmpty()) {
				for (String groupName : permissions.getConfigurationSection("groups").getKeys(false)) {
					addGroup(p, groupName);
					break;
				}
			}

			for (String gName : permissions.getStringList("users." + p.getUniqueId() + ".groups")) {
				for (Group g : groups) {
					if (g.getName().equals(gName)) {
						for (String igName : g.getInhs()) {
							for (Group ig : groups) {
								if (ig.getName().equals(igName)) {
									for (String iperm : ig.getPerms()) {
										attachments.get(p.getUniqueId()).setPermission(iperm, true);
										if (iperm.equals("*")) {
											for (Permission aperm : Bukkit.getServer().getPluginManager().getPermissions()) {
												attachments.get(p.getUniqueId()).setPermission(aperm, true);
											}
										}
									}
								}
							}
						}
						for (String perm : g.getPerms()) {
							attachments.get(p.getUniqueId()).setPermission(perm, true);
							if (perm.equals("*")) {
								for (Permission aperm : Bukkit.getServer().getPluginManager().getPermissions()) {
									attachments.get(p.getUniqueId()).setPermission(aperm, true);
								}
							}
						}
					}
				}
			}
		}
	}

	public void uninjectPlayer(Player... player) {
		for (Player p : player) {
			if (attachments.get(p.getUniqueId()) == null)
				return;
			p.removeAttachment(attachments.get(p.getUniqueId()));
			attachments.remove(p.getUniqueId());
		}
	}

	public Group getGroup(String name) {
		for (Group g : groups) {
			if (g.getName().equals(name))
				return g;
		}
		return null;
	}

	public ConfigurationSection getGroupSection(String name) {
		return permissions.getConfigurationSection("groups." + name);
	}

	public FileConfiguration getPermissionsConfig() {
		return permissions;
	}

	public FileConfiguration getConfig() {
		return config;
	}
	
	public FileConfiguration getWarpsConfig(){
		return warp;
	}

	public void savePermissions() {
		try {
			permissions.save(pfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveConfig() {
		try {
			config.save(cfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveWarpConfig(){
		try {
			warp.save(wfile);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void reload() {
		permissions = YamlConfiguration.loadConfiguration(pfile);
		config = YamlConfiguration.loadConfiguration(cfile);
		warp = YamlConfiguration.loadConfiguration(wfile);

		uninjectPlayer(Bukkit.getServer().getOnlinePlayers());
		injectPlayer(Bukkit.getServer().getOnlinePlayers());
	}

	public Plugin getPlugin() {
		return p;
	}

}
