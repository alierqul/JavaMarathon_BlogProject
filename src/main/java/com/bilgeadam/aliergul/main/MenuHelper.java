package com.bilgeadam.aliergul.main;

import java.util.LinkedHashMap;
import java.util.Map;

import com.bilgeadam.aliergul.dto.DtoUser;
import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.utils.helper.ConsoleHelper;
import com.bilgeadam.aliergul.utils.helper.GlobalStrings;

public class MenuHelper {
	
	private GlobalStrings language;
	private ConsoleHelper cHelper;
	
	public MenuHelper(GlobalStrings language) {
		this.language = language;
		cHelper = ConsoleHelper.getInstance(language);
	}
	
	public Map<Integer, String> getLoginMenu() {
		Map<Integer, String> temp = new LinkedHashMap();
		temp.put(1, language.getString("Globalization.LOGIN"));
		temp.put(2, language.getString("Globalization.REGISTER"));
		temp.put(3, language.getString("Globalization.LANGUAGE"));
		temp.put(4, language.getString("Globalization.EXIT"));
		return temp;
	}
	
	public DtoUserDetails scanNewAccountInformation() {
		DtoUser user = new DtoUser();
		String name = ConsoleHelper.getInstance(language).readString(language.getString("Globalization.SCAN_NAME"));
		String surname = ConsoleHelper.getInstance(language)
				.readString(language.getString("Globalization.SCAN_SURNAME"));
		String phone = ConsoleHelper.getInstance(language).readString(language.getString("Globalization.SCAN_PHONE"));
		String email = ConsoleHelper.getInstance(language).readString(language.getString("Globalization.SCAN_EMAIL"))
				.trim().toLowerCase();
		String password = ConsoleHelper.getInstance(language).readString(language.getString("Globalization.PASSWORD"));
		String hescode = ConsoleHelper.getInstance(language).readString(language.getString("Globalization.HESCODE"));
		return new DtoUserDetails(email, password, name, surname, password, hescode);
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
