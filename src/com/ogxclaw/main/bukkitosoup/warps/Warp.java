package com.ogxclaw.main.bukkitosoup.warps;

import org.bukkit.Location;

public class Warp {
	
	private String name;
	private Location location;
	
	public Warp(String name, Location location){
		setName(name);
		setLocation(location);
	}
	
	public void setName(String s){
		name = s;
	}
	
	public void setLocation(Location location){
		this.location = location;
	}
	
	public String getName(){
		return name;
	}
	
	public Location getLocation(){
		return location;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder(name + ",");
		
		String world = getLocation().getWorld().getName();
		String x = getLocation().getX() + "";
		String y = getLocation().getY() + "";
		String z = getLocation().getZ() + "";
		String pitch = getLocation().getPitch() + "";
		String yaw = getLocation().getYaw() + "";
		
		sb.append(world + ",");
		sb.append(x + ",");
		sb.append(y + ",");
		sb.append(z + ",");
		sb.append(pitch + ",");
		sb.append(yaw + ",");
		
		return sb.toString();
	}

}
