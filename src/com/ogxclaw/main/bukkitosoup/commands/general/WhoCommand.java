package com.ogxclaw.main.bukkitosoup.commands.general;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.ogxclaw.main.bukkitosoup.commands.BaseCommand;
import com.ogxclaw.main.bukkitosoup.commands.BaseCommand.Name;
import com.ogxclaw.main.bukkitosoup.core.PlayerHandler;
import com.ogxclaw.main.bukkitosoup.utils.BukkitOSoupCommandException;
import com.ogxclaw.main.bukkitosoup.utils.PermissionDeniedException;

@Name("who")
public class WhoCommand extends BaseCommand {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommandPlayer(Player player, Command command, String s, String[] args) throws BukkitOSoupCommandException {
		if (player.hasPermission("bukkitosoup.who") || player.hasPermission("*")) {
			if (args.length == 0) {
				Player[] players = plugin.getServer().getOnlinePlayers();
				String ret = "";
				if (players.length > 0) {
					for (Player ply : players) {
						String name = ply.getName();
						ret += ", " + name;
					}
					ret = ret.substring(2).trim();
				}
				ret = "Connected players: " + ret;
				sendDirectedMessage(player, ret);
				return true;
			} else {
				if (player.hasPermission("bukkitosoup.who.players") || player.hasPermission("*")) {
					Player target = player.getServer().getPlayer(args[0]);
					/*
					 * List<String> groups =
					 * SettingsManager.getInstance().getGroups(target); String
					 * groupname = groups.get(groups.size()-1).toString(); Group
					 * group =
					 * SettingsManager.getInstance().getGroup(groupname); String
					 * prefix = group.getPrefix().replaceAll("&", "\u00A7");
					 * String suffix = group.getSuffix().replaceAll("&",
					 * "\u00A7"); String displayname = target.getDisplayName();
					 */
					sendDirectedMessage(player, "Name: " + target.getName());
					sendDirectedMessage(player, "Rank: " + PlayerHandler.getInstance().getRank(target));
					sendDirectedMessage(player, "NameTag: " + PlayerHandler.getInstance().getFullDisplayName(target));
					sendDirectedMessage(player, "World: " + target.getWorld().getName());
					if(player.hasPermission("bukkitosoup.who.address") || player.hasPermission("*")){
						sendDirectedMessage(player, "IP: " + target.getAddress().getAddress().getHostAddress());
					}
					return true;
				}
				return false;
			}
		} else {
			throw new PermissionDeniedException();
		}
	}

	@Override
	public String getUsage() {
		return "/who [player]";
	}

	@Override
	public String getHelp() {
		return "Displays a list of online players or information on a specific player";
	}
}
