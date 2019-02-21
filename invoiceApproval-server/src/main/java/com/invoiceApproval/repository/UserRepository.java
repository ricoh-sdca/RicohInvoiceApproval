package com.invoiceApproval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;

import com.invoiceApproval.entity.User;

@RestResource(exported = false)
public interface UserRepository extends JpaRepository<User, String> {

	@Query("select distinct u from User u where u.username =?1  and u.password =?2 and u.isActive ='Y'")
	public User findUserByCredential(String userName,String password);
}