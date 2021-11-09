package com.bilgeadam.aliergul.utils.helper;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class GlobalStrings {
	private static final String BUNDLE_NAME = "com.bilgeadam.aliergul.utils.language.String";
	private String leangue;
	private ResourceBundle resourceBundle;
	
	public GlobalStrings(String language, String country) {
		Locale locale = new Locale(language.toLowerCase(), country.toUpperCase());
		this.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
		this.leangue = language;
	}
	
	public GlobalStrings(String language) {
		Locale locale = new Locale(language.toLowerCase());
		this.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
		this.leangue = language;
	}
	
	public GlobalStrings() {
		this.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
		this.leangue = "tr";
	}
	
	public String getString(String key) {
		try {
			return this.resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public String getLeangue() {
		return leangue;
	}
	
}