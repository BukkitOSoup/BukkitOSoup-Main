package com.ogxclaw.main.bukkitosoup.commands.teleportation.warps;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.ogxclaw.main.bukkitosoup.commands.BaseCommand;
import com.ogxclaw.main.bukkitosoup.commands.BaseCommand.Name;
import com.ogxclaw.main.bukkitosoup.utils.BukkitOSoupCommandException;
import com.ogxclaw.main.bukkitosoup.utils.PermissionDeniedException;
import com.ogxclaw.main.bukkitosoup.warps.Warp;
import com.ogxclaw.main.bukkitosoup.warps.WarpHelper;
import com.ogxclaw.main.bukkitosoup.warps.WarpManager;

@Name("warp")
public class WarpCommand extends BaseCommand {
	
	@Override
	public boolean onCommandPlayer(Player player, Command command, String s, String[] args) throws BukkitOSoupCommandException {
		if(player.hasPermission("bukkitosoup.teleportation.warp") || player.hasPermission("*")){
			if(args.length == 0){
				throw new BukkitOSoupCommandException("Usage: " + this.getUsage());
			}
			
			if(args.length > 0){
				String warpName = args[0];
				
				if(!WarpManager.isWarp(warpName)){
					throw new BukkitOSoupCommandException("Warp not found");
				}
				
				Warp to = WarpManager.getWarp(warpName);
				
				WarpHelper.warpSelf(player, to);
				return true;
			}else{
				throw new BukkitOSoupCommandException("Usage: " + this.getUsage());
			}
		}else{
			throw new PermissionDeniedException();
		}
	}

	@Override
	public String getUsage() {
		return "/warp <name>";
	}

	@Override
	public String getHelp() {
		return "Warps a player to the specified warp";
	}

}
