package com.ogxclaw.main.bukkitosoup.warps;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.ogxclaw.main.bukkitosoup.commands.BaseCommand;

public class WarpHelper {
	
	public static ArrayList<WarpTimer> warpTimers = new ArrayList<WarpTimer>();
	
	public static void warpOther(Player player, Warp to){
		if(WarpSettings.delay == 0 || (WarpSettings.warpOtherBypassDelay) || (WarpSettings.permsBypassDelay && player.hasPermission("bukkitosoup.teleportation.warp.delay.bypass") || player.hasPermission("*"))){
			player.teleport(to.getLocation()); 
			BaseCommand.sendDirectedMessage(player, "You have been warped to " + to.getName());
			return;
		}
		
		delayedTeleport(player, to);
	}
	
	public static void warpSelf(Player player, Warp to){
		if(WarpSettings.delay == 0 || (WarpSettings.permsBypassDelay && player.hasPermission("bukkitosoup.teleportation.warp.delay.bypass") || player.hasPermission("*"))){
			player.teleport(to.getLocation());
			BaseCommand.sendDirectedMessage(player, "You have been warped to " + to.getName());
			return;
		}
		
		delayedTeleport(player, to);
	}
	
	public static void warpSign(Player player, Warp to){
		if(WarpSettings.delay == 0 || (WarpSettings.permsBypassDelay && player.hasPermission("bukkitosoup.teleportation.warp.delay.bypass") || player.hasPermission("*") || (WarpSettings.signsBypassDelay))){
			player.teleport(to.getLocation());
			BaseCommand.sendDirectedMessage(player, "You have been warped to " + to.getName());
			return;
		}
		
		delayedTeleport(player, to);
	}
	
	public static void delayedTeleport(Player player, Warp to){
		BaseCommand.sendDirectedMessage(player, "You will be warped in \u00a7c" + WarpSettings.delay + " \u00a7fseconds. Don't move.");
		if(isWarping(player)){
			stopWarping(player);
		}
		
		WarpTimer warpTimer = new WarpTimer(player, to);
		warpTimer.id = Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), warpTimer, 20L * WarpSettings.delay);
		
		warpTimers.add(warpTimer);
	}
	
	public static boolean isWarping(Player player){
		for(WarpTimer t : warpTimers){
			if(t.player.getName().equalsIgnoreCase(player.getName())){
				return true;
			}
		}
		return false;
	}
	
	public static void stopWarping(Player player){
		if(isWarping(player)){
			WarpTimer warpTimer = null;
			
			for(WarpTimer t : warpTimers){
				if(t.player.getName().equalsIgnoreCase(player.getName())){
					warpTimer = t;
					break;
				}
			}
			
			if(warpTimer != null){
				Bukkit.getScheduler().cancelTask(warpTimer.id);
				warpTimers.remove(warpTimer);
			}
		}
	}
	
	public static Plugin getPlugin(){
		return Bukkit.getPluginManager().getPlugin("BukkitOSoup-Main");
	}

}
