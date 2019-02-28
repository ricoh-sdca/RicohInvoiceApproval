package com.invoiceApproval.doa;

import com.invoiceApproval.entity.Organization;

public interface IOrganizationDao {
	public Organization find(Integer id) throws Exception;
}
