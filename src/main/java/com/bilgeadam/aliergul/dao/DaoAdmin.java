package com.bilgeadam.aliergul.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.bilgeadam.aliergul.dto.DtoBlogRole;
import com.bilgeadam.aliergul.dto.DtoUserDetails;
import com.bilgeadam.aliergul.utils.exceptions.ExceptionNotAuthorizedError;

public class DaoAdmin implements IAdminOperations<DtoUserDetails> {
	
	private DtoBlogRole role;
	
	public DaoAdmin(DtoUserDetails dto) {
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
	public void deleteAccount(DtoUserDetails dto) throws ExceptionNotAuthorizedError {
		if (this.role.isUserDeleteAccount()) {
			try (Connection conn = getInterfaceConnection()) {
				final String query = "UPDATE public.blog_users 	SET user_is_deleted=? WHERE user_id=?;";
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
		} else {
			throw new ExceptionNotAuthorizedError("Globalization.ERROR_AUTHORIZED");
		}
		
	}
	
	@Override
	public void changedUserRoleStatus(DtoUserDetails dto) throws ExceptionNotAuthorizedError {
		if (this.role.isUserDeleteAccount()) {
			
		} else {
			throw new ExceptionNotAuthorizedError("Globalization.ERROR_AUTHORIZED");
		}
		
	}
	
	@Override
	public void changedUserActiveStatus(DtoUserDetails dto) throws ExceptionNotAuthorizedError {
		if (this.role.isUserDeleteAccount()) {
			
		} else {
			throw new ExceptionNotAuthorizedError("Globalization.ERROR_AUTHORIZED");
		}
		
	}
	
	@Override
	public void addNewRole(DtoUserDetails dto) throws ExceptionNotAuthorizedError {
		if (this.role.isUserDeleteAccount()) {
			
		} else {
			throw new ExceptionNotAuthorizedError("Globalization.ERROR_AUTHORIZED");
		}
		
	}
	
	@Override
	public Map<String, Integer> countOfRecordByRoles() throws ExceptionNotAuthorizedError {
		if (this.role.isUserDeleteAccount()) {
			return null;
		} else {
			throw new ExceptionNotAuthorizedError("Globalization.ERROR_AUTHORIZED");
		}
		
	}
	
}
