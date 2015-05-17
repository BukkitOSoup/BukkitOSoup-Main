package com.ogxclaw.main.bukkitosoup.utils;

public class PermissionDeniedException extends BukkitOSoupCommandException {

	private static final long serialVersionUID = 1L;

	public PermissionDeniedException() {
		this("Permission denied!");
	}

	public PermissionDeniedException(String s) {
		super(s);
		setColor('4');
	}

	public PermissionDeniedException(Throwable cause) {
		super("Permission denied!", cause);
	}

	public PermissionDeniedException(String s, Throwable cause) {
		super(s, cause);
		setColor('4');
	}

}
