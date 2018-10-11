package com.renrendai.loan.beetle.commons.util.config;

import java.util.Set;

import com.renrendai.loan.beetle.commons.util.error.BusinessException;

public interface ConfigElement {

	String attr(String attrName);
	
	String attr(String attrName, String defaultValue);
	
	int attr(String attrName, int defaultValue);
	
	Integer attrAsInt(String attrName);
	
	ConfigElement[] getChildren();
	
	ConfigElement[] getChildren(String elementName);
	
	String getValue();

	boolean attr(String attrName, boolean defaultValue);
	
	Boolean attrAsBoolean(String attrName);
	
	<T> T attr(String attrName, Class<T> clazz) throws BusinessException;

	ConfigElement getChild(String eleName) throws BusinessException;

	String getChildText(String eleName) throws BusinessException;

	String getName();
	
	Set<String> attrSet();
	
}
