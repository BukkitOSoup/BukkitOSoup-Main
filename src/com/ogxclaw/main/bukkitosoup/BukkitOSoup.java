package com.ogxclaw.main.bukkitosoup;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import com.ogxclaw.main.bukkitosoup.commands.BaseCommand;
import com.ogxclaw.main.bukkitosoup.core.InjectEvents;
import com.ogxclaw.main.bukkitosoup.core.SettingsManager;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitOSoup extends JavaPlugin implements Listener {
	
	public static BukkitOSoup instance;
	
	public BukkitOSoup(){
		instance = this;
	}
	
	@SuppressWarnings("deprecation")
        @Override
	public void onEnable(){
		//FileConfiguration config = SettingsManager.getInstance().getConfig();
		
		SettingsManager.getInstance().setup(this);
		
		SettingsManager.getInstance().injectPlayer(Bukkit.getServer().getOnlinePlayers());
		
		BaseCommand.registerCommands();
		
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getPluginManager().registerEvents(new InjectEvents(), this);
		
		/*try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		}catch(IOException e){
			Bukkit.getServer().getLogger().warning("Failed to submat stats to mcstats.org");
		}*/
		
		Bukkit.getServer().getLogger().log(Level.INFO, "BukkitOSoup-Main v{0} has been enabled.", this.getDescription().getVersion());
		
		/*if(config.getBoolean("update.autoupdate")){
			Updater updater = new Updater(this, 70303, this.getFile(), Updater.UpdateType.DEFAULT, true);
			if(updater.getResult().equals(UpdateResult.NO_UPDATE)){
				Bukkit.getServer().getLogger().info("BukkitOSoup-Main is up to date");
				return;
			}
			if(updater.getResult().equals(UpdateResult.DISABLED)){
				Bukkit.getServer().getLogger().warning("BukkitOSoup-Main failed updateing because autoupdater has been disabled.");
				return;
			}
			if(updater.getResult().equals(UpdateResult.SUCCESS)){
				Bukkit.getServer().getLogger().info("BukkitOSoup-Main has successfully been updated.");
				return;
			}else{
				Bukkit.getServer().getLogger().info("An error has occurred while updating BukkitOSoup-Main.");
				return;
			}
		}
		
		if(config.getBoolean("update.notify.console")){
			Updater updater = new Updater(this, 70303, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, true);
			if(updater.getResult().equals(UpdateResult.UPDATE_AVAILABLE)){
				Bukkit.getServer().getLogger().info("A new update is available to download for BukkitOSoup");
			}
		}	*/
	}
	
	@SuppressWarnings("deprecation")
	public void onDisable(){
		SettingsManager.getInstance().uninjectPlayer(Bukkit.getServer().getOnlinePlayers());
		Bukkit.getServer().getLogger().info("BukkitOSoup-Main has been disabled");
	}
	
	/*@EventHandler(ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent e){
		SettingsManager settings = SettingsManager.getInstance();
		if(e.getPlayer().isOp() && settings.getConfig().getBoolean("update.notify.ops")){
			Updater updater = new Updater(this, 70303, this.getFile(), Updater.UpdateType.NO_DOWNLOAD, true);
			if(updater.getResult().equals(UpdateResult.UPDATE_AVAILABLE)){
				BaseCommand.sendDirectedMessage(e.getPlayer(), "A new update is available to download.");
			}
		}
	}*/

}
