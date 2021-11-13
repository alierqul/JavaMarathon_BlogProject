package com.bilgeadam.aliergul.utils.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import com.bilgeadam.aliergul.dto.DtoUserDetails;

public enum SharedPreferencesHelper {
	getInstance;
	
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	private static final String PATH = "./src/main/java/com/bilgeadam/aliergul/data/data.txt";
	
	public DtoUserDetails readToFile() {
		File file = new File(PATH);
		try (ObjectInputStream output = new ObjectInputStream(new FileInputStream(file));) {
			Object obj = null;
			if ((obj = output.readObject()) != null) {
				DtoUserDetails u = (DtoUserDetails) obj;
				u.setPasswod(StringHelper.getInstance().decodeBase64(u.getPasswod()));
				return u;
			}
			
		} catch (IOException | ClassNotFoundException ex) {
			System.err.println("HATA: readToFile " + ex.getMessage());
		}
		return null;
		
	}
	
	public boolean writeToFile(DtoUserDetails dto) {
		Map<String, String> map = null;
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(new File(PATH)))) {
			dto.setPasswod(StringHelper.getInstance().encodeBase64(dto.getPasswod()));
			output.writeObject(dto);
			output.flush();
			
			return true;
		} catch (IOException ex) {
			System.err.println("HATA: writeToFile " + ex.getMessage());
			return false;
		}
	}
}
