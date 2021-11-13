package com.bilgeadam.aliergul.main.language;

import com.bilgeadam.aliergul.utils.language.GlobalStrings;

public class MenuLanguage {
	private static MenuLanguage instance;
	private GlobalStrings language;
	
	private MenuLanguage() {
		this.language = new GlobalStrings("tr");
	}
	
	public static MenuLanguage getInstance() {
		if (instance == null)
			instance = new MenuLanguage();
		return instance;
	}
	
	public GlobalStrings getLanguage() {
		return language;
	}
	
	public GlobalStrings changedLanguage() {
		if (language.getLeangue().equals("tr")) {
			language = new GlobalStrings("en");
			
		} else {
			language = new GlobalStrings("tr");
			
		}
		
		return language;
	}
	
}
