package com.bilgeadam.aliergul.main;

import java.util.List;

import com.bilgeadam.aliergul.controller.UserController;
import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.utils.helper.ConsoleHelper;
import com.bilgeadam.aliergul.utils.helper.GlobalStrings;
import com.bilgeadam.aliergul.utils.helper.MenuBuilder;

public class UserPanel {
	private static GlobalStrings language;
	private static UserPanel instance;
	
	private DtoUserDetails uDetails;
	
	private UserPanel() {
		
	}
	
	public static UserPanel getInstance(GlobalStrings language) {
		if (instance == null) {
			instance = new UserPanel();
		}
		UserPanel.language = language;
		return instance;
	}
	
	public void mainUserPanel(DtoUserDetails uEmailAndPassword) {
		int choose = -1;
		
		while (choose != 4) {
			choose = new MenuBuilder.Builder().title(language.getString("Globalization.USER_PANEL"))
					.addMenu(1, language.getString("Globalization.SHOW_PROFILE"))
					.addMenu(2, language.getString("Globalization.INBOX"))
					.addMenu(3, language.getString("Globalization.SEARCH"))
					.addMenu(4, language.getString("Globalization.SAFE_EXIT"))
					.selectMessage(language.getString("Globalization.ENTER_NUMBER_ONLY")).build().show().readInteger();
			switch (choose) {
				case 1:
					viewProfileInfo(uEmailAndPassword);
					break;
				case 2:
					
					break;
				case 3:
					viewProfileSearch();
					break;
				
				default:
					break;
			}
		}
		
	}
	
	private void viewProfileSearch() {
		String searchWord = language.getString("Globalization.TO_SEARCH_WORD");
		
		new MenuBuilder.Builder().title(language.getString("Globalization.SEARCH")).body(searchWord).lineCount(30)
				.build().show();
		
		String findQuery = ConsoleHelper.getInstance(language).readString(searchWord + " : ");
		DtoUserDetails dto = new DtoUserDetails();
		dto.setMetaData("%" + findQuery + "%");
		List<DtoUserDetails> tempList = UserController.getInstance.getFindUser(dto);
		if (tempList.size() > 0) {
			MenuBuilder filterMenu = new MenuBuilder.Builder().title(searchWord + ": " + findQuery)
					.selectMessage(language.getString("Globalization.SELECT_A_USER_TO_SEND_MESSAGE")).build();
			int i = 0;
			for (DtoUserDetails uDetails : tempList) {
				
				filterMenu.addMenu(i++, uDetails.getName().toUpperCase() + " " + uDetails.getSurName().toUpperCase()
						+ " " + uDetails.getEmail());
				
			}
			filterMenu.show().readInteger();
		} else {
			System.err.println(
					language.getString("Globalization.SEARCH_NOT_FOUND") + "\n" + searchWord + ":" + findQuery);
		}
		
	}
	
	public void viewProfileInfo(DtoUserDetails uEmailAndPassword) {
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
				default:
					System.err.println(language.getString("Globalization.ERROR_NOT_CHANGED"));
					break;
			}
			
		}
		
	}
	
}
