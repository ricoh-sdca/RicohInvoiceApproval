package com.invoiceApproval.service;

import com.invoiceApproval.entity.Organization;

public interface IOrgnizationService {

	public Organization find(Integer id) throws Exception;
}
