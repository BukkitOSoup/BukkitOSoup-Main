package com.ogxclaw.main.bukkitosoup.utils;

public class BukkitOSoupCommandException extends Exception {

	private static final long serialVersionUID = 1L;

	private char color = '5';

	public BukkitOSoupCommandException(String s) {
		super(s);
	}

	public BukkitOSoupCommandException(Throwable cause) {
		super(cause);
	}

	public BukkitOSoupCommandException(String s, Throwable cause) {
		super(s, cause);
	}

	public BukkitOSoupCommandException setColor(char color) {
		this.color = color;
		return this;
	}

	public char getColor() {
		return color;
	}

}
