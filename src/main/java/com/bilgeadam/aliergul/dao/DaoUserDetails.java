package com.bilgeadam.aliergul.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.utils.exceptions.ExceptionIncorrectPasswordBlockedStatus;

public class DaoUserDetails implements IUserOperations<DtoUserDetails> {
	
	private int getNewUserID() {
		try (Connection conn = getInterfaceConnection()) {
			final String query = "SELECT MAX(user_id) FROM public.blog_users;";
			ResultSet result = conn.prepareStatement(query).executeQuery();
			while (result.next()) {
				return result.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("HATA - getNewUserID: " + e.getMessage());
		}
		return -1;
	}
	
	private void addLogHistoy(String email, boolean isuccessful) {
		int userID = getEmailtoUserId(email);
		if (userID != -1) {
			try (Connection conn = getInterfaceConnection()) {
				final String query = "INSERT INTO public.login_log_history(log_user_id, log_is_successful)VALUES (  ?, ?);";
				PreparedStatement pre = conn.prepareStatement(query);
				
				pre.setInt(1, userID);
				pre.setBoolean(2, isuccessful);
				pre.executeUpdate();
				
			} catch (SQLException e) {
				System.out.println("HATA - addLogHistoy: " + e.getMessage());
				
			}
		}
		
	}
	
	private int getEmailtoUserId(String email) {
		int id = -1;
		try (Connection conn = getInterfaceConnection()) {
			
			final String query = "SELECT user_id FROM public.blog_users where user_email=?;";
			PreparedStatement pre = conn.prepareStatement(query);
			pre.setString(1, email);
			ResultSet result = pre.executeQuery();
			while (result.next()) {
				id = result.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("HATA - getEmailtoUserId: " + e.getMessage());
		}
		return id;
		
	}
	
	@Override
	public boolean createAccount(DtoUserDetails dto) {
		final String queryUser = "INSERT INTO public.blog_users( user_email, user_password ,user_meta_data) "
				+ "VALUES (?, ?, ?);";
		final String queryDetails = "INSERT INTO public.users_detail(user_id,user_name, user_surname, user_phone, user_hescode, user_role_id)	VALUES ( ?,?, ?, ?, ?, 3);";
		try (Connection conn = getInterfaceConnection()) {
			PreparedStatement preparedStatement = conn.prepareStatement(queryUser);
			preparedStatement.setString(1, dto.getEmail());
			preparedStatement.setString(2, dto.getPasswod());
			preparedStatement.setString(3, dto.getMetaData());
			if (preparedStatement.execute()) {
				preparedStatement = conn.prepareStatement(queryDetails);
				preparedStatement.setInt(1, getNewUserID());
				preparedStatement.setString(2, dto.getName());
				preparedStatement.setString(3, dto.getSurName());
				preparedStatement.setString(4, dto.getPhone());
				preparedStatement.setString(5, dto.getHescode());
				preparedStatement.executeUpdate();
				return true;
			}
			
		} catch (SQLException e) {
			System.out.println("HATA - insert(UserDto): " + e.getMessage());
			return false;
		}
		return false;
	}
	
	@Override
	public boolean logIn(DtoUserDetails dto) throws ExceptionIncorrectPasswordBlockedStatus {
		boolean isSuccessful = false;
		final String queryUser = "SELECT u.user_is_active FROM public.blog_users as u where u.user_email=? AND u.user_password=?;";
		try (Connection conn = getInterfaceConnection()) {
			PreparedStatement preparedStatement = conn.prepareStatement(queryUser);
			preparedStatement.setString(1, dto.getEmail());
			preparedStatement.setString(2, dto.getPasswod());
			ResultSet result = preparedStatement.executeQuery();
			
			while (result.next()) {
				if (!result.getBoolean(1)) {
					addLogHistoy(dto.getEmail(), isSuccessful);
					throw new ExceptionIncorrectPasswordBlockedStatus("Globalization.BLOKE_USER_PASSWORD");
				} else {
					isSuccessful = true;
				}
				
			}
			addLogHistoy(dto.getEmail(), isSuccessful);
			
		} catch (SQLException e) {
			System.out.println("HATA - insert(UserDto): " + e.getMessage());
			
		}
		return isSuccessful;
	}
	
	@Override
	public boolean updateUser(DtoUserDetails dto) {
		boolean isSuccessful = false;
		final String queryUser = "UPDATE public.blog_users SET user_email=?, user_password=?, user_meta_data=? WHERE user_id=?; ";
		final String queryDetails = "UPDATE public.users_detail SET user_name=?, user_surname=?, user_phone=?, user_hescode=? WHERE user_id=?";
		try (Connection conn = getInterfaceConnection()) {
			// blog_users
			PreparedStatement preparedStatement = conn.prepareStatement(queryUser);
			preparedStatement.setString(1, dto.getEmail());
			preparedStatement.setString(2, dto.getPasswod());
			String metaData = dto.getName() + dto.getSurName() + dto.getHescode()
					+ dto.getEmail().substring(0, dto.getEmail().indexOf("@"));
			preparedStatement.setString(3, metaData);
			preparedStatement.setInt(4, dto.getId());
			int num = preparedStatement.executeUpdate();
			// users_detail
			preparedStatement = conn.prepareStatement(queryDetails);
			preparedStatement.setString(1, dto.getName());
			preparedStatement.setString(2, dto.getSurName());
			preparedStatement.setString(3, dto.getPhone());
			preparedStatement.setString(4, dto.getHescode());
			preparedStatement.setInt(5, dto.getId());
			num = preparedStatement.executeUpdate();
			if (num > 0) {
				isSuccessful = true;
			}
			
		} catch (SQLException e) {
			System.out.println("HATA - updateUser(DtoUserDetails): " + e.getMessage());
			
		}
		return isSuccessful;
	}
	
	@Override
	public DtoUserDetails getUserDetails(DtoUserDetails dto) {
		final String query = "SELECT  u.user_id, u.user_email, u.user_password, u.user_is_active, u.user_meta_data,"
				+ " d.user_name,d.user_surname, d.user_phone,d.user_hescode, d.user_role_id, d.user_created_date"
				+ " FROM public.users_detail AS d JOIN public.blog_users AS u ON u.user_id = d.user_id WHERE u.user_email=?;";
		List<DtoUserDetails> tempList = getFindQueryUser(query, dto.getEmail());
		return tempList.size() > 0 ? tempList.get(0) : null;
	}
	
	@Override
	public List<DtoUserDetails> getFindUser(DtoUserDetails dto) {
		final String query = "SELECT  u.user_id, u.user_email, u.user_password, u.user_is_active, u.user_meta_data,"
				+ " d.user_name,d.user_surname, d.user_phone,d.user_hescode, d.user_role_id, d.user_created_date"
				+ " FROM public.users_detail AS d JOIN public.blog_users AS u ON u.user_id = d.user_id WHERE u.user_meta_data LIKE ?;";
		return getFindQueryUser(query, dto.getMetaData());
	}
	
	private List<DtoUserDetails> getFindQueryUser(String query, String findString) {
		List<DtoUserDetails> tempList = new ArrayList<>();
		DtoUserDetails uDetails = null;
		try (Connection conn = getInterfaceConnection()) {
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, findString);
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				int id = result.getInt("user_id");
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
				uDetails = new DtoUserDetails(id, user_created_date, email, user_password, metaData, user_is_active,
						user_name, user_surname, user_phone, user_hescode, user_role_id);
				tempList.add(uDetails);
			}
		} catch (SQLException e) {
			System.out.println("HATA - getFindQueryUser(DtoUserDetails): " + e.getMessage());
			
		}
		return tempList;
	}
	
}
