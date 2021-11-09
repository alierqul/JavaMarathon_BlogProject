/**
 * 
 */
package com.bilgeadam.aliergul.utils.helper;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

/**
 * Singleton Design Pattern (2)
 * EagerCache
 * 
 * @author ERGUL
 */
public class ConsoleHelper {
	private GlobalStrings language;
	private Scanner in;
	private static ConsoleHelper helper;
	
	// Birden fazla inhisilaz edilmemesi için private yapıyoruz.
	private ConsoleHelper(GlobalStrings language) {
		this.language = language;
		this.in = new Scanner(System.in);
	}
	
	public static ConsoleHelper getInstance(GlobalStrings language) {
		if (helper == null)
			helper = new ConsoleHelper(language);
		return helper;
	}
	
	public void close() {
		if (in != null)
			in.close();
	}
	
	/**
	 * Verilen Mesaja uygun bir string girilmesini bekler.
	 * 
	 * @param msg
	 * @return
	 */
	public String readString(String msg) {
		
		String strLine = "";
		do {
			try {
				System.out.print(msg);
				strLine = in.next();
				return strLine;
			} catch (IllegalArgumentException e) {
				System.out.println(strLine + " = " + e.getMessage());
			}
		} while (true);
		
	}
	
	/**
	 * Ekrandan Girilen değerin Double olup olmadığını kontrol eder
	 * 
	 * @param msg
	 * @return
	 */
	public double readDouble(String msg) {
		
		String secim;
		
		do {
			int countNokta = 0;
			System.out.print(msg);
			boolean isDigit = true;
			secim = in.next().trim();
			for (int i = 0; i < secim.length(); i++) {
				if (secim.charAt(i) == '.' && countNokta < 1)
					countNokta++;
				else if (!Character.isDigit(secim.charAt(i))) {
					isDigit = false;
				}
				
			}
			
			if (isDigit)
				return Double.parseDouble(secim);
			else
				System.out.println(language.getString("Globalization.ENTER_NUMBER_ONLY"));
		} while (true);
	}
	
	/**
	 * Ekrandan sadece sayı istediğimizde kullanıdğımız method
	 * 
	 * @param msg
	 * @return
	 */
	public int readInteger(String msg) {
		
		String secim;
		
		do {
			System.out.print(msg);
			boolean isDigit = true;
			secim = in.next().trim();
			for (int i = 0; i < secim.length(); i++) {
				if (!Character.isDigit(secim.charAt(i))) {
					isDigit = false;
				}
			}
			
			if (isDigit)
				return Integer.parseInt(secim);
			else
				System.out.println(language.getString("Globalization.ENTER_NUMBER_ONLY"));
		} while (true);
	}
	
	/**
	 * String girilen tarihinn Date Formatında Alma
	 * 
	 * @param msg
	 * @return
	 */
	public Date readDate(String msg) {
		
		String secim;
		
		do {
			System.out.print(msg);
			
			secim = in.next().trim();
			try {
				Date date = StringHelper.getInstance().stringToDate(secim);
				return date;
			} catch (IllegalArgumentException e) {
				System.out.println(language.getString("Globalization.ERROR") + " : " + e.getMessage());
				in.nextLine();
			}
			
		} while (true);
	}
	
	/**
	 * String girilen tarihinn Local Date Formatında Alma
	 * 
	 * @param msg
	 * @return
	 */
	public LocalDate readLocalDate(String msg) {
		
		String secim;
		
		do {
			System.out.print(msg);
			
			secim = in.next().trim();
			try {
				LocalDate date = StringHelper.getInstance().stringToLocalDate(secim);
				return date;
			} catch (IllegalArgumentException e) {
				System.out.println(language.getString("Globalization.ERROR") + " : " + e.getMessage());
				in.nextLine();
			}
			
		} while (true);
	}
	
