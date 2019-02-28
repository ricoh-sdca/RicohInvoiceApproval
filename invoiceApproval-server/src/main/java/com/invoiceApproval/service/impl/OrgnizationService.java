package com.invoiceApproval.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invoiceApproval.doa.IOrganizationDao;
import com.invoiceApproval.entity.Organization;
import com.invoiceApproval.service.IOrgnizationService;

@Service
public class OrgnizationService implements IOrgnizationService {

	private static final Logger logger = LogManager.getLogger(OrgnizationService.class);
	
	@Autowired
	IOrganizationDao organizationDao;
	
	@Override
	public Organization find(Integer id){
		try {
			logger.info("Enter find() of OrgnizationService");
			return organizationDao.find(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
