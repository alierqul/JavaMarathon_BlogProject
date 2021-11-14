package com.bilgeadam.aliergul.main.chatapp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.bilgeadam.aliergul.controller.InboxController;
import com.bilgeadam.aliergul.dto.DtoMessage;
import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.main.language.MenuLanguage;
import com.bilgeadam.aliergul.utils.helper.MenuBuilder;
import com.bilgeadam.aliergul.utils.language.GlobalStrings;

public enum ChatServer {
	getInstance;
	
	private DtoUserDetails user;
	private DtoUserDetails friend;
	private PortInfo port;
	private InboxController iControleer;
	private List<DtoMessage> listMessageHistory;
	private GlobalStrings language = MenuLanguage.getInstance().getLanguage();
	
	private Thread newMessageConroleLoop = new Thread() {
		
		public void run() {
			while (true) {
				if (listMessageHistory.size() != iControleer.getListMessage(user, friend).size()) {
					listMessageHistory = iControleer.getListMessage(user, friend);
					historyMessageBuilder(listMessageHistory).show();
					System.out.print("[ " + user.getName() + "] : ");
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					
				}
			}
			
		}
	};
	
	public void startServer(DtoUserDetails user, DtoUserDetails friend) {
		
		this.listMessageHistory = new ArrayList<>();
		this.port = new PortInfo();
		this.user = user;
		this.friend = friend;
		iControleer = InboxController.getInstance;
		
		try (ServerSocket sServer = new ServerSocket(port.getPort())) {
			Socket socket = sServer.accept();
			newMessageConroleLoop.start();
			new MenuBuilder.Builder().title(language.getString("Globalization.CHAT_APP_MENU"))
					.body(user.getName() + " " + language.getString("Globalization.CHATAPP_JOIN")).lineCount(50).build()
					.show();
			inComingMessage(socket);
		} catch (IOException e) {
			System.err.println("Server Başlatılamadı.");
			
		}
	}
	
	private void inComingMessage(Socket socket) {
		try {
			ObjectInputStream objectReader = new ObjectInputStream(socket.getInputStream());
			Object receivedMessage = null;
			while (true) {
				try {
					
					if ((receivedMessage = objectReader.readObject()) instanceof DtoMessage) {
						DtoMessage msj = (DtoMessage) receivedMessage;
						if (msj.getMessage().equals("0")) {
							socket.close();
							newMessageConroleLoop.stop();
							break;
						} else {
							iControleer.appendMessageDatabase(msj);
						}
						
					}
					
				} catch (ClassNotFoundException e) {
					
					e.printStackTrace();
				}
				
			}
		} catch (IOException e) {
			System.err.println("Gelen Veri Okunamadı.");
		}
		
	}
	
	private MenuBuilder historyMessageBuilder(List<DtoMessage> listMessage) {
		System.out.println();
		MenuBuilder histortyMessage = new MenuBuilder.Builder().title(friend.getName() + " " + friend.getSurName())
				.selectMessage(language.getString("Globalization.EXIT")).build();
		int id = 0;
		for (DtoMessage message : listMessage) {
			id++;
			
			String key = String.format("[%03d]%s:", id, message.getNameAndSurname());
			histortyMessage.addMenu(key, message.getMessage());
		}
		return histortyMessage;
	}
	
}
