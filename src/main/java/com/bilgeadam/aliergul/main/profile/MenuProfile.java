package com.bilgeadam.aliergul.main.profile;

import com.bilgeadam.aliergul.controller.UserController;
import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.main.language.MenuLanguage;
import com.bilgeadam.aliergul.utils.helper.ConsoleHelper;
import com.bilgeadam.aliergul.utils.helper.MenuBuilder;
import com.bilgeadam.aliergul.utils.language.GlobalStrings;

public class MenuProfile {
	private static MenuProfile instance;
	private GlobalStrings language;
	private DtoUserDetails uDetails;
	
	private MenuProfile() {
		
	}
	
	public static MenuProfile getInstance() {
		if (instance == null)
			instance = new MenuProfile();
		
		return instance;
	}
	
	public void viewProfileInfo(DtoUserDetails uEmailAndPassword) {
		this.language = MenuLanguage.getInstance().getLanguage();
		this.uDetails = UserController.getInstance.getUserDetails(uEmailAndPassword);
		ConsoleHelper cHelper = ConsoleHelper.getInstance(language);
		int choose = -1;
		boolean isChanged = false;
		
		while (choose != 0) {
			MenuBuilder menuProfile = new MenuBuilder.Builder().title(language.getString("Globalization.USER_PANEL"))
					.body(uDetails.getName() + " " + uDetails.getSurName())
					.addMenu(1,
							String.format("%-18s :%s", language.getString("Globalization.SCAN_NAME"),
									uDetails.getName()))
					.addMenu(2,
							String.format("%-18s :%s", language.getString("Globalization.SCAN_SURNAME"),
									uDetails.getSurName()))
					.addMenu(3,
							String.format("%-18s :%s", language.getString("Globalization.SCAN_PHONE"),
									uDetails.getPhone()))
					.addMenu(4,
							String.format("%-18s :%s", language.getString("Globalization.SCAN_EMAIL"),
									uDetails.getEmail()))
					.addMenu(5,
							String.format("%-18s :%s", language.getString("Globalization.SCAN_HESCODE"),
									uDetails.getHescode()))
					.addMenu(6,
							String.format("%-18s :%s", language.getString("Globalization.SCAN_CREATED_DATE"),
									uDetails.getCreateDate().toString()))
					.selectMessage(language.getString("Globalization.EXIT"))
					.body(language.getString("Globalization.SELECT_NUMBER_TO_UPDATE")).build();
			if (isChanged) {
				menuProfile.addMenu(7, language.getString("Globalization.TO_SAVE_CHANGES") + " [7]");
			}
			choose = menuProfile.show().readInteger();
			
			switch (choose) {
				case 1:
					isChanged = true;
					uDetails.setName(cHelper.readString(language.getString("Globalization.SCAN_NAME") + " : "));
					break;
				case 2:
					isChanged = true;
					uDetails.setSurName(cHelper.readString(language.getString("Globalization.SCAN_SURNAME") + " : "));
					break;
				case 3:
					isChanged = true;
					uDetails.setPhone(cHelper.readString(language.getString("Globalization.SCAN_PHONE") + " : "));
					break;
				case 4:
					isChanged = true;
					uDetails.setEmail(cHelper.readString(language.getString("Globalization.SCAN_EMAIL") + " : "));
					break;
				case 5:
					isChanged = true;
					uDetails.setHescode(cHelper.readString(language.getString("Globalization.SCAN_HESCODE") + " : "));
					break;
				case 6:
					System.err.println(language.getString("Globalization.ERROR_NOT_CHANGED"));
					break;
				case 7:
					if (isChanged) {
						UserController.getInstance.updateUser(uDetails);
						isChanged = false;
						menuProfile.removeMenu(7);
					}
					break;
				case 0:
					break;
				default:
					System.err.println(language.getString("Globalization.ERROR_NOT_CHANGED"));
					break;
			}
			
		}
		
	}
}
