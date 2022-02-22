package com.catand.catandBOT.plugin.SPDNet;

import lombok.Data;

@Data
public class Account {
	public Account() {
	}

	public Account(boolean bot, boolean admin, String key, String nick) {
		this.bot = bot;
		this.admin = admin;
		this.key = key;
		this.nick = nick;
	}

	private boolean bot;
	private boolean admin;
	private String key;
	private String nick;
}
