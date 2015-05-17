package com.ogxclaw.main.bukkitosoup.commands.permissions;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ogxclaw.main.bukkitosoup.commands.BaseCommand;
import com.ogxclaw.main.bukkitosoup.commands.BaseCommand.Name;
import com.ogxclaw.main.bukkitosoup.core.SettingsManager;
import com.ogxclaw.main.bukkitosoup.utils.BukkitOSoupCommandException;
import com.ogxclaw.main.bukkitosoup.utils.PermissionDeniedException;

@Name("setrank")
public class SetRankCommand extends BaseCommand {
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommandPlayer(Player player, Command command, String s, String[] args) throws BukkitOSoupCommandException {
		if(player.hasPermission("bukkitosoup.permissions.setrank") || player.hasPermission("*")){
			String g = args[1];
			Player target = player.getServer().getPlayer(args[0]);
			int playerLevel = SettingsManager.getInstance().getPlayerLevel(player);
			int targetLevel = SettingsManager.getInstance().getPlayerLevel(target);
			//OfflinePlayer offlineTarget = Bukkit.getServer().getOfflinePlayer(args[0]);
			if(SettingsManager.getInstance().getGroup(g) == null){
				throw new BukkitOSoupCommandException("Group " + g + " does not exist!");
			}
			if(target.isOnline() && target != null){
				if(playerLevel > targetLevel){
					SettingsManager.getInstance().setGroup(target, g);
				}else{
					throw new BukkitOSoupCommandException("You cannot set " + target.getName() + " to " + SettingsManager.getInstance().getGroup(g).getName());
				}
			}else{
				throw new BukkitOSoupCommandException("Player not online! Please set rank through the config file!");
			}
			sendServerMessage(player.getName() + " set rank of " + target.getName() + " to " + SettingsManager.getInstance().getGroup(g).getName()); 
			return true;
		}else{
			throw new PermissionDeniedException();
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommandConsole(CommandSender sender, Command command, String s, String[] args) throws BukkitOSoupCommandException {
		String g = args[1];
		Player target = sender.getServer().getPlayer(args[0]);
		OfflinePlayer offlineTarget = Bukkit.getServer().getOfflinePlayer(args[0]);
		if(SettingsManager.getInstance().getGroup(g) == null){
			throw new BukkitOSoupCommandException("Group " + g + " does not exist!");
		}
		if(target.isOnline() && target != null){
			SettingsManager.getInstance().setGroup(target, g);
		}else{
			SettingsManager.getInstance().setGroup(offlineTarget, g);
		}
		sendServerMessage("CONSOLE set rank of " + target.getName() + " to " + SettingsManager.getInstance().getGroup(g).getName()); 
		return true;
	}

	@Override
	public String getUsage() {
		return "/setrank <player> <rank>";
	}

	@Override
	public String getHelp() {
		return "Sets the rank of a player";
	}

}
