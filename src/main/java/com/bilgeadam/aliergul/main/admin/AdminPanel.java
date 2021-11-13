package com.bilgeadam.aliergul.main.admin;

import java.util.Map;

import com.bilgeadam.aliergul.controller.AdminController;
import com.bilgeadam.aliergul.controller.UserController;
import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.main.language.MenuLanguage;
import com.bilgeadam.aliergul.main.search.MenuSearch;
import com.bilgeadam.aliergul.utils.exceptions.ExceptionNotAuthorizedError;
import com.bilgeadam.aliergul.utils.helper.MenuBuilder;
import com.bilgeadam.aliergul.utils.language.GlobalStrings;

public class AdminPanel {
	private static AdminPanel instance;
	private GlobalStrings language;
	private DtoUserDetails uDetials;
	private AdminController adminController;
	private UserController userController = UserController.getInstance;
	private static final int ADMIN_ROLE = 1;
	private static final int USER_ROLE = 3;
	
	private AdminPanel() {
		
	}
	
	public static AdminPanel getInstance() {
		if (instance == null)
			instance = new AdminPanel();
		
		return instance;
	}
	
	public void mainAdminPanel(DtoUserDetails uDetails) {
		int choose = -1;
		this.uDetials = uDetails;
		
		this.adminController = AdminController.getInstance(uDetails);
		this.language = MenuLanguage.getInstance().getLanguage();
		MenuBuilder menu = adminMenuBuilder();
		
		while (choose != 0) {
			choose = menu.show().readInteger();
			switch (choose) {
				case 1:
					menuDeletedUser();
					break;
				case 2:
					menuChangedBlockedPassword();
					break;
				
				case 3:
					menuChangedRoleUser();
					break;
				
				case 4:
					if (adminController.getUserRole(uDetials).isViewNumberOfRecord()) {
						try {
							MenuBuilder menuCounts = new MenuBuilder.Builder()
									.title(language.getString("Globalization.ADMIN_COUNT")).build();
							for (Map.Entry<String, Integer> entry : adminController.getInstance(uDetails)
									.countOfRecordByRoles().entrySet()) {
								String key = entry.getKey();
								Integer val = entry.getValue();
								menuCounts.addMenu("[ " + key + " ] =", "[ " + val + " ]");
							}
							menuCounts.show().readInteger();
						} catch (ExceptionNotAuthorizedError e) {
							System.err.println(language.getString(e.getMessage()));
						}
					} else {
						System.err.println(language.getString("Globalization.ERROR_AUTHORIZED"));
					}
					break;
				case 5:
					language = MenuLanguage.getInstance().changedLanguage();
					break;
				case 0:
					
					break;
				
				default:
					break;
			}
		}
		
	}
	
	private void menuDeletedUser() {
		DtoUserDetails findUser;
		if (adminController.getUserRole(uDetials).isUserDeleteAccount()) {
			findUser = MenuSearch.getInstance().viewProfileSearch();
			
			if (findUser != null && chooseBooleanYesNo(language.getString("Globalization.ADMIN_DELETED_ACCOUNT"),
					language.getString("Globalization.ADMIN_DELETED_CONFIRM"), findUser)) {
				try {
					adminController.deleteAccount(findUser);
				} catch (ExceptionNotAuthorizedError e) {
					System.err.println(language.getString(e.getMessage()));
				}
			}
		} else {
			System.err.println(language.getString("Globalization.ERROR_AUTHORIZED"));
		}
	}
	
	private void menuChangedRoleUser() {
		DtoUserDetails findUser;
		if (adminController.getUserRole(uDetials).isUserChangeRole()) {
			findUser = MenuSearch.getInstance().viewProfileSearch();
			
			if (findUser != null) {
				try {
					int role = chooseRoleAdminUser(language.getString("Globalization.ADMIN_ROLE_CHANGE"),
							language.getString("Globalization.ADMIN_DELETED_CONFIRM"), findUser);
					findUser.setRoleId(role);
					adminController.changedUserRoleStatus(findUser);
				} catch (ExceptionNotAuthorizedError e) {
					System.err.println(language.getString(e.getMessage()));
				}
			}
		} else {
			System.err.println(language.getString("Globalization.ERROR_AUTHORIZED"));
		}
	}
	
	private void menuChangedBlockedPassword() {
		DtoUserDetails findUser;
		if (adminController.getUserRole(uDetials).isUserChangeActive()) {
			findUser = MenuSearch.getInstance().viewProfileSearch();
			
			if (findUser != null && chooseBooleanYesNo(language.getString("Globalization.ADMIN_PASSWORD_UNBLOKING"),
					language.getString("Globalization.ADMIN_UNBLOKING_PASS"), findUser)) {
				try {
					adminController.changedUserActiveStatus(findUser);
				} catch (ExceptionNotAuthorizedError e) {
					System.err.println(language.getString(e.getMessage()));
				}
			}
		} else {
			System.err.println(language.getString("Globalization.ERROR_AUTHORIZED"));
		}
	}
	
	private MenuBuilder adminMenuBuilder() {
		
		MenuBuilder menu = new MenuBuilder.Builder().title(language.getString("Globalization.ADMIN_PANEL"))
				.selectMessage(language.getString("Globalization.ENTER_NUMBER_ONLY")).build();
		
		if (adminController.getUserRole(uDetials).isUserDeleteAccount()) {
			menu.addMenu(1, language.getString("Globalization.ADMIN_DELETED_ACCOUNT"));
		}
		if (adminController.getUserRole(uDetials).isUserChangeActive()) {
			menu.addMenu(2, language.getString("Globalization.ADMIN_PASSWORD_UNBLOKING"));
		}
		if (adminController.getUserRole(uDetials).isUserChangeRole()) {
			menu.addMenu(3, language.getString("Globalization.ADMIN_ROLE_CHANGE"));
		}
		
		if (adminController.getUserRole(uDetials).isViewNumberOfRecord()) {
			menu.addMenu(4, language.getString("Globalization.ADMIN_COUNT"));
		}
		if (adminController.getUserRole(uDetials).isViewNumberOfRecord()) {
			menu.addMenu(5, language.getString("Globalization.LANGUAGE"));
		}
		menu.addMenu(0, language.getString("Globalization.EXIT"));
		return menu;
		
	}
	
	private boolean chooseBooleanYesNo(String title, String body, DtoUserDetails dto) {
		int choose = new MenuBuilder.Builder().title(title).body(body)
				.addMenu("", "[ " + dto.getName() + " " + dto.getSurName() + " " + dto.getEmail() + " ]")
				.selectMessage(" 00 [ x ]    -   01 [ O ] ").build().show().readInteger();
		
		return choose == 0 ? false : true;
	}
	
	private int chooseRoleAdminUser(String title, String body, DtoUserDetails dto) {
		int choose;
		do {
			choose = new MenuBuilder.Builder().title(title).body(body)
					.addMenu("", "[ " + dto.getName() + " " + dto.getSurName() + " " + dto.getEmail() + " ]")
					.selectMessage(" [" + ADMIN_ROLE + "  User ]    -   [" + USER_ROLE + "  Admin ] ").build().show()
					.readInteger();
		} while (choose == ADMIN_ROLE || choose == USER_ROLE);
		
		return choose;
	}
	
}
