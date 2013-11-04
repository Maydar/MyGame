package ru.mail.projects.resources;

import ru.mail.projects.base.Resource;

public class UserSessResource implements Resource {
	private String playTime;

	public int getPlayTime() {
		return Integer.decode(playTime);
	}

	public void setHealth(int PlayTime) {
		this.playTime = String.valueOf(PlayTime);
	}
}
