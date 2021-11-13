package com.bilgeadam.aliergul.main.chatapp;

import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.main.language.MenuLanguage;
import com.bilgeadam.aliergul.utils.language.GlobalStrings;

public class MenuChatApp {
	private static MenuChatApp instance;
	private GlobalStrings language;
	private DtoUserDetails user;
	private DtoUserDetails friend;
	
	private MenuChatApp() {
		language = MenuLanguage.getInstance().getLanguage();
	}
	
	public static MenuChatApp getInstance() {
		if (instance == null)
			instance = new MenuChatApp();
		
		return instance;
	}
	
	public void viewNewMEssage(DtoUserDetails user, DtoUserDetails friend) {
		this.user = user;
		this.friend = friend;
		Thread serverThread = new Thread() {
			public void run() {
				ChatServer.getInstance.startServer(user, friend);
			}
		};
		
		serverThread.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		ChatClient.getInstance.startClient(user, friend);
	}
	
}
