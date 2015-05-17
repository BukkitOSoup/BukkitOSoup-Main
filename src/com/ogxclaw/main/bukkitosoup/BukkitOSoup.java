package com.ogxclaw.main.bukkitosoup;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.ogxclaw.main.bukkitosoup.commands.BaseCommand;
import com.ogxclaw.main.bukkitosoup.core.InjectEvents;
import com.ogxclaw.main.bukkitosoup.core.SettingsManager;
import com.ogxclaw.main.bukkitosoup.warps.WarpManager;

public class BukkitOSoup extends JavaPlugin implements Listener {

	public static BukkitOSoup instance;

	public BukkitOSoup() {
		instance = this;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		// FileConfiguration config = SettingsManager.getInstance().getConfig();

		SettingsManager.getInstance().setup(this);

		SettingsManager.getInstance().injectPlayer(Bukkit.getServer().getOnlinePlayers());

		BaseCommand.registerCommands();
		
		WarpManager.loadWarps();

		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getPluginManager().registerEvents(new InjectEvents(), this);

		Bukkit.getServer().getLogger().log(Level.INFO, "BukkitOSoup-Main v{0} has been enabled.", this.getDescription().getVersion());
	}

	@SuppressWarnings("deprecation")
	public void onDisable() {
		WarpManager.saveWarps();
		SettingsManager.getInstance().uninjectPlayer(Bukkit.getServer().getOnlinePlayers());
		Bukkit.getServer().getLogger().info("BukkitOSoup-Main has been disabled");
	}
}
