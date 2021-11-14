package com.bilgeadam.aliergul.main.chatapp;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.bilgeadam.aliergul.dto.DtoMessage;
import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.main.language.MenuLanguage;
import com.bilgeadam.aliergul.utils.helper.ConsoleHelper;
import com.bilgeadam.aliergul.utils.language.GlobalStrings;

public enum ChatClient {
	getInstance;
	
	private GlobalStrings language = MenuLanguage.getInstance().getLanguage();
	
	public void startClient(DtoUserDetails user, DtoUserDetails friend) {
		
		PortInfo port = new PortInfo();
		try (ObjectOutputStream objOut = new ObjectOutputStream(
				(new Socket(port.getIdAdress(), port.getPort())).getOutputStream())) {
			
			while (true) {
				String msg = ConsoleHelper.getInstance(language).readString(user.getName() + ": ");
				DtoMessage msjSend = new DtoMessage(user.getId(), friend.getId(), msg);
				objOut.writeObject(msjSend);
				if (msg.equals("0")) {
					break;
				}
				
			}
			
		} catch (IOException e1) {
			System.err.println("HATA: startClient(ChatClient) :" + e1.getMessage());
			
		}
		
	}
}
