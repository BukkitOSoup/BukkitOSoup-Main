package com.ogxclaw.main.bukkitosoup.warps;

import org.bukkit.entity.Player;

import com.ogxclaw.main.bukkitosoup.commands.BaseCommand;

public class WarpTimer implements Runnable {
	
	public Player player = null;
	public Warp warp = null;
	
	public int id = 0;
	
	public WarpTimer(Player p, Warp w){
		player = p;
		warp = w;
	}
	
	@Override
	public void run(){
		WarpHelper.stopWarping(player);
		
		player.teleport(warp.getLocation());
		BaseCommand.sendDirectedMessage(player, "You have been warped to " + warp.getName());
	}

}
