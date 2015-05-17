package com.ogxclaw.main.bukkitosoup.commands.general;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.ogxclaw.main.bukkitosoup.commands.BaseCommand;
import com.ogxclaw.main.bukkitosoup.commands.BaseCommand.Name;
import com.ogxclaw.main.bukkitosoup.utils.BukkitOSoupCommandException;
import com.ogxclaw.main.bukkitosoup.utils.PermissionDeniedException;

@Name("me")
public class MeCommand extends BaseCommand {
	
	@Override
	public boolean onCommandPlayer(Player player, Command command, String s, String[] args) throws BukkitOSoupCommandException {
		if(player.hasPermission("bukkitosoup.me") || player.hasPermission("*")){
			String message = "";
			for(int i = 0; i < args.length; i++){
				message = message + args[i] + " ";
			}
			player.getServer().broadcastMessage("\u00a77* " + ChatColor.RESET + player.getDisplayName() + " \u00a77" + message);
			return true;
		}else{
			throw new PermissionDeniedException();
		}
	}

	@Override
	public String getUsage() {
		return "/me [message]";
	}

	@Override
	public String getHelp() {
		return "Well, /me";
	}

}
