package com.ogxclaw.main.bukkitosoup.commands.teleportation.warps;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.ogxclaw.main.bukkitosoup.commands.BaseCommand;
import com.ogxclaw.main.bukkitosoup.commands.BaseCommand.Name;
import com.ogxclaw.main.bukkitosoup.utils.BukkitOSoupCommandException;
import com.ogxclaw.main.bukkitosoup.utils.PermissionDeniedException;
import com.ogxclaw.main.bukkitosoup.warps.WarpManager;

@Name("setwarp")
public class SetWarpCommand extends BaseCommand {
	
	@Override
	public boolean onCommandPlayer(Player player, Command command, String s, String[] args) throws BukkitOSoupCommandException {
		if(player.hasPermission("bukkitosoup.teleportation.warp.setwarp") || player.hasPermission("*")){
			if(WarpManager.isWarp(args[0])){
				throw new BukkitOSoupCommandException("This warp already exists!");
			}
			WarpManager.addWarp(args[0], player.getLocation());
			
			sendDirectedMessage(player, "Warp \"" + args[0] + "\" set.");
			WarpManager.saveWarps();
			return true;
		}else{
			throw new PermissionDeniedException();
		}
	}

	@Override
	public String getUsage() {
		return "/setwarp <name>";
	}

	@Override
	public String getHelp() {
		return "Sets the warp at the player's current location";
	}

}
