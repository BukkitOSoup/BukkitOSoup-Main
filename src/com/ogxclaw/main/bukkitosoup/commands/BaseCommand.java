package com.ogxclaw.main.bukkitosoup.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ogxclaw.main.bukkitosoup.BukkitOSoup;
import com.ogxclaw.main.bukkitosoup.utils.BukkitOSoupCommandException;
import com.ogxclaw.main.bukkitosoup.utils.Utils;

public abstract class BaseCommand implements CommandExecutor {

	public final BukkitOSoup plugin;

	public BaseCommand() {
		this.plugin = BukkitOSoup.instance;
	}

	@Retention(RetentionPolicy.RUNTIME)
	public @interface Name {
		String value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	public @interface Permission {
		String value();
	}

	public abstract String getUsage();

	public abstract String getHelp();

	@Override
	public final boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		try {
			return onCommandAll(commandSender, command, s, strings);
		} catch (Exception e) {
			sendDirectedMessage(commandSender, e.getMessage(), '4');
			return false;
		}
	}

	public boolean onCommandAll(CommandSender commandSender, Command command, String s, String[] strings) throws BukkitOSoupCommandException {
		if (commandSender instanceof Player || commandSender instanceof OfflinePlayer) {
			return onCommandPlayer((Player) commandSender, command, s, strings);
		} else {
			return onCommandConsole(commandSender, command, s, strings);
		}
	}

	public boolean onCommandConsole(CommandSender commandSender, Command command, String s, String[] strings) throws BukkitOSoupCommandException {
		sendDirectedMessage(commandSender, "Sorry, this command can not be used from the console!", '4');
		return false;
	}

	public boolean onCommandPlayer(Player player, Command command, String s, String[] strings) throws BukkitOSoupCommandException {
		sendDirectedMessage(player, "Sorry, this command cannot be used by a player!", '4');
		return false;
	}

	public static void registerCommands() {
		List<Class<? extends BaseCommand>> commands = Utils.getSubClasses(BaseCommand.class);
		for (Class<? extends BaseCommand> command : commands) {
			registerCommand(command);
		}
	}

	private static void registerCommand(Class<? extends BaseCommand> commandClass) {
		try {
			Constructor<? extends BaseCommand> ctor = commandClass.getConstructor();
			BaseCommand command = ctor.newInstance();
			if (!commandClass.isAnnotationPresent(Name.class))
				return;
			BukkitOSoup.instance.getCommand(commandClass.getAnnotation(Name.class).value()).setExecutor(command);
		} catch (Exception e) {
		}
	}

	@SuppressWarnings("deprecation")
	protected Player getPlayerSingle(String name) throws Exception {
		List<Player> ret = plugin.getServer().matchPlayer(name);
		if (ret == null || ret.isEmpty()) {
			throw new Exception("Sorry, no player found!");
		} else if (ret.size() > 1) {
			throw new Exception("Sorry, multiple players found!");
		}
		return ret.get(0);
	}

	public static void sendServerMessage(String msg) {
		sendServerMessage(msg, '5');
	}

	public static void sendServerMessage(String msg, char colorCode) {
		msg = "\u00a7" + colorCode + "[BoS]\u00a7f " + msg;
		Bukkit.broadcastMessage(msg);
	}

	public static void sendServerMessage(String message, String permission) {
		sendServerMessage(message, permission, '5');
	}

	public static void sendServerMessage(String message, String permission, char colorCode) {
		broadcastMessage("\u00a7" + colorCode + "[BoS]\u00a7f " + message, permission);
	}

	public static void broadcastMessage(String message, String permission) {
		@SuppressWarnings("deprecation")
		Player[] players = Bukkit.getOnlinePlayers();

		for (Player player : players) {
			if (!player.hasPermission(permission))
				continue;

			player.sendMessage(message);
		}
	}

	public static void sendServerMessage(String msg, CommandSender... exceptPlayers) {
		sendServerMessage(msg, '5', exceptPlayers);
	}

	public static void sendServerMessage(String msg, char colorCode, CommandSender... exceptPlayers) {
		msg = "\u00a7" + colorCode + "[BoS]\u00a7f " + msg;

		Set<Player> exceptPlayersSet = new HashSet<>();
		for (CommandSender exceptPlayer : exceptPlayers) {
			if (!(exceptPlayer instanceof Player))
				continue;

			exceptPlayersSet.add((Player) exceptPlayer);
		}

		@SuppressWarnings("deprecation")
		Player[] players = Bukkit.getOnlinePlayers();

		for (Player player : players) {
			if (exceptPlayersSet.contains(player))
				continue;

			player.sendMessage(msg);
		}
	}

	public static void sendDirectedMessage(CommandSender commandSender, String msg, char colorCode) {
		commandSender.sendMessage("\u00a7" + colorCode + "[BoS]\u00a7f " + msg);
	}

	public static void sendDirectedMessage(CommandSender commandSender, String msg) {
		sendDirectedMessage(commandSender, msg, '5');
	}

}
