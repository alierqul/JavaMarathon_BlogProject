package com.bilgeadam.aliergul.main.inbox;

import com.bilgeadam.aliergul.controller.InboxController;
import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.main.language.MenuLanguage;
import com.bilgeadam.aliergul.utils.helper.MenuBuilder;
import com.bilgeadam.aliergul.utils.language.GlobalStrings;

public class MenuInbox {
	private static MenuInbox instance;
	private GlobalStrings language;
	private DtoUserDetails uDetails;
	
	private MenuInbox() {
		
	}
	
	public static MenuInbox getInstance() {
		if (instance == null)
			instance = new MenuInbox();
		
		return instance;
	}
	
	public void viewInbox(DtoUserDetails uDetails) {
		language = MenuLanguage.getInstance().getLanguage();
		MenuBuilder menu = new MenuBuilder.Builder().title(language.getString("Globalization.INBOX"))
				.selectMessage(language.getString("Globalization.EXIT")).build();
		for (DtoUserDetails u : InboxController.getInstance.getListUserMessage(uDetails)) {
			menu.addMenu(u.getId(), u.getName() + " " + u.getSurName() + " [" + u.getEmail() + "]");
			System.out.println(u.toString());
		}
		int choose = -1;
		while (choose != 0) {
			choose = menu.show().readInteger();
			
		}
		
	}
}
