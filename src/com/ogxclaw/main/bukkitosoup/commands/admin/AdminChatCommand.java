package com.ogxclaw.main.bukkitosoup.commands.admin;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.ogxclaw.main.bukkitosoup.commands.BaseCommand;
import com.ogxclaw.main.bukkitosoup.commands.BaseCommand.Name;
import com.ogxclaw.main.bukkitosoup.utils.BukkitOSoupCommandException;
import com.ogxclaw.main.bukkitosoup.utils.PermissionDeniedException;

@Name("ac")
public class AdminChatCommand extends BaseCommand {

	@Override
	public boolean onCommandPlayer(Player player, Command command, String s, String[] args) throws BukkitOSoupCommandException {
		if (player.hasPermission("bukkitosoup.adminchat") || player.hasPermission("*")) {
			String message = "";
			for (int i = 0; i < args.length; i++) {
				message = message + args[i] + " ";
			}
			for (World w : player.getServer().getWorlds()) {
				for (Player p2 : w.getPlayers()) {
					if (p2.hasPermission("bukkitosoup.adminchat.receive") || player.hasPermission("*")) {
						p2.sendMessage("\u00a7e[AC] " + ChatColor.RESET + player.getDisplayName() + ": " + message);
					}
				}
			}
			return true;
		} else {
			throw new PermissionDeniedException();
		}
	}

	@Override
	public String getUsage() {
		return "/ac [text]";
	}

	@Override
	public String getHelp() {
		return "Inputs chat into AdminChat";
	}

}
