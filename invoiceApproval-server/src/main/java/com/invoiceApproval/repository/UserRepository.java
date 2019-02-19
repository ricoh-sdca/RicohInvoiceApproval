package com.invoiceApproval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.invoiceApproval.entity.User;


/*@RepositoryRestResource(path = "/users")
public interface UserRepository extends JpaRepository<User, String> {

}
*/

/**
 * 
 * Replace the @RepositoryRestResource(path = "/users") annotation with @RestResource(exported = false) annotation. 
 * This annotation informs Spring Boot not to export the methods as REST endpoints.
 * @author aditya_thote
 *
 */
@RestResource(exported = false)
public interface UserRepository extends JpaRepository<User, String> {

}