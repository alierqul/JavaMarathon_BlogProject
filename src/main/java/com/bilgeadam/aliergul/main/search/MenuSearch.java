package com.bilgeadam.aliergul.main.search;

import java.util.List;

import com.bilgeadam.aliergul.controller.UserController;
import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.main.language.MenuLanguage;
import com.bilgeadam.aliergul.utils.helper.ConsoleHelper;
import com.bilgeadam.aliergul.utils.helper.MenuBuilder;
import com.bilgeadam.aliergul.utils.language.GlobalStrings;

public class MenuSearch {
	private static MenuSearch instance;
	private GlobalStrings language;
	private DtoUserDetails uDetails;
	
	private MenuSearch() {
		
	}
	
	public static MenuSearch getInstance() {
		if (instance == null)
			instance = new MenuSearch();
		
		return instance;
	}
	
	public DtoUserDetails viewProfileSearch() {
		this.language = MenuLanguage.getInstance().getLanguage();
		MenuBuilder filterMenu = null;
		String searchWord = language.getString("Globalization.TO_SEARCH_WORD");
		new MenuBuilder.Builder().title(language.getString("Globalization.SEARCH")).body(searchWord).lineCount(30)
				.build().show();
		
		String findQuery = ConsoleHelper.getInstance(language).readString(searchWord + " : ");
		DtoUserDetails dto = new DtoUserDetails();
		dto.setMetaData("%" + findQuery.toLowerCase() + "%");
		List<DtoUserDetails> tempList = UserController.getInstance.getFindUser(dto);
		if (tempList.size() > 0) {
			filterMenu = new MenuBuilder.Builder().title(searchWord + ": " + findQuery)
					.selectMessage(language.getString("Globalization.SELECT_A_USER_TO_SEND_MESSAGE")).build();
			int i = 0;
			for (DtoUserDetails uDetails : tempList) {
				
				filterMenu.addMenu(i++, uDetails.getName().toUpperCase() + " " + uDetails.getSurName().toUpperCase()
						+ " " + uDetails.getEmail());
				
			}
			int choose = filterMenu.show().readInteger();
			return tempList.get(choose);
		} else {
			filterMenu = new MenuBuilder.Builder()
					.title(language.getString("Globalization.SEARCH_NOT_FOUND") + "\n" + searchWord + ":" + findQuery)
					.build().show();
			
		}
		
		return null;
	}
	
}
