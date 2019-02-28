package com.invoiceApproval.doa.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.invoiceApproval.doa.IOrganizationDao;
import com.invoiceApproval.entity.Organization;
import com.invoiceApproval.repository.OrganizationRepository;

@Repository
@Transactional
public class OrganizationDao implements IOrganizationDao {

	@Autowired
	OrganizationRepository repository;
	
	@Override
	public Organization find(Integer id) throws Exception {
		return repository.getOne(id);
	}

}
