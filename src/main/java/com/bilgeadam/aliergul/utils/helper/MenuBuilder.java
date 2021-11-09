package com.bilgeadam.aliergul.utils.helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class MenuBuilder {
	private String title = "";
	private String body = "";
	private String selectMessage = "";
	private int lineCount = 0;
	private Map<Object, String> menu;
	private char icon = '▒';// Alt + 176 = ░ Alt + 177 = ▒ Alt + 178 = ▓
	private Scanner in = null;
	
	public MenuBuilder(Builder build) {
		this.title = build.title;
		this.body = build.body;
		this.selectMessage = build.selectMessage;
		this.lineCount = build.lineCount;
		this.menu = build.menu;
		this.icon = build.icon;
		in = new Scanner(System.in);
	}
	
	// public static void main(String[] args) {
	// new MenuBuilder.Builder().title("Ali başlık 1").body("user Panel").addMenu(1,
	// "Ali Ergül 1987 01 21 Erkek")
	// .addMenu(" Detay", "Ugur Ergül 1987 01 21 Erkek çalışıyır").icon('▒')
	// .selectMessage("Lüfen Seçtiğiniz kayıt numarasını
	// yazınız....").build().show();
	// }
	
	public MenuBuilder show() {
		int rowCount = rowCountFind() + 4;
		if (lineCount < rowCount)
			lineCount = rowCount;
		printLine(lineCount);
		if (!title.isEmpty())
			centerWrite(title.toUpperCase());
		if (!body.isEmpty())
			centerWrite(toPascalCaseString(body));
		if (menu != null)
			showMenu();
		printLine(lineCount);
		
		return this;
	}
	
	public int readInteger() {
		
		String secim;
		
		do {
			if (!selectMessage.isEmpty()) {
				centerWrite(toPascalCaseString(selectMessage));
				printLine(lineCount);
			}
			boolean isDigit = true;
			secim = in.next().trim();
			for (int i = 0; i < secim.length(); i++) {
				if (!Character.isDigit(secim.charAt(i))) {
					isDigit = false;
				}
			}
			
			if (isDigit)
				return Integer.parseInt(secim);
		} while (true);
	}
	
	public String readString() {
		
		String strLine = "";
		do {
			try {
				if (!selectMessage.isEmpty()) {
					centerWrite(toPascalCaseString(selectMessage));
					printLine(lineCount);
				}
				strLine = in.next();
				return strLine;
			} catch (Exception e) {
				System.out.println(strLine + " = " + e.getMessage());
			}
		} while (true);
		
	}
	
	private void showMenu() {
		for (Map.Entry<Object, String> entry : menu.entrySet()) {
			Object key = entry.getKey();
			String val = entry.getValue();
			if (key instanceof Integer) {
				String row = String.format(icon + " %02d - %s", (int) key, val);
				System.out.printf("%s%" + (lineCount - row.length()) + "s%n", row, icon);
			}
			
			if (key instanceof String) {
				String row = String.format(icon + " %s %s", (String) key, val);
				System.out.printf("%s%" + (lineCount - row.length()) + "s%n", row, icon);
			}
			
		}
	}
	
	private int rowCountFind() {
		List<Integer> rowCount = new ArrayList<>();
		rowCount.add(title.length());
		rowCount.add(body.length());
		if (menu != null) {
			for (Map.Entry<Object, String> entry : menu.entrySet()) {
				Object key = entry.getKey();
				String val = entry.getValue();
				
				rowCount.add(val.length() + key.toString().length() + 4);
				
			}
		}
		rowCount = rowCount.stream().sorted().collect(Collectors.toList());
		
		return rowCount.get(rowCount.size() - 1);
	}
	
	public void centerWrite(String msg) {
		int length = msg.length() + 4;
		String row = "";
		if (lineCount > length) {
			row = String.format(icon + "%-" + Math.abs(Math.ceil((lineCount - length) / 2)) + "s", " ");
			
		}
		row = row.concat(msg);
		System.out.printf("%s%" + (lineCount - row.length()) + "s%n", row, icon);
		
	}
	
	private void printLine(int msgLong) {
		
		StringBuilder line = new StringBuilder();
		for (int i = 0; i < msgLong; i++) {
			line.append(icon);
		}
		System.out.printf("%s\n", line);
	}
	
	public String toPascalCaseString(String metin) {
		StringTokenizer st = new StringTokenizer(metin, " ");
		StringBuilder newMetin = new StringBuilder();
		while (st.hasMoreTokens()) {
			String A = st.nextToken();
			A = A.substring(0, 1).toUpperCase().concat(A.substring(1, A.length()));
			newMetin.append(A);
			newMetin.append(" ");
		}
		
		return newMetin.toString();
	}
	
	public static class Builder {
		private String title = "";
		private String body = "";
		private String selectMessage = "";
		private int lineCount = 0;
		private Map<Object, String> menu = null;
		private char icon = '▒';
		
		public Builder() {
			super();
			
		}
		
		public Builder title(String title) {
			this.title = title;
			return this;
		}
		
		public Builder icon(char icon) {
			this.icon = icon;
			return this;
		}
		
		public Builder body(String body) {
			this.body = body;
			return this;
		}
		
		public Builder selectMessage(String selectMessage) {
			this.selectMessage = selectMessage;
			return this;
		}
		
		public Builder addMenu(Object obj, String msj) {
			if (menu == null) {
				this.menu = new LinkedHashMap<Object, String>();
				
			}
			this.menu.put(obj, msj);
			
			return this;
		}
		
		public Builder lineCount(int lineCount) {
			this.lineCount = lineCount;
			return this;
		}
		
		public MenuBuilder build() {
			return new MenuBuilder(this);
		}
	}
}
