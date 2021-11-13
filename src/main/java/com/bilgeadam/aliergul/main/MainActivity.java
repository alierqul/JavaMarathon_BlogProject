package com.bilgeadam.aliergul.main;

import com.bilgeadam.aliergul.controller.UserController;
import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.main.language.MenuLanguage;
import com.bilgeadam.aliergul.main.user.UserPanel;
import com.bilgeadam.aliergul.utils.exceptions.ExceptionIncorrectPasswordBlockedStatus;
import com.bilgeadam.aliergul.utils.helper.ConsoleHelper;
import com.bilgeadam.aliergul.utils.helper.MenuBuilder;
import com.bilgeadam.aliergul.utils.helper.SharedPreferencesHelper;
import com.bilgeadam.aliergul.utils.language.GlobalStrings;

public class MainActivity {
	private GlobalStrings language;
	
	public static void main(String[] args) {
		MainActivity main = new MainActivity();
		main.activity();
		
	}
	
	private void activity() {
		this.language = MenuLanguage.getInstance().getLanguage();
		
		int choose = -1;
		while (choose != 0) {
			choose = getLoginMenu().show().readInteger();
			switch (choose) {
				case 1:
					loginView();
					
					break;
				case 2:
					newAccountView();
					break;
				case 3:
					this.language = MenuLanguage.getInstance().changedLanguage();
					break;
				
				case 4:
					choose = 0;
					break;
				case 0:
					
					break;
				default:
					break;
			}
			if (choose == 0) {
				break;
			}
		}
	}
	
	private void newAccountView() {
		DtoUserDetails uDetails = scanNewAccountInformation();
		UserController controller = UserController.getInstance;
		if (controller.createAccount(uDetails)) {
			System.out.println(language.getString("Globalization.SUCCESSFUL_NEW_ACCOUNT"));
		} else {
			System.out.println(language.getString("Globalization.ERROR_NEW_ACCOUNT"));
		}
		
	}
	
	private void loginView() {
		DtoUserDetails user = SharedPreferencesHelper.getInstance.readToFile();
		if (user == null || user.getEmail().isEmpty()) {
			user = scanLoginInformation();
		}
		UserController controller = UserController.getInstance;
		try {
			if (controller.logIn(user)) {
				SharedPreferencesHelper.getInstance.writeToFile(user);
				System.out.println(language.getString("Globalization.SUCCESSFUL_LOGIN"));
				UserPanel.getInstance().mainUserPanel(user);
			} else {
				System.out.println(language.getString("Globalization.ERROR_NEW_ACCOUNT"));
			}
		} catch (ExceptionIncorrectPasswordBlockedStatus e) {
			System.out.println(language.getString(e.getMessage()));
		}
	}
	
	public MenuBuilder getLoginMenu() {
		return new MenuBuilder.Builder().title(language.getString("Globalization.APP_NAME"))
				.addMenu(1, language.getString("Globalization.LOGIN"))
				.addMenu(2, language.getString("Globalization.REGISTER"))
				.addMenu(3, language.getString("Globalization.LANGUAGE"))
				.addMenu(4, language.getString("Globalization.EXIT"))
				.selectMessage(language.getString("Globalization.PLEASE_ENTER_YOUR_MENU_NUMBER")).build();
	}
	
	public DtoUserDetails scanNewAccountInformation() {
		
		String name = ConsoleHelper.getInstance(language).readString(language.getString("Globalization.SCAN_NAME"));
		String surname = ConsoleHelper.getInstance(language)
				.readString(language.getString("Globalization.SCAN_SURNAME"));
		String phone = ConsoleHelper.getInstance(language).readString(language.getString("Globalization.SCAN_PHONE"));
		String email = ConsoleHelper.getInstance(language).readString(language.getString("Globalization.SCAN_EMAIL"))
				.trim().toLowerCase();
		String password = ConsoleHelper.getInstance(language).readString(language.getString("Globalization.PASSWORD"));
		String hescode = ConsoleHelper.getInstance(language).readString(language.getString("Globalization.HESCODE"));
		return new DtoUserDetails(email, password, name, surname, phone, hescode);
	}
	
	public DtoUserDetails scanLoginInformation() {
		
		ConsoleHelper cHelper = ConsoleHelper.getInstance(language);
		new MenuBuilder.Builder().title(language.getString("Globalization.LOGIN")).build().show();
		String email = cHelper.readString(language.getString("Globalization.SCAN_EMAIL") + " : ").trim().toLowerCase();
		String password = cHelper.readString(language.getString("Globalization.SCAN_PASSWORD") + " : ");
		return new DtoUserDetails(email, password);
	}
	
}
