package com.ogxclaw.main.bukkitosoup.warps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class WarpManager {
	
	private static HashMap<String, Warp> warps = new HashMap<String, Warp>();
	
	public static void addWarp(String name, Location location){
		if(!isWarp(name)){
			warps.put(name.toLowerCase(), new Warp(name, location));
		}
	}
	
	public static void addWarp(Warp warp){
		if(warp != null && !isWarp(warp.getName())){
			warps.put(warp.getName().toLowerCase(), warp);
		}
	}
	
	public static Warp getWarp(String name){
		if(isWarp(name)){
			return warps.get(name.toLowerCase());
		}
		return null;
	}
	
	public static void removeWarp(Warp warp){
		if(isWarp(warp)){
			warps.remove(warp.getName().toLowerCase());
		}
	}
	
	public static boolean isWarp(String name){
		return (warps.containsKey(name.toLowerCase()));
	}
	
	public static boolean isWarp(Warp warp){
		return isWarp(warp.getName().toLowerCase());
	}
	
	public static Warp deserialize(String warp){
		String args[] = warp.split(",");
		
		String name = args[0];
		String worldS = args[1];
		String xS = args[2];
		String yS = args[3];
		String zS = args[4];
		String pitchS = args[5];
		String yawS = args[6];
		
		try {
			World world = Bukkit.getWorld(worldS);
			double x = Double.parseDouble(xS);
			double y = Double.parseDouble(yS);
			double z = Double.parseDouble(zS);
			Float pitch = Float.parseFloat(pitchS);
			Float yaw = Float.parseFloat(yawS);
			
			Location loc = new Location(world, x, y, z, yaw, pitch);
			
			return new Warp(name, loc);
		}catch(Exception e){
			Bukkit.getLogger().log(Level.INFO, "Error parsing warp '" + name + "'");		
			return null;
		}
	}
	
	public static ArrayList<String> getWarpNames(){
		ArrayList<String> ret = new ArrayList<String>();
		for(String s : warps.keySet()){
			ret.add(s);
		}
		return ret;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ArrayList<Warp> getWarpObjects(){
		ArrayList<Warp> ret = new ArrayList<Warp>();
		Iterator it = warps.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Warp> entry = (Map.Entry<String, Warp>) it.next();
			ret.add(entry.getValue());
		}
		return ret;
	}
	
	public static ArrayList<String> getAvailable(CommandSender s){
		if(!WarpSettings.perWarpPerms || s.hasPermission("bukkitosoup.teleportation.warp.*") || s.hasPermission("*")){
			return getWarpNames();
		}
		
		ArrayList<String> ret = new ArrayList<String>();
		
		for(String warp : getWarpNames()){
			if(s.hasPermission("bukkitosoup.teleportation.warp." + warp.toLowerCase()) || s.hasPermission("*")){
				ret.add(warp);
			}
		}
		
		return ret;
	}
	
	public static File getWarpsFile(){
		String fName = "warps.data";
		return new File("plugins/BukkitOSoup-Main/" + fName);
	}
	
	public static void loadWarps(){
		File file = getWarpsFile();
		
		if(file.exists()){
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
				Object result = ois.readObject();
				
				ois.close();
				if(result != null){
					@SuppressWarnings("unchecked")
					ArrayList<String> parse = (ArrayList<String>) result;
					for(String i : parse){
						try {
							addWarp(deserialize(i));
							ois.close();
						}catch(Exception e){
							Bukkit.getLogger().log(Level.WARNING, "Error loading warps");
						}
					}
				}
			}catch(Exception e){
				Bukkit.getLogger().log(Level.WARNING, "Error loading warps");
			}
		}
	}
	
	public static void saveWarps(){
		File file = getWarpsFile();
		
		ArrayList<String> format = new ArrayList<String>();
		
		for(Warp w : getWarpObjects()){
			format.add(w.toString());
		}
		
		new File("plugins/").mkdir();
		new File("plugins/BukkitOSoup-Main/").mkdir();
		
		if(!file.exists()){
			try {
				file.createNewFile();
			}catch(IOException e){
				Bukkit.getLogger().log(Level.WARNING, "Error saving warps");
			}
		}
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.getAbsolutePath()));
			oos.writeObject(format);
			oos.flush();
			oos.close();
		}catch(Exception e){
			Bukkit.getLogger().log(Level.WARNING, "Error saving warps");
		}
	}

}
