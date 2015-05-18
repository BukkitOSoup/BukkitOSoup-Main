package com.ogxclaw.main.bukkitosoup.commands.teleportation.spawn;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.ogxclaw.main.bukkitosoup.commands.BaseCommand;
import com.ogxclaw.main.bukkitosoup.commands.BaseCommand.Name;
import com.ogxclaw.main.bukkitosoup.core.SettingsManager;
import com.ogxclaw.main.bukkitosoup.utils.BukkitOSoupCommandException;
import com.ogxclaw.main.bukkitosoup.utils.PermissionDeniedException;
import com.ogxclaw.main.bukkitosoup.warps.Spawn;

@Name("setspawn")
public class SetSpawnCommand extends BaseCommand {
	
	@Override
	public boolean onCommandPlayer(Player player, Command command, String s, String[] args) throws BukkitOSoupCommandException {
		if(player.hasPermission("bukkitosoup.teleportation.spawn.set") || player.hasPermission("*")){
			if(args.length == 0){
				Spawn.getInstance().setSpawnAtPlayerLocation(player);
				SettingsManager.getInstance().saveConfig();
				sendDirectedMessage(player, "Spawn location set!");
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
		return "/setspawn";
	}

	@Override
	public String getHelp() {
		return "Sets the spawn for the server";
	}

}
