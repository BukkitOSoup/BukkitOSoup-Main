package com.ogxclaw.main.bukkitosoup.commands.teleportation.warps;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.ogxclaw.main.bukkitosoup.commands.BaseCommand;
import com.ogxclaw.main.bukkitosoup.commands.BaseCommand.Name;
import com.ogxclaw.main.bukkitosoup.utils.BukkitOSoupCommandException;
import com.ogxclaw.main.bukkitosoup.utils.PermissionDeniedException;
import com.ogxclaw.main.bukkitosoup.warps.WarpManager;

@Name("warps")
public class WarpsCommand extends BaseCommand {
	
	@Override
	public boolean onCommandPlayer(Player player, Command command, String s, String[] args) throws BukkitOSoupCommandException {
		if(player.hasPermission("bukkitosoup.teleportation.warp.list") || player.hasPermission("*")){
			int page = 1;
			
			if(args.length > 0){
				try {
					page = Integer.parseInt(args[0]);
				}catch(Exception e){
					throw new BukkitOSoupCommandException("Usage: " + this.getUsage());
				}
			}
			
			page = Math.min(page, (((WarpManager.getAvailable(player).size() - 1) / 8) + 1));
			
			sendDirectedMessage(player, "Available Warps \u00a7c(" + page + "/" + (((WarpManager.getAvailable(player).size() - 1) / 8) + 1) + ")\u00a7f:");
			
			for(int i = ((page - 1) * 8); i < Math.min(WarpManager.getAvailable(player).size(), ((page - 1) * 8) + 8); i++){
				sendDirectedMessage(player, (i + 1) + ". " + WarpManager.getAvailable(player).get(i));
			}
			return true;
		}else{
			throw new PermissionDeniedException();
		}
	}

	@Override
	public String getUsage() {
		return "/warps [page]";
	}

	@Override
	public String getHelp() {
		return "Displays all available warps to teleport to";
	}

}
