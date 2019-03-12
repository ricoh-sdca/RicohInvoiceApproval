package com.invoiceApproval.Utils;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.invoiceApproval.entity.ResponseVO;

public class ResponseUtils {

	public static ModelMap getModelMap(ResponseVO responseVO,List<?> ruleDataList )
	{
		ModelMap modelMap = new ModelMap();
		modelMap.put("response", responseVO);
		modelMap.put("ruleDetails", ruleDataList);
		return modelMap;
	}
}
