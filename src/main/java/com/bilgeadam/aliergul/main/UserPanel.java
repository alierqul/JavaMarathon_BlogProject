package com.bilgeadam.aliergul.main;

import java.util.LinkedHashMap;
import java.util.Map;

import com.bilgeadam.aliergul.controller.UserController;
import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.utils.helper.ConsoleHelper;
import com.bilgeadam.aliergul.utils.helper.GlobalStrings;
import com.bilgeadam.aliergul.utils.helper.MenuBuilder;

public class UserPanel {
	private static GlobalStrings language;
	private static UserPanel instance;
	private ConsoleHelper cHelper;
	private DtoUserDetails uDetails;
	
	private UserPanel() {
		
		cHelper = ConsoleHelper.getInstance(language);
	}
	
	public static UserPanel getInstance(GlobalStrings language) {
		if (instance == null) {
			instance = new UserPanel();
		}
		UserPanel.language = language;
		return instance;
	}
	
	public void mainUserPanel2(DtoUserDetails uDetails) {
		
		uDetails = UserController.getInstance.getUserDetails(uDetails);
		Map<Integer, String> map = new LinkedHashMap<>();
		map.put(1, String.format("%-18s :%s", language.getString("Globalization.SCAN_NAME"), uDetails.getName()));
		map.put(2, String.format("%-18s :%s", language.getString("Globalization.SCAN_SURNAME"), uDetails.getSurName()));
		map.put(3, String.format("%-18s :%s", language.getString("Globalization.SCAN_PHONE"), uDetails.getPhone()));
		map.put(4, String.format("%-18s :%s", language.getString("Globalization.SCAN_EMAIL"), uDetails.getEmail()));
		map.put(5, String.format("%-18s :%s", language.getString("Globalization.SCAN_HESCODE"), uDetails.getHescode()));
		map.put(6, String.format("%-18s :%s", language.getString("Globalization.SCAN_CREATED_DATE"),
				uDetails.getCreateDate().toString()));
		cHelper.showMenu(language.getString("Globalization.USER_PANEL"), map);
	}
	
	public void mainUserPanel(DtoUserDetails uDetails) {
		this.uDetails = UserController.getInstance.getUserDetails(uDetails);
		new MenuBuilder.Builder().title(language.getString("Globalization.USER_PANEL"))
				.body(uDetails.getName() + " " + uDetails.getSurName())
				.addMenu(1,
						String.format("%-18s :%s", language.getString("Globalization.SCAN_NAME"), uDetails.getName()))
				.addMenu(2,
						String.format("%-18s :%s", language.getString("Globalization.SCAN_SURNAME"),
								uDetails.getSurName()))
				.addMenu(3,
						String.format("%-18s :%s", language.getString("Globalization.SCAN_PHONE"), uDetails.getPhone()))
				.addMenu(4,
						String.format("%-18s :%s", language.getString("Globalization.SCAN_EMAIL"), uDetails.getEmail()))
				.addMenu(5,
						String.format("%-18s :%s", language.getString("Globalization.SCAN_HESCODE"),
								uDetails.getHescode()))
				.addMenu(6,
						String.format("%-18s :%s", language.getString("Globalization.SCAN_CREATED_DATE"),
								uDetails.getCreateDate().toString()))
				.selectMessage(language.getString("Globalization.ENTER_NUMBER_ONLY")).build().show().readInteger();
		
	}
	
}
