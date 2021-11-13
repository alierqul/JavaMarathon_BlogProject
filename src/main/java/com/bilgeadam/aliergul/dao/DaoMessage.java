package com.bilgeadam.aliergul.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bilgeadam.aliergul.dto.DtoMessage;
import com.bilgeadam.aliergul.dto.DtoUserDetails;

public class DaoMessage implements IMessageOperations {
	
	@Override
	public boolean appendMessageDatabase(DtoMessage msg) {
		final String queryUser = "INSERT INTO public.blog_inbox(user_id, message_id, inbox_message) VALUES (?, ?, ?);";
		
		try (Connection conn = getInterfaceConnection()) {
			PreparedStatement preparedStatement = conn.prepareStatement(queryUser);
			preparedStatement.setInt(1, msg.getUserId());
			preparedStatement.setInt(2, msg.getMessageId());
			preparedStatement.setString(3, msg.getMessage());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("HATA - insert(DaoMessage): " + e.getMessage());
			return false;
		}
		return true;
		
	}
	
	@Override
	public List<DtoMessage> getListMessage(DtoUserDetails user, DtoUserDetails friend) {
		final String queryUser = "SELECT i.inbox_id, i.user_id, i.message_id, i.inbox_message, i.created_date, d.user_name, d.user_surname, u.user_email"
				+ " FROM public.blog_inbox as i INNER JOIN public.blog_users as u ON u.user_id = i.user_id"
				+ " INNER JOIN public.users_detail as d ON d.user_id = u.user_id WHERE ((i.user_id=? or i.message_id=?) and (i.user_id=? or i.message_id=?));";
		List<DtoMessage> temp = new ArrayList<>();
		try (Connection conn = getInterfaceConnection()) {
			PreparedStatement preparedStatement = conn.prepareStatement(queryUser);
			preparedStatement.setInt(1, user.getId());
			preparedStatement.setInt(2, user.getId());
			preparedStatement.setInt(3, friend.getId());
			preparedStatement.setInt(4, friend.getId());
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				int inboxID = result.getInt("inbox_id");
				int userID = result.getInt("user_id");
				int messageID = result.getInt("message_id");
				String message = result.getString("inbox_message");
				Date created_date = result.getDate("created_date");
				String nameAndSurname = result.getString("user_name") + " " + result.getString("user_surname");
				String email = result.getString("user_email");
				temp.add(new DtoMessage(inboxID, created_date, userID, messageID, message, nameAndSurname, email));
			}
			
		} catch (SQLException e) {
			System.out.println("HATA - getListMessage(DaoMessage): " + e.getMessage());
			
		}
		
		return temp;
	}
	
	@Override
	public List<DtoUserDetails> getListUserMessage(DtoUserDetails user) {
		final String queryUser = "SELECT DISTINCT ON (i.message_id) *" + " FROM public.blog_inbox as i"
				+ " INNER JOIN public.blog_users as u ON u.user_id = i.message_id"
				+ " INNER JOIN public.users_detail as d ON d.user_id = u.user_id WHERE i.user_id=?;";
		List<DtoUserDetails> temp = new ArrayList<>();
		try (Connection conn = getInterfaceConnection()) {
			PreparedStatement preparedStatement = conn.prepareStatement(queryUser);
			preparedStatement.setInt(1, user.getId());
			
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				int id = result.getInt("message_id");
				String email = result.getString("user_email");
				String user_password = result.getString("user_password");
				String metaData = result.getString("user_meta_data");
				boolean user_is_active = result.getBoolean("user_is_active");
				String user_name = result.getString("user_name");
				String user_surname = result.getString("user_surname");
				String user_phone = result.getString("user_phone");
				String user_hescode = result.getString("user_hescode");
				int user_role_id = result.getInt("user_role_id");
				Date user_created_date = result.getDate("user_created_date");
				temp.add(new DtoUserDetails(id, user_created_date, email, user_password, metaData, user_is_active,
						user_name, user_surname, user_phone, user_hescode, user_role_id));
			}
			
		} catch (SQLException e) {
			System.out.println("HATA - getListUserMessage(DtoUserDetails): " + e.getMessage());
			
		}
		
		return temp;
	}
	
}
