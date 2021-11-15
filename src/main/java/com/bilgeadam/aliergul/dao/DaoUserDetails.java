package com.bilgeadam.aliergul.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.utils.exceptions.ExceptionDeletedAccount;
import com.bilgeadam.aliergul.utils.exceptions.ExceptionIncorrectPasswordBlockedStatus;

public class DaoUserDetails implements IUserOperations<DtoUserDetails> {
	private DtoUserDetails myUser = null;
	
	private void addLogHistoy(String email, boolean isuccessful, String comment) {
		int userID = getEmailtoUserId(email);
		if (userID != -1) {
			try (Connection conn = getInterfaceConnection("addLogHistoy")) {
				final String query = "INSERT INTO public.login_log_history(log_user_id, log_is_successful, log_comment)VALUES (?,?,?);";
				PreparedStatement pre = conn.prepareStatement(query);
				
				pre.setInt(1, userID);
				pre.setBoolean(2, isuccessful);
				pre.setString(3, comment);
				pre.executeUpdate();
				
			} catch (SQLException e) {
				System.out.println("HATA - addLogHistoy: " + e.getMessage());
				
			}
		}
		
	}
	
	private int getEmailtoUserId(String email) {
		int id = -1;
		try (Connection conn = getInterfaceConnection("getEmailtoUserId")) {
			conn.isClosed();
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
		// "call registerNewAccount('email','pass','meta','d','d','d','d',2);";
		final String queryUser = "call registerNewAccount(?,?,?,?,?,?,?,?);";
		final int USER_ROLE = 2;
		try (Connection conn = getInterfaceConnection("createAccount")) {
			PreparedStatement createUser = conn.prepareStatement(queryUser);
			createUser.setString(1, dto.getEmail().toLowerCase());
			createUser.setString(2, convertMD5(dto.getPasswod()));
			createUser.setString(3, createMetaData(dto).toLowerCase());
			createUser.setString(4, dto.getName());
			createUser.setString(5, dto.getSurName());
			createUser.setString(6, dto.getPhone());
			createUser.setString(7, dto.getHescode());
			createUser.setInt(8, USER_ROLE);
			int result = createUser.executeUpdate();
			addLogHistoy(dto.getEmail(), true, "createAccount");
			System.out.println("createAccount = " + result);
			return true;
			
		} catch (SQLException e) {
			System.out.println("HATA - insert(UserDto): " + e.getMessage());
			return false;
		}
		
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
	
	@Override
	public boolean logIn(DtoUserDetails dto) throws ExceptionIncorrectPasswordBlockedStatus, ExceptionDeletedAccount {
		boolean isSuccessful = false;
		final String queryUser = "SELECT u.user_is_active, u.user_is_deleted FROM public.blog_users as u where u.user_email=? AND u.user_password=?;";
		try (Connection conn = getInterfaceConnection("logIn")) {
			PreparedStatement preparedStatement = conn.prepareStatement(queryUser);
			preparedStatement.setString(1, dto.getEmail());
			preparedStatement.setString(2, convertMD5(dto.getPasswod()));
			ResultSet result = preparedStatement.executeQuery();
			
			while (result.next()) {
				if (result.getBoolean(2)) {
					addLogHistoy(dto.getEmail(), isSuccessful, "logIn");
					throw new ExceptionDeletedAccount("Globalization.DELETE_ACCOUNT");
				} else if (!result.getBoolean(1)) {
					addLogHistoy(dto.getEmail(), isSuccessful, "logIn");
					throw new ExceptionIncorrectPasswordBlockedStatus("Globalization.BLOKE_USER_PASSWORD");
				} else {
					this.myUser = getUserDetails(dto);
					isSuccessful = true;
				}
				
			}
			addLogHistoy(dto.getEmail(), isSuccessful, "logIn");
			
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
		try (Connection conn = getInterfaceConnection("updateUser")) {
			// blog_users
			PreparedStatement preparedStatement = conn.prepareStatement(queryUser);
			preparedStatement.setString(1, dto.getEmail().toLowerCase());
			preparedStatement.setString(2, convertMD5(dto.getPasswod()));
			
			preparedStatement.setString(3, createMetaData(dto).toLowerCase());
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
	
	private String createMetaData(DtoUserDetails dto) {
		return dto.getName() + dto.getSurName() + dto.getEmail().substring(0,
				dto.getEmail().indexOf("@") > 0 ? dto.getEmail().indexOf("@") : dto.getEmail().length());
	}
	
	@Override
	public DtoUserDetails getUserDetails(DtoUserDetails dto) {
		final String query = "SELECT  u.user_id, u.user_email, u.user_password, u.user_is_active, u.user_meta_data,"
				+ " d.user_name,d.user_surname, d.user_phone,d.user_hescode, d.user_role_id, d.user_created_date"
				+ " FROM public.users_detail AS d JOIN public.blog_users AS u ON u.user_id = d.user_id WHERE u.user_email=? AND u.user_is_deleted=false;";
		List<DtoUserDetails> tempList = getFindQueryUser(query, dto.getEmail());
		return tempList.size() > 0 ? tempList.get(0) : null;
	}
	
	@Override
	public List<DtoUserDetails> getFindUser(DtoUserDetails dto) {
		final String query = "SELECT  u.user_id, u.user_email, u.user_password, u.user_is_active, u.user_meta_data,"
				+ " d.user_name,d.user_surname, d.user_phone,d.user_hescode, d.user_role_id, d.user_created_date"
				+ " FROM public.users_detail AS d JOIN public.blog_users AS u ON u.user_id = d.user_id WHERE u.user_meta_data LIKE ? AND u.user_is_deleted=false ;";
		List<DtoUserDetails> temp = getFindQueryUser(query, dto.getMetaData());
		int index = temp.stream().map(u -> u.getId()).collect(Collectors.toList()).indexOf(myUser.getId());
		if (index >= 0) {
			// Arama sonucundan kendini çıkarıyoruz.
			temp.remove(index);
		}
		return temp;
	}
	
	private List<DtoUserDetails> getFindQueryUser(String query, String findString) {
		List<DtoUserDetails> tempList = new ArrayList<>();
		DtoUserDetails uDetails = null;
		try (Connection conn = getInterfaceConnection("getFindQueryUser")) {
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
