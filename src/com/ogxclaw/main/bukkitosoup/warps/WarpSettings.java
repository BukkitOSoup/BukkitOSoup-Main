package com.ogxclaw.main.bukkitosoup.warps;

import java.util.logging.Level;

import com.ogxclaw.main.bukkitosoup.BukkitOSoup;
import com.ogxclaw.main.bukkitosoup.core.SettingsManager;

public class WarpSettings {
	
	private static BukkitOSoup plugin;
	public static int delay = 0;
	public static boolean perWarpPerms = false;
	public static boolean signsReqPerms = false;
	public static boolean signsPerWarpPerms = false;
	public static boolean permsBypassDelay = false;
	public static boolean warpOtherBypassDelay = false;
	public static boolean signsBypassDelay = false;
	
	public static void load(SettingsManager settings){
		loadSettings(settings);
	}
	
	public static void loadSettings(SettingsManager settings){
		try {
			WarpSettings.delay = settings.getWarpsConfig().getInt("warp-delay");
			WarpSettings.perWarpPerms = settings.getConfig().getBoolean("per-warp-permissions");
			WarpSettings.signsReqPerms = settings.getConfig().getBoolean("signs-require-permissions");
			WarpSettings.signsPerWarpPerms = settings.getConfig().getBoolean("signs-per-warp-permissions");
			WarpSettings.permsBypassDelay = settings.getConfig().getBoolean("permissions-bypass-delay");
			WarpSettings.warpOtherBypassDelay = settings.getConfig().getBoolean("warp-other-bypass-delay");
			WarpSettings.signsBypassDelay = settings.getConfig().getBoolean("signsr-bypass-delay");
		}catch(Exception e){
			plugin.getLogger().log(Level.WARNING, "Error loading config: using defaults");
		}
	}

}