	/**
	 * Hazırlanan Map içindeki Menüyü ekrana bastırıp menü oluşturma
	 * 
	 * @param title
	 * @param menu
	 * @return
	 */
	public int showMenu(String title, Map<Integer, String> menu) {
		int lineCount = mapToStringMaxLength(menu);
		printTitle(title, lineCount + 10);
		if (menu != null) {
			for (Map.Entry<Integer, String> entry : menu.entrySet()) {
				Integer key = entry.getKey();
				String val = entry.getValue();
				print(String.format("[  %02d - %-" + lineCount + "s ]", key, val), 0, true);
				
			}
		}
		
		printLine(lineCount + 10);
		
		return readInteger(language.getString("Globalization.PLEASE_ENTER_YOUR_MENU_NUMBER"));
	}
	
	private int mapToStringMaxLength(Map<Integer, String> menu) {
		int max = Integer.MIN_VALUE;
		for (Map.Entry<Integer, String> entry : menu.entrySet()) {
			String val = entry.getValue();
			if (max < val.length())
				max = val.length();
		}
		return max;
	}
	
	/**
	 * Ekrana Tab koyarak yazdırma.
	 * 
	 * @param msg
	 * @param tabCount
	 * @param ln
	 */
	public void print(String msg, int tabCount, boolean ln) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < tabCount; i++) {
			b.append("\t");
		}
		b.append(msg);
		b.append(ln ? "\n" : "");
		System.out.print(b);
		
	}
	
	/**
	 * Ekrana Ortaı bir şekilde Münü başlığı yazdırma
	 * 
	 * @param msg
	 * @param length
	 */
	public void printTitle(String msg, int lineCount) {
		int length = msg.length() + 12;
		if (lineCount < length) {
			lineCount = length;
		}
		
		printLine(lineCount);
		int center = (length - 12);
		int end = length - (msg.length() + 6);
		if (lineCount > length) {
			System.out.printf("%-" + Math.abs(Math.ceil((lineCount - length) / 2)) + "s", " ");
		}
		System.out.printf("===== %" + (Math.round(center)) + "s%" + (Math.round(end)) + "s%n", msg.trim(), " =====");
		printLine(lineCount);
	}
	
	public void printTitle(String msg) {
		printTitle(msg, msg.length());
	}
	
	public void printLine(String msg) {
		printLine(msg.length());
	}
	
	/**
	 * Ekrana belirilen adet kadar = koyan method.
	 * 
	 * @param msgLong
	 */
	public void printLine(int msgLong) {
		
		StringBuilder line = new StringBuilder();
		for (int i = 0; i < msgLong; i++) {
			line.append("=");
		}
		System.out.printf("%s\n", line);
	}
	
	public static void main(String[] args) {
		ConsoleHelper.getInstance(new GlobalStrings("tr")).printTitle("ali");
		ConsoleHelper.getInstance(new GlobalStrings("tr")).printTitle("Blog Yönetimi");
		ConsoleHelper.getInstance(new GlobalStrings("tr")).printTitle("ali ergl asd asda ads");
	}
	
	/**
	 * Eclipse de /r kontrolü için Ascii Controlü açmamız lazım.
	 * 
	 * @param remain
	 * @param total
	 */
	public void animate(int remain, int total) {
		if (remain > total) {
			throw new IllegalArgumentException();
		}
		int maxBareSize = 10; // 10unit for 100%
		int remainProcent = ((100 * remain) / total) / maxBareSize;
		final char defaultChar = '-';
		final String icon = "*";
		String bare = new String(new char[maxBareSize]).replace('\0', defaultChar) + "]";
		StringBuilder bareDone = new StringBuilder();
		bareDone.append("[");
		for (int i = 0; i < remainProcent; i++) {
			bareDone.append(icon);
		}
		String bareRemain = bare.substring(remainProcent, bare.length());
		System.out.print("\r" + bareDone + bareRemain + " " + remainProcent * 10 + "%");
		if (remain == total) {
			System.out.print("\n");
		}
	}
	
}
