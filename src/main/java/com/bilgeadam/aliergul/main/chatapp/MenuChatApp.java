package com.bilgeadam.aliergul.main.chatapp;

import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.main.language.MenuLanguage;
import com.bilgeadam.aliergul.utils.language.GlobalStrings;

public class MenuChatApp {
	private static MenuChatApp instance;
	private GlobalStrings language;
	private DtoUserDetails uDetails;
	
	private MenuChatApp() {
		language = MenuLanguage.getInstance().getLanguage();
	}
	
	public static MenuChatApp getInstance() {
		if (instance == null)
			instance = new MenuChatApp();
		
		return instance;
	}
	
}
