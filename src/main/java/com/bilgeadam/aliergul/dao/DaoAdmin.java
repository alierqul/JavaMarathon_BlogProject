package com.bilgeadam.aliergul.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.bilgeadam.aliergul.dto.DtoBlogRole;
import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.utils.exceptions.ExceptionNotAuthorizedError;

public class DaoAdmin implements IAdminOperations<DtoUserDetails> {
	
	private DtoBlogRole role;
	
	public DaoAdmin(DtoUserDetails dto) {
		super();
		try (Connection conn = getInterfaceConnection()) {
			final String query = "SELECT role_id, role_name, user_change_active, view_number_of_record, user_delete_account, user_change_role, add_new_role, created_date"
					+ "	FROM public.blog_rollers WHERE role_id=?; ";
			PreparedStatement pStatement = conn.prepareStatement(query);
			pStatement.setInt(1, dto.getRoleId());
			ResultSet result = pStatement.executeQuery();
			
			while (result.next()) {
				int id = result.getInt("role_id");
				String roleName = result.getString("role_name");
				boolean userChangeActive = result.getBoolean("user_change_active");
				boolean viewNumberOfRecord = result.getBoolean("view_number_of_record");
				boolean userDeleteAccount = result.getBoolean("user_delete_account");
				boolean userChangeRole = result.getBoolean("user_change_role");
				boolean addNewRole = result.getBoolean("add_new_role");
				Date createDate = result.getDate("created_date");
				this.role = new DtoBlogRole(id, createDate, roleName, userChangeActive, viewNumberOfRecord,
						userDeleteAccount, userChangeRole, addNewRole);
			}
		} catch (SQLException e) {
			System.out.println("HATA: DaoAdmin() :" + e.getMessage());
		}
	}
	
	@Override
	public boolean deleteAccount(DtoUserDetails dto) throws ExceptionNotAuthorizedError {
		int result = -1;
		if (this.role.isUserDeleteAccount()) {
			try (Connection conn = getInterfaceConnection()) {
				final String query = "UPDATE public.blog_users SET user_is_deleted=? WHERE user_id=?;";
				PreparedStatement pStatement = conn.prepareStatement(query);
				pStatement.setBoolean(1, true);
				pStatement.setInt(2, dto.getId());
				result = pStatement.executeUpdate();
				
			} catch (SQLException e) {
				System.out.println("HATA: deleteAccount() :" + e.getMessage());
			}
		} else {
			throw new ExceptionNotAuthorizedError("Globalization.ERROR_AUTHORIZED");
		}
		if (result > 0)
			return true;
		else
			return false;
		
	}
	
	@Override
	public boolean changedUserRoleStatus(DtoUserDetails dto) throws ExceptionNotAuthorizedError {
		int result = -1;
		if (this.role.isUserChangeRole()) {
			try (Connection conn = getInterfaceConnection()) {
				final String query = "UPDATE public.users_detail SET user_role_id=? WHERE user_id=?;";
				PreparedStatement pStatement = conn.prepareStatement(query);
				pStatement.setInt(1, dto.getRoleId());
				pStatement.setInt(2, dto.getId());
				result = pStatement.executeUpdate();
				
			} catch (SQLException e) {
				System.out.println("HATA: changedUserRoleStatus() :" + e.getMessage());
			}
		} else {
			throw new ExceptionNotAuthorizedError("Globalization.ERROR_AUTHORIZED");
		}
		if (result > 0)
			return true;
		else
			return false;
		
	}
	
	@Override
	public boolean changedUserActiveStatus(DtoUserDetails dto) throws ExceptionNotAuthorizedError {
		int result = -1;
		if (this.role.isUserChangeActive()) {
			try (Connection conn = getInterfaceConnection()) {
				final String query = "UPDATE public.blog_users SET user_is_active=? WHERE user_id=?;";
				PreparedStatement pStatement = conn.prepareStatement(query);
				pStatement.setBoolean(1, dto.isActive());
				pStatement.setInt(2, dto.getId());
				result = pStatement.executeUpdate();
				
			} catch (SQLException e) {
				System.out.println("HATA: changedUserActiveStatus() :" + e.getMessage());
			}
		} else {
			throw new ExceptionNotAuthorizedError("Globalization.ERROR_AUTHORIZED");
		}
		if (result > 0)
			return true;
		else
			return false;
	}
	
	@Override
	public Map<String, Integer> countOfRecordByRoles() throws ExceptionNotAuthorizedError {
		Map<String, Integer> temp = new HashMap<>();
		if (this.role.isUserDeleteAccount()) {
			
			try (Connection conn = getInterfaceConnection()) {
				final String query = "SELECT b.role_name, r.role_count	FROM users_of_number_record AS r INNER JOIN blog_rollers AS b ON b.role_id = r.role_id";
				PreparedStatement pStatement = conn.prepareStatement(query);
				
				ResultSet result = pStatement.executeQuery();
				while (result.next()) {
					temp.put(result.getString("role_name"), result.getInt("role_count"));
				}
			} catch (SQLException e) {
				System.out.println("HATA: changedUserActiveStatus() :" + e.getMessage());
			}
		} else {
			throw new ExceptionNotAuthorizedError("Globalization.ERROR_AUTHORIZED");
		}
		
		return temp;
		
	}
	
	@Override
	public DtoBlogRole getUserRole(DtoUserDetails dto) {
		
		return this.role;
	}
	
	@Override
	public Map<Date, Integer> countOfRecordByDay() throws ExceptionNotAuthorizedError {
		Map<Date, Integer> temp = new LinkedHashMap<>();
		if (this.role.isViewNumberOfRecord()) {
			
			try (Connection conn = getInterfaceConnection()) {
				final String query = "SELECT DATE(log_login_date)as logDate , COUNT(*) as logCount FROM public.login_log_history GROUP BY DATE(log_login_date) ORDER BY DATE(log_login_date) DESC;";
				PreparedStatement pStatement = conn.prepareStatement(query);
				
				ResultSet result = pStatement.executeQuery();
				while (result.next()) {
					temp.put(result.getDate("logDate"), result.getInt("logCount"));
				}
			} catch (SQLException e) {
				System.out.println("HATA: countOfRecordByDay() :" + e.getMessage());
			}
		} else {
			throw new ExceptionNotAuthorizedError("Globalization.ERROR_AUTHORIZED");
		}
		
		return temp;
	}
	
}
