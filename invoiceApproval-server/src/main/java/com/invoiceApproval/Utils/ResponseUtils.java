package com.invoiceApproval.Utils;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.invoiceApproval.entity.ResponseVO;

/**
 * @author atul_jadhav
 *
 */
public class ResponseUtils {

	/**
	 * This method returns modelmap object to UI or user for data iteration. 
	 * @param responseVO
	 * @param ruleDataList
	 * @param key
	 * @return ModelMap
	 */
	public static ModelMap getModelMap(ResponseVO responseVO,List<?> dataList,String key)
	{
		ModelMap modelMap = new ModelMap();
		modelMap.put("response", responseVO);
		modelMap.put(key, dataList);
		return modelMap;
	}
}
