package com.ogxclaw.main.bukkitosoup.commands.general;

import com.ogxclaw.main.bukkitosoup.commands.BaseCommand;
import com.ogxclaw.main.bukkitosoup.commands.BaseCommand.Name;

@Name("help")
public class HelpCommand extends BaseCommand {

	@Override
	public String getUsage() {
		return "/help [command]";
	}

	@Override
	public String getHelp() {
		return "Displays all available commands or information on a specific command";
	}

	/*@Override
	public boolean onCommandPlayer(Player player, Command command, String s,
			String[] args) throws BukkitOSoupCommandException {
		String ret = "";
		if (player.hasPermission(Permissions.commandHelp)) {
			if (args.length == 0) {
				if (player.hasPermission(Permissions.commandAdminChat)) {
					ret = ret + "/.\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandAccept)) {
					ret = ret + "/accept\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandBack)) {
					ret = ret + "/back\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandBan)) {
					ret = ret + "/ban\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandButcher)) {
					ret = ret + "/butcher\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandClear)) {
					ret = ret + "/clear\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandCompass)) {
					ret = ret + "/compass\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandDelJail)) {
					ret = ret + "/deljail\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandDelWarp)) {
					ret = ret + "/delwarp\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandDemote)) {
					ret = ret + "/demote\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandDeny)) {
					ret = ret + "/deny\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandFly)) {
					ret = ret + "/fly\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandFullbright)) {
					ret = ret + "/fullbright\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandGamemode)) {
					ret = ret + "/gamemode\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandGive)) {
					ret = ret + "/give\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandHelp)) {
					ret = ret + "/help\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandHome)) {
					ret = ret + "/home\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandJail)) {
					ret = ret + "/jail\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandKick)) {
					ret = ret + "/kick\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandKickAll)) {
					ret = ret + "/kickall\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandLeash)) {
					ret = ret + "/leash\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandLockdown)) {
					ret = ret + "/lockdown\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandMe)) {
					ret = ret + "/me\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandMute)) {
					ret = ret + "/mute\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandMuteAll)) {
					ret = ret + "/muteall\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandNoPort)) {
					ret = ret + "/noport\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandNoSummon)) {
					ret = ret + "/nosummon\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandNoTp)) {
					ret = ret + "/notp\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandPM)) {
					ret = ret + "/pm\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandPromote)) {
					ret = ret + "/promote\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandRage)) {
					ret = ret + "/rage\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandAbuse)) {
					ret = ret + "/report\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandSetHome)) {
					ret = ret + "/sethome\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandSetJail)) {
					ret = ret + "/setjail\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandSetnick)) {
					ret = ret + "/setnick\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandSetrank)) {
					ret = ret + "/setrank\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandSetSpawn)) {
					ret = ret + "/setspawn\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandSpeed)) {
					ret = ret + "/speed\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandSpy)) {
					ret = ret + "/spy\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandStaff)) {
					ret = ret + "/staff\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandSummon)) {
					ret = ret + "/summon\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandSummonA)) {
					ret = ret + "/summona\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandTp)) {
					ret = ret + "/tp\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandTpA)) {
					ret = ret + "/tpa\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandTime)) {
					ret = ret + "/time\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandUnban)) {
					ret = ret + "/unban\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandUnjail)) {
					ret = ret + "/unjail\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandWarp)) {
					ret = ret + "/warp\u00a77,\u00a7f ";
				}
				if (player.hasPermission(Permissions.commandWho)) {
					ret = ret + "/who";
				}

				sendDirectedMessage(player, "Available commands: " + ret);
			} else {
				return true;
			}
			return true;
		} else {
			throw new PermissionDeniedException();
		}
	}*/

}
