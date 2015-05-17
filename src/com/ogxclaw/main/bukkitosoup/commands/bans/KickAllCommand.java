package com.ogxclaw.main.bukkitosoup.commands.bans;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ogxclaw.main.bukkitosoup.commands.BaseCommand;
import com.ogxclaw.main.bukkitosoup.commands.BaseCommand.Name;
import com.ogxclaw.main.bukkitosoup.utils.BukkitOSoupCommandException;
import com.ogxclaw.main.bukkitosoup.utils.PermissionDeniedException;

@Name("kickall")
public class KickAllCommand extends BaseCommand {

	@Override
	public boolean onCommandPlayer(Player player, Command comand, String s, String[] args) throws BukkitOSoupCommandException {
		if (player.hasPermission("bukkitosoup.kickall") || player.hasPermission("*")) {
			for (World w : plugin.getServer().getWorlds()) {
				for (Player p2 : w.getPlayers()) {
					if (!p2.hasPermission("bukkitosoup.kickall.exempt") || p2.hasPermission("*")) {
						String reason = "";
						for (int i = 1; i < args.length; i++) {
							reason = reason + args[i] + " ";
						}
						p2.kickPlayer("Kicked by " + player.getName() + " Reason:" + reason);
						sendServerMessage(player.getName() + " kicked all players!");
						return true;
					}
				}
			}
		} else {
			throw new PermissionDeniedException();
		}
		return false;
	}

	@Override
	public boolean onCommandConsole(CommandSender sender, Command comand, String s, String[] args) throws BukkitOSoupCommandException {
		for (World w : plugin.getServer().getWorlds()) {
			for (Player p2 : w.getPlayers()) {
				if (!p2.hasPermission("bukkitosoup.kickall.exempt") || p2.hasPermission("*")) {
					String reason = "";
					for (int i = 1; i < args.length; i++) {
						reason = reason + args[i] + " ";
					}
					p2.kickPlayer("Kicked by CONSOLE. Reason:" + reason);
					sendServerMessage("CONSOLE kicked all players!");
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String getUsage() {
		return "/kickall [reason]";
	}

	@Override
	public String getHelp() {
		return "Kicks all players on the server";
	}

}
