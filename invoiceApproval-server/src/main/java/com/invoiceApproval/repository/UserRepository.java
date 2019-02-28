package com.invoiceApproval.repository;

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
	@Query("select u from User u where u.userName =?1 and u.password =?2 and u.userStatus='active'")
	public User findUserByCredential(String userName,String password);
}