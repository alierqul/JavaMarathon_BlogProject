package com.bilgeadam.aliergul.main;

import com.bilgeadam.aliergul.controller.UserController;
import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.utils.exceptions.ExceptionIncorrectPasswordBlockedStatus;
import com.bilgeadam.aliergul.utils.helper.GlobalStrings;

public class MainActivity {
	private GlobalStrings language;
	private MenuHelper menuHelper;
	
	public static void main(String[] args) {
		MainActivity main = new MainActivity();
		main.activity();
		
	}
	
	private void activity() {
		this.language = new GlobalStrings("tr");
		this.menuHelper = new MenuHelper(language);
		
		int choose = -1;
		while (choose != 0) {
			choose = menuHelper.getLoginMenu().show().readInteger();
			switch (choose) {
				case 1:
					loginView();
					
					break;
				case 2:
					newAccountView();
					break;
				case 3:
					changeLanguage();
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
		DtoUserDetails uDetails = menuHelper.scanNewAccountInformation();
		UserController controller = UserController.getInstance;
		if (controller.createAccount(uDetails)) {
			System.out.println(language.getString("Globalization.SUCCESSFUL_NEW_ACCOUNT"));
		} else {
			System.out.println(language.getString("Globalization.ERROR_NEW_ACCOUNT"));
		}
		
	}
	
	private void loginView() {
		DtoUserDetails user = menuHelper.scanLoginInformation(language);
		UserController controller = UserController.getInstance;
		try {
			if (controller.logIn(user)) {
				System.out.println(language.getString("Globalization.SUCCESSFUL_LOGIN"));
				UserPanel.getInstance(language).mainUserPanel(user);
			} else {
				System.out.println(language.getString("Globalization.ERROR_NEW_ACCOUNT"));
			}
		} catch (ExceptionIncorrectPasswordBlockedStatus e) {
			System.out.println(language.getString(e.getMessage()));
		}
	}
	
	private void changeLanguage() {
		if (language.getLeangue().equals("tr")) {
			language = new GlobalStrings("en");
			
		} else {
			language = new GlobalStrings("tr");
			
		}
		this.menuHelper = new MenuHelper(language);
		
	}
}
