package com.bilgeadam.aliergul.main;

import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.utils.helper.ConsoleHelper;
import com.bilgeadam.aliergul.utils.helper.GlobalStrings;
import com.bilgeadam.aliergul.utils.helper.MenuBuilder;

public class MenuHelper {
	
	private GlobalStrings language;
	private ConsoleHelper cHelper;
	
	public MenuHelper(GlobalStrings language) {
		this.language = language;
		cHelper = ConsoleHelper.getInstance(language);
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
	
	public DtoUserDetails scanLoginInformation(GlobalStrings language) {
		this.language = language;
		this.cHelper = ConsoleHelper.getInstance(language);
		cHelper.printTitle(language.getString("Globalization.LOGIN"));
		String email = cHelper.readString(language.getString("Globalization.SCAN_EMAIL") + " : ").trim().toLowerCase();
		String password = cHelper.readString(language.getString("Globalization.SCAN_PASSWORD") + " : ");
		return new DtoUserDetails(email, password);
	}
	
}
