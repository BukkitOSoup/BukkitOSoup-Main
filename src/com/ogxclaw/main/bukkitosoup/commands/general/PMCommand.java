package com.ogxclaw.main.bukkitosoup.commands.general;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.ogxclaw.main.bukkitosoup.commands.BaseCommand;
import com.ogxclaw.main.bukkitosoup.commands.BaseCommand.Name;
import com.ogxclaw.main.bukkitosoup.utils.BukkitOSoupCommandException;
import com.ogxclaw.main.bukkitosoup.utils.PermissionDeniedException;

@Name("pm")
public class PMCommand extends BaseCommand {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommandPlayer(Player player, Command command, String s, String[] args) throws BukkitOSoupCommandException {
		if (player.hasPermission("bukkitosoup.pm") || player.hasPermission("*")) {
			Player target = player.getServer().getPlayer(args[0]);
			String message = "";
			for (int i = 1; i < args.length; i++) {
				message = message + args[i] + " ";
			}
			if (target != null) {
				for (World w : player.getServer().getWorlds()) {
					for (Player p2 : w.getPlayers()) {
						if (p2.getName() == target.getName()) {
							p2.sendMessage("\u00a7e[PM][\u00a76" + player.getName() + " \u00a7e-> \u00a76" + target.getName() + "\u00a7e] \u00a7f" + message);
							player.sendMessage("\u00a7e[PM][\u00a76" + player.getName() + " \u00a7e-> \u00a76" + target.getName() + "\u00a7e] \u00a7f" + message);
							return true;
						}
					}
				}
			} else {
				throw new BukkitOSoupCommandException("Player not online!");
			}
		} else {
			throw new PermissionDeniedException();
		}
		return false;
	}

	@Override
	public String getUsage() {
		return "/pm <player> [message]";
	}

	@Override
	public String getHelp() {
		return "Messages a player privately";
	}

}
