package com.ogxclaw.main.bukkitosoup.commands.teleportation.spawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.ogxclaw.main.bukkitosoup.commands.BaseCommand;
import com.ogxclaw.main.bukkitosoup.commands.BaseCommand.Name;
import com.ogxclaw.main.bukkitosoup.core.SettingsManager;
import com.ogxclaw.main.bukkitosoup.utils.BukkitOSoupCommandException;
import com.ogxclaw.main.bukkitosoup.utils.PermissionDeniedException;

@Name("spawn")
public class SpawnCommand extends BaseCommand {

	@Override
	public boolean onCommandPlayer(Player player, Command command, String s, String[] args) throws BukkitOSoupCommandException {
		if (player.hasPermission("bukkitosoup.teleportation.spawn") || player.hasPermission("*")) {
			String w = SettingsManager.getInstance().getConfig().getString("Server.Spawn.World");
			double x = SettingsManager.getInstance().getConfig().getDouble("Server.Spawn.X");
			double y = SettingsManager.getInstance().getConfig().getDouble("Server.Spawn.Y");
			double z = SettingsManager.getInstance().getConfig().getDouble("Server.Spawn.Z");
			float yaw = (float) SettingsManager.getInstance().getConfig().getDouble("Server.Spawn.Yaw");
			float pitch = (float) SettingsManager.getInstance().getConfig().getDouble("Server.Spawn.Pitch");
			World world = Bukkit.getWorld(w);
			player.teleport(new Location(world, x, y, z, yaw, pitch));
			SettingsManager.getInstance().saveConfig();
			sendDirectedMessage(player, "Returned to Spawn!");
			return true;
		}else{
			throw new PermissionDeniedException();
		}
	}

	@Override
	public String getUsage() {
		return "/spawn";
	}

	@Override
	public String getHelp() {
		return "Teleports you to spawn";
	}

}
