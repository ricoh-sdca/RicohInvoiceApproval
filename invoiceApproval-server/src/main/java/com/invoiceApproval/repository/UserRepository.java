package com.invoiceApproval.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;

import com.invoiceApproval.entity.User;

/**
 * @author atul_jadhav
 *
 */
@RestResource(exported = false)
public interface UserRepository extends JpaRepository<User, String> {

	
	/**
	 * This method return valid user by comparing user name,password and user status 
	 * @param userName
	 * @param password
	 * @return User
	 */
	@Query("select u from User u where u.userName =?1 and u.password =?2 and u.userStatus=?3")
	public User findUserByCredential(String userName,String password,String status);
	
	@Query("select u from User u where u.approvalLevel=?1 and u.userStatus=?2")
	public List<User> getUsersByApprovalLevel(String approverLevel,String status);
	
}