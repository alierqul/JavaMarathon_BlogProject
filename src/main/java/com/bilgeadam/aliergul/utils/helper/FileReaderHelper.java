package com.bilgeadam.aliergul.utils.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public enum FileReaderHelper {
	getInstance;
	
	public String readFiletoString(String path) {
		
		StringBuffer srBuffer = new StringBuffer();
		
		File file = new File(path);
		try {
			FileReader fR = new FileReader(file);
			BufferedReader bR = new BufferedReader(fR);
			String text;
			while ((text = bR.readLine()) != null) {
				srBuffer.append(text).append("\n");
				
			}
			bR.close();
			
		} catch (IOException ex) {
			ex.printStackTrace();
			return "";
			
		}
		
		return srBuffer.toString();
		
	}
	
}
