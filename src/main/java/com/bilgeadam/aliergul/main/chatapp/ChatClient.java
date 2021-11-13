package com.bilgeadam.aliergul.main.chatapp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.bilgeadam.aliergul.dto.DtoMessage;
import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.main.language.MenuLanguage;
import com.bilgeadam.aliergul.utils.helper.ConsoleHelper;

public enum ChatClient {
	getInstance;
	
	public void startClient(DtoUserDetails user, DtoUserDetails friend) {
		
		PortInfo port = new PortInfo();
		Socket socket;
		try {
			socket = new Socket(port.getIdAdress(), port.getPort());
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(outputStream);
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			
			while (true) {
				Object obj = null;
				
				String msg = ConsoleHelper.getInstance(MenuLanguage.getInstance().getLanguage())
						.readString(user.getName() + ": ");
				DtoMessage msjSend = new DtoMessage(user.getId(), friend.getId(), msg);
				objOut.writeObject(msjSend);
				
			}
			
		} catch (IOException e1) {
			System.err.println("HATA: startClient(ChatClient) :" + e1.getMessage());
			
		}
		
	}
}
