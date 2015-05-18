package com.ogxclaw.main.bukkitosoup.warps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.ogxclaw.main.bukkitosoup.core.SettingsManager;

public class Spawn {

	private Spawn() {
	}

	private static Spawn instance = new Spawn();

	public static Spawn getInstance() {
		return instance;
	}
	
	public void setSpawnAtPlayerLocation(Player player){
		String worldName = player.getLocation().getWorld().getName();
		double x = player.getLocation().getX();
		double y = player.getLocation().getY();
		double z = player.getLocation().getZ();
		float yaw = player.getLocation().getYaw();
		float pitch = player.getLocation().getPitch();
		World world = Bukkit.getWorld(worldName);
		player.getWorld().setSpawnLocation((int)x, (int)y, (int)z);
		SettingsManager.getInstance().getConfig().set("Server.Spawn.World", world.getName());
		SettingsManager.getInstance().getConfig().set("Server.Spawn.X", x);
		SettingsManager.getInstance().getConfig().set("Server.Spawn.Y", y);
		SettingsManager.getInstance().getConfig().set("Server.Spawn.Z", z);
		SettingsManager.getInstance().getConfig().set("Server.Spawn.yaw", yaw);
		SettingsManager.getInstance().getConfig().set("Server.Spawn.pitch", pitch);
		SettingsManager.getInstance().saveConfig();
	}
	
	public void teleportPlayerToSpawn(Player player){
		String w = SettingsManager.getInstance().getConfig().getString("Server.Spawn.World");
		double x = SettingsManager.getInstance().getConfig().getDouble("Server.Spawn.X");
		double y = SettingsManager.getInstance().getConfig().getDouble("Server.Spawn.Y");
		double z = SettingsManager.getInstance().getConfig().getDouble("Server.Spawn.Z");
		float yaw = (float) SettingsManager.getInstance().getConfig().getDouble("Server.Spawn.Yaw");
		float pitch = (float) SettingsManager.getInstance().getConfig().getDouble("Server.Spawn.Pitch");
		World world = Bukkit.getWorld(w);
		player.teleport(new Location(world, x, y, z, yaw, pitch));
	}

}
