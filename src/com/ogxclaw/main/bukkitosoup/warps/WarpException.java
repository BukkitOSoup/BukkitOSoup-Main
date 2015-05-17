package com.ogxclaw.main.bukkitosoup.warps;

import com.ogxclaw.main.bukkitosoup.utils.BukkitOSoupCommandException;

public class WarpException extends BukkitOSoupCommandException {
	
	private static final long serialVersionUID = 1L;
	
	public WarpException(String message){
		super(message);
	}
	
	public WarpException(Throwable cause){
		super(cause);
	}
	
	public WarpException(String s, Throwable c){
		super(s, c);
	}
	
	@Override
	public WarpException setColor(char color){
		super.setColor(color);
		return this;
	}

}
