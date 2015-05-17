package com.ogxclaw.main.bukkitosoup.commands.teleportation.warps;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.ogxclaw.main.bukkitosoup.commands.BaseCommand;
import com.ogxclaw.main.bukkitosoup.commands.BaseCommand.Name;
import com.ogxclaw.main.bukkitosoup.utils.BukkitOSoupCommandException;
import com.ogxclaw.main.bukkitosoup.utils.PermissionDeniedException;
import com.ogxclaw.main.bukkitosoup.warps.Warp;
import com.ogxclaw.main.bukkitosoup.warps.WarpManager;

@Name("delwarp")
public class DelWarpCommand extends BaseCommand {
	
	@Override
	public boolean onCommandPlayer(Player player, Command command, String s, String[] args) throws BukkitOSoupCommandException {
		if(player.hasPermission("bukkitosoup.teleportation.warp.delwarp") || player.hasPermission("*")){
			if(!WarpManager.isWarp(args[0])){
				throw new BukkitOSoupCommandException("This warp does not exist!");
			}
			
			Warp remove = WarpManager.getWarp(args[0]);
			
			sendDirectedMessage(player, "Warp " + args[0] + " removed");
			WarpManager.removeWarp(remove);
			WarpManager.saveWarps();
			return true;
		}else{
			throw new PermissionDeniedException();
		}
	}

	@Override
	public String getUsage() {
		return "/delwarp <name>";
	}

	@Override
	public String getHelp() {
		return "Deletes the selected warp";
	}

}
