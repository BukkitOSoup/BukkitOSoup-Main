package com.ogxclaw.main.bukkitosoup.permissions;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import com.ogxclaw.main.bukkitosoup.core.SettingsManager;

public class Group {
	
	private String name;
	private boolean isDefault;
	private String prefix;
	private String suffix;
	private ArrayList<String> inhs;
	private ArrayList<String> perms;
	private String loginColor;
	private int level;
	
	public Group(String name){
		ConfigurationSection s = SettingsManager.getInstance().getGroupSection(name);
		
		this.name = name;
		
		if(!SettingsManager.getInstance().getGroupSection(name).contains("default")){
			this.isDefault = s.getBoolean("default");
		}else{
			this.isDefault = false;
		}
		
		this.prefix = s.getString("prefix");
		this.suffix = s.getString("suffix");
		this.loginColor = s.getString("loginColor");
		this.level = s.getInt("level");
		
		if(!s.contains("inheritance"))
			s.set("inheritance", new ArrayList<String>());
		this.inhs = new ArrayList<String>(s.getStringList("inheritance"));
		
		if(!s.contains("permissions"))
			s.set("permissions", new ArrayList<String>());
		this.perms = new ArrayList<String>(s.getStringList("permissions"));
	}
	
	public String getName(){
		return name;
	}
	
	public boolean isDefault(){
		return isDefault;
	}
	
	public String getPrefix(){
		return prefix;
	}
	
	public String getSuffix(){
		return suffix;
	}
	
	public String getLoginColor(){
		return loginColor;
	}
	
	public int getLevel(){
		return level;
	}
	
	public void setPrefix(String prefix){
		SettingsManager.getInstance().getGroupSection(name).set("prefix", prefix);
		SettingsManager.getInstance().savePermissions();
	}
	
	public void setSuffix(String suffix){
		SettingsManager.getInstance().getGroupSection(name).set("suffix", suffix);
		SettingsManager.getInstance().savePermissions();
	}
	
	public void setLoginColor(String color){
		SettingsManager.getInstance().getGroupSection(name).set("loginColor", color);
		SettingsManager.getInstance().savePermissions();
	}
	
	public void setLevel(int level){
		SettingsManager.getInstance().getGroupSection(name).set("level", level);
		SettingsManager.getInstance().savePermissions();
	}
	
	public boolean hasPerm(String perm){
		return perms.contains(perm);
	}
	
	@SuppressWarnings("deprecation")
	public void addInh(String inh){
		inhs.add(inh);
		SettingsManager.getInstance().getGroupSection(name).set("inheritance", inhs);
		SettingsManager.getInstance().savePermissions();
		SettingsManager.getInstance().injectPlayer(Bukkit.getServer().getOnlinePlayers());
	}
	
	@SuppressWarnings("deprecation")
	public void removeInh(String inh){
		inhs.remove(inh);
		SettingsManager.getInstance().getGroupSection(name).set("inheritance", inhs);
		SettingsManager.getInstance().savePermissions();
		SettingsManager.getInstance().injectPlayer(Bukkit.getServer().getOnlinePlayers());
	}
	
	private ArrayList<String> getGroupInhs(Group group){
		ArrayList<String> parents = new ArrayList<String>();
		for(String parent : inhs){
			Group pgroup = SettingsManager.getInstance().getGroup(parent);
			if(pgroup != null)
				parents.addAll(getGroupInhs(pgroup));
		}
		return parents;
	}
	
	public ArrayList<String> getInhs(){
		return getGroupInhs(this);
	}
	
	@SuppressWarnings("deprecation")
	public void addPerm(String perm){
		perms.add(perm);
		SettingsManager.getInstance().getGroupSection(name).set("permissions", perms);
		SettingsManager.getInstance().savePermissions();
		SettingsManager.getInstance().injectPlayer(Bukkit.getServer().getOnlinePlayers());
	}
	
	@SuppressWarnings("deprecation")
	public void removePerm(String perm){
		perms.remove(perm);
		SettingsManager.getInstance().getGroupSection(name).set("permissions", perms);
		SettingsManager.getInstance().savePermissions();
		SettingsManager.getInstance().injectPlayer(Bukkit.getServer().getOnlinePlayers());
	}
	
	public ArrayList<String> getPerms(){
		return perms;
	}

}
