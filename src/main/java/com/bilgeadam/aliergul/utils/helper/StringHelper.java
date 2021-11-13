package com.bilgeadam.aliergul.utils.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Singleton Design Pattern (1)
 * Layz
 * 
 * @author ERGUL
 *
 */
public class StringHelper {
	
	private static StringHelper instance;
	
	private StringHelper() {
		
	}
	
	// Eğer ilk defa kullanılacaksa instance edilmişmi kontrol edelim
	public static StringHelper getInstance() {
		if (instance == null) {
			instance = new StringHelper();
		}
		return instance;
	}
	
	private static final String DATE_PATTERN = "dd.MM.yyyy";
	
	/**
	 * String Format da Tarihi Date Çevirme
	 * 
	 * @param birthDay
	 * @return
	 */
	public Date stringToDate(String strDate) {
		
		SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
		Date date = new Date();
		try {
			date = format.parse(strDate.trim());
		} catch (ParseException e) {
			date = null;
			throw new IllegalArgumentException("Hatalı Tarih Formatı\ndd.MM.yyyy Şeklinde Girilmeli");
			
		}
		return date;
	}
	
	public LocalDate stringToLocalDate(String strDate) {
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_PATTERN);
		LocalDate date = LocalDate.now();
		date = LocalDate.parse(strDate.trim(), format);
		return date;
	}
	
	/**
	 * COUNT = değişilik sayısı
	 * VALUE = Yeni Metin değeri
	 * 
	 * @param msg
	 * @return
	 */
	public Map<String, String> changeTRCharecter(String msg) {
		final List<Character> trKey = Arrays.asList('ö', 'ç', 'ş', 'ı', 'ğ', 'ü', 'Ö', 'Ç', 'Ş', 'İ', 'Ğ', 'Ü');
		final List<Character> enKey = Arrays.asList('o', 'c', 's', 'i', 'g', 'u', 'O', 'C', 'S', 'I', 'G', 'U');
		StringBuilder newStr = new StringBuilder();
		int count = 0;
		char[] charArray = msg.toCharArray();
		for (int i = 0; i < msg.length(); i++) {
			if (trKey.contains(charArray[i])) {
				newStr.append(enKey.get(trKey.indexOf(charArray[i])));
				count++;
			} else {
				newStr.append(charArray[i]);
			}
		}
		Map<String, String> map = new HashMap<>();
		map.put("COUNT", String.valueOf(count));
		map.put("VALUE", newStr.toString());
		
		return map;
		
	}
	
	/**
	 * Girilen Metinin tersini döndüren metod.
	 * 
	 * @param msg JAVA
	 * @return AVAJ
	 */
	public String toReverseString(String msg) {
		StringBuilder revStr = new StringBuilder();
		
		for (int i = (msg.length() - 1); i >= 0; i--) {
			revStr.append(msg.charAt(i));
			
		}
		return revStr.toString();
		
	}
	
	/**
	 * Metin baş harfini büyük çeviren method
	 * 
	 * @param A = "adana spor"
	 * @return Adana Spor
	 */
	public String toPascalCaseString(String metin) {
		StringTokenizer st = new StringTokenizer(metin, " ");
		StringBuilder newMetin = new StringBuilder();
		while (st.hasMoreTokens()) {
			String A = st.nextToken();
			A = A.substring(0, 1).toUpperCase().concat(A.substring(1, A.length()));
			newMetin.append(A);
		}
		
		return newMetin.toString();
	}
	
	private String convertMD5(String parola) {
		try {
			MessageDigest messageDigestNesnesi = MessageDigest.getInstance("MD5");
			messageDigestNesnesi.update(parola.getBytes());
			byte messageDigestDizisi[] = messageDigestNesnesi.digest();
			StringBuffer sb16 = new StringBuffer();
			StringBuffer sb32 = new StringBuffer();
			for (int i = 0; i < messageDigestDizisi.length; i++) {
				sb16.append(Integer.toString((messageDigestDizisi[i] & 0xff) + 0x100, 16).substring(1));
				sb32.append(Integer.toString((messageDigestDizisi[i] & 0xff) + 0x100, 32));
				
			}
			return sb32.toString();
		} catch (NoSuchAlgorithmException ex) {
			System.err.println(ex);
		}
		return "-1";
	}
	
}
