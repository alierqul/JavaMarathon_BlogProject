package com.bilgeadam.aliergul.main.user;

import com.bilgeadam.aliergul.controller.UserController;
import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.main.admin.AdminPanel;
import com.bilgeadam.aliergul.main.chatapp.MenuChatApp;
import com.bilgeadam.aliergul.main.inbox.MenuInbox;
import com.bilgeadam.aliergul.main.language.MenuLanguage;
import com.bilgeadam.aliergul.main.profile.MenuProfile;
import com.bilgeadam.aliergul.main.search.MenuSearch;
import com.bilgeadam.aliergul.utils.helper.MenuBuilder;
import com.bilgeadam.aliergul.utils.helper.SharedPreferencesHelper;
import com.bilgeadam.aliergul.utils.language.GlobalStrings;

public class UserPanel {
	private GlobalStrings language;
	private static UserPanel instance;
	private static final int USER_ROLE_ID = 3;
	private DtoUserDetails uDetails;
	
	private UserPanel() {
		this.language = MenuLanguage.getInstance().getLanguage();
	}
	
	public static UserPanel getInstance() {
		if (instance == null) {
			instance = new UserPanel();
		}
		
		return instance;
	}
	
	public void mainUserPanel(DtoUserDetails uEmailAndPassword) {
		int choose = -1;
		uDetails = UserController.getInstance.getUserDetails(uEmailAndPassword);
		MenuBuilder menu = userMenuBuild();
		while (choose != 0) {
			
			this.language = MenuLanguage.getInstance().getLanguage();
			
			choose = menu.show().readInteger();
			switch (choose) {
				case 1:
					MenuProfile.getInstance().viewProfileInfo(uDetails);
					
					break;
				case 2:
					MenuInbox.getInstance().viewInbox(uDetails);
					break;
				case 3:
					DtoUserDetails friend = MenuSearch.getInstance().viewProfileSearch();
					if (friend != null)
						MenuChatApp.getInstance().viewNewMEssage(uDetails, friend);
					
					break;
				case 4:
					language = MenuLanguage.getInstance().changedLanguage();
					menu = userMenuBuild();
					break;
				case 5:
					if (uDetails.getRoleId() != USER_ROLE_ID) {
						
						AdminPanel.getInstance().mainAdminPanel(uDetails);
					}
					break;
				default:
					break;
			}
		}
		SharedPreferencesHelper.getInstance.writeToFile(new DtoUserDetails());
		
	}
	
	private MenuBuilder userMenuBuild() {
		MenuBuilder menu = new MenuBuilder.Builder().title(language.getString("Globalization.USER_PANEL"))
				.body(uDetails.getName() + " " + uDetails.getSurName())
				.addMenu(1, language.getString("Globalization.SHOW_PROFILE"))
				.addMenu(2, language.getString("Globalization.INBOX"))
				.addMenu(3, language.getString("Globalization.SEARCH"))
				.addMenu(4, language.getString("Globalization.LANGUAGE"))
				
				.selectMessage(language.getString("Globalization.ENTER_NUMBER_ONLY")).build();
		if (uDetails.getRoleId() != USER_ROLE_ID) {
			menu.addMenu(5, language.getString("Globalization.ADMIN_PANEL"));
			
		}
		menu.addMenu(0, language.getString("Globalization.SAFE_EXIT"));
		return menu;
	}
	
}
