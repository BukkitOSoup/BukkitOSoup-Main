package com.ogxclaw.main.bukkitosoup.commands.bans;

import java.awt.Desktop;
import java.net.URI;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.ogxclaw.main.bukkitosoup.commands.BaseCommand;
import com.ogxclaw.main.bukkitosoup.commands.BaseCommand.Name;
import com.ogxclaw.main.bukkitosoup.utils.BukkitOSoupCommandException;
import com.ogxclaw.main.bukkitosoup.utils.PermissionDeniedException;

@Name("lookup")
public class LookupCommand extends BaseCommand {

	@Override
	public boolean onCommandPlayer(Player player, Command command, String s, String[] args) throws BukkitOSoupCommandException {
		if (player.hasPermission("bukkitosoup.lookup") || player.hasPermission("*")) {
			@SuppressWarnings("deprecation")
			Player target = player.getServer().getPlayer(args[0]);
			if (args.length >= 1) {
				sendDirectedMessage(player, "Looking up " + target.getName() + "...");
				if (Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().browse(new URI("http://www.fishbans.com/u/" + target.getName()));
						return true;
					} catch (Exception e) {
						throw new BukkitOSoupCommandException("Error with connecting to servers...");
					}
				}
			} else {
				throw new BukkitOSoupCommandException(this.getUsage());
			}
		} else {
			throw new PermissionDeniedException();
		}
		return false;
	}

	@Override
	public String getUsage() {
		return "Usage: /lookup <playername>";
	}

	@Override
	public String getHelp() {
		return "Looks up a player's previous bans and alternate accounts";
	}
}
